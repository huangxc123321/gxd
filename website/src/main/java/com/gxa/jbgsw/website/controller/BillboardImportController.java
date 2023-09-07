package com.gxa.jbgsw.website.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryDTO;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryDTO;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryRequest;
import com.gxa.jbgsw.business.protocol.dto.BillboardTemporaryResponse;
import com.gxa.jbgsw.business.protocol.enums.DictionaryTypeCodeEnum;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.PageResult;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "榜单导入功能")
@RestController
@Slf4j
@ResponseBody
public class BillboardImportController extends BaseController {
    @Resource
    DictionaryTypeFeignApi dictionaryTypeFeignApi;
    @Resource
    BillboardTemporaryFeignApi billboardTemporaryFeignApi;
    @Resource
    UserFeignApi userFeignApi;
    @Resource
    DictionaryFeignApi dictionaryFeignApi;
    @Resource
    BillboardFeignApi billboardFeignApi;


    @ApiOperation(value = "批量提交", notes = "批量提交")
    @PostMapping("/temporary/billboard/insertBatchIds")
    public void insertBatchIds(@RequestBody Long[] ids){
        billboardFeignApi.insertBatchIds(ids);
    }

    @ApiOperation("编辑导入榜单信息")
    @PostMapping("/temporary/billboard/update")
    void update(@RequestBody BillboardTemporaryDTO billboardDTO) throws BizException {
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        if(billboardDTO.getId() == null){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_ERROR);
        }
        billboardTemporaryFeignApi.update(billboardDTO);
    }

    @ApiOperation(value = "获取榜单详情", notes = "获取榜单详情")
    @GetMapping("/temporary/billboard/getById")
    BillboardTemporaryDTO getById(@RequestParam("id") Long id){
        BillboardTemporaryDTO billboardTemporaryDTO = billboardTemporaryFeignApi.getById(id);
        return billboardTemporaryDTO;
    }

    @ApiOperation(value = "批量删除导入榜单", notes = "批量删除导入榜单")
    @PostMapping("/temporary/billboard/deleteBatchIds")
    public void deleteBatchIds(@RequestBody Long[] ids){
        billboardTemporaryFeignApi.deleteBatchIds(ids);
    }

    @ApiOperation("获取榜单列表")
    @PostMapping("/temporary/billboard/pageQuery")
    PageResult<BillboardTemporaryResponse> pageQuery(@RequestBody BillboardTemporaryRequest request){
        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        request.setCreateBy(userId);
        PageResult<BillboardTemporaryResponse> pageResult = billboardTemporaryFeignApi.pageQuery(request);
        if(pageResult != null){
            List<BillboardTemporaryResponse> responses = pageResult.getList();
            if(CollectionUtils.isNotEmpty(responses)){
                responses.stream().forEach(s->{
                    DictionaryDTO dictionaryDTO = dictionaryFeignApi.getByCache(DictionaryTypeCodeEnum.categories.name(), String.valueOf(s.getCategories()));
                    if(dictionaryDTO != null){
                        // 工信大类名称
                        s.setCategoriesName(dictionaryDTO.getDicValue());
                    }
                    // 状态： 0 导入成功  1 导入失败
                    s.setStatusName(s.getStatus().equals(Integer.valueOf(0)) ? "导入成功" : "导入失败");
                });
            }
        }


        return pageResult;
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/readExcel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        Long userId = this.getUserId();
        InputStream inputStream = null;
        UserResponse userResponse = userFeignApi.getUserById(userId);

        if(userId == null || userResponse == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }

        ApiResult apiResult = new ApiResult();
        List<BillboardTemporaryDTO> batchList = new ArrayList();
        batchList.clear();

        String unitName = userResponse.getUnitName();
        String unitLogo = userResponse.getUnitLogo();

        int total = 0;
        int failures = 0;

        try{
            // 获得文件流
            inputStream =  file.getInputStream();
            //1.创建工作簿,使用excel能操作的这边都看看操作
            Workbook workbook = new XSSFWorkbook(inputStream);
            //2.得到表
            Sheet sheet = workbook.getSheetAt(0);
            // 起始行第0行
            int firstRowNum = 0;
            // 一直读到最后一行
            int lasrRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lasrRowNum; i++) {
                BillboardTemporaryDTO billboardDTO = new BillboardTemporaryDTO();
                Integer status = 0;

                Row row =  sheet.getRow(i);
                // 获取当前最后单元格列号
                int lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);


                    CellType cellType = cell.getCellType();
                    if(CellType.STRING.compareTo(cellType) == 0){
                        String value = cell.getStringCellValue();
                        switch (j) {
                            case 0:
                                if(value.contains("政") || value.contains("企")){
                                    billboardDTO.setType(value.contains("政") == true ? 0 : 1);
                                }else{
                                    billboardDTO.setType(1);
                                    status = 1;
                                }
                                break;
                            case 1:
                                if(StrUtil.isBlank(value)){
                                    status = 1;
                                }else{
                                    billboardDTO.setTitle(value);
                                }

                                break;
                            case 2:
                                billboardDTO.setCategories(getDicCode(value));
                                if(billboardDTO.getCategories() == null){
                                    status = 1;
                                }
                                break;
                            case 3:
                                billboardDTO.setTechKeys(value);
                                if(StrUtil.isBlank(billboardDTO.getTechKeys())){
                                    status = 1;
                                }
                                break;
                            case 4:
                                break;
                            case 5:
                                billboardDTO.setContent(value);
                                if(StrUtil.isBlank(billboardDTO.getContent())){
                                    status = 1;
                                }
                                break;
                            case 6:
                                deal(value, billboardDTO);
                                break;
                            default:
                                break;
                        }
                    }else if(CellType.NUMERIC.compareTo(cellType) == 0){
                        double amount = cell.getNumericCellValue();
                        billboardDTO.setAmount(BigDecimal.valueOf(amount));
                        if(billboardDTO.getAmount() == null){
                            status = 1;
                        }
                    }
                }
                billboardDTO.setUnitName(unitName);
                billboardDTO.setUnitLogo(unitLogo);
                billboardDTO.setCreateBy(userResponse.getId());
                billboardDTO.setCreateAt(new Date());
                billboardDTO.setProvinceId(userResponse.getProvinceId());
                billboardDTO.setProvinceName(userResponse.getProvinceName());
                billboardDTO.setCityId(userResponse.getCityId());
                billboardDTO.setCityName(userResponse.getCityName());
                billboardDTO.setAreaId(userResponse.getAreaId());
                billboardDTO.setAreaName(userResponse.getAreaName());
                billboardDTO.setStatus(status);
                if(billboardDTO.getTitle() == null || "".equals(billboardDTO.getTitle().trim())){
                    log.info("空行抛弃");
                }else{
                    batchList.add(billboardDTO);
                }

                if(status == 1){
                    ++failures;
                }
            }

            // 批量插入
            BillboardTemporaryDTO[]  objects = new BillboardTemporaryDTO[batchList.size()];
            batchList.toArray(objects);

            total = objects.length;
            System.out.println("total: "+total);
            billboardTemporaryFeignApi.batchInsert(objects);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("导入有些错误");
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }

        int success = total - failures;
        apiResult.setMessage("成功上传"+success+"条数据，失败"+failures+"条。您可以在列表编辑或删除");
        return  apiResult;
    }

    private void deal(String value, BillboardTemporaryDTO billboardDTO) {
        try{
            if(StrUtil.isNotBlank(value)){
                String[] strs =StrUtil.split(value, "-");
                if(strs != null && strs.length == 2){
                    billboardDTO.setStartAt(DateUtil.parse(strs[0], "yyyy/MM/dd"));
                    billboardDTO.setEndAt(DateUtil.parse(strs[1],"yyyy/MM/dd"));
                }
            }
        }catch (Exception ex){
            throw new BizException(BusinessErrorCode.BUSINESS_PARAMS_DATE_ERROR);
        }
    }

    private Integer getDicCode(String value){
        String dicCode = "0";

        List<DictionaryResponse> responses = dictionaryTypeFeignApi.getByCode(DictionaryTypeEnum.categories.name());
        if(CollectionUtils.isNotEmpty(responses)){
            for(DictionaryResponse response : responses ){
                if(response.getDicValue().equals(value)){
                    dicCode = response.getDicCode();
                    break;
                }
            }
        }
        if(StrUtil.isNotBlank(dicCode)){
            return Integer.valueOf(dicCode);
        }

        return 0;
    }



}
