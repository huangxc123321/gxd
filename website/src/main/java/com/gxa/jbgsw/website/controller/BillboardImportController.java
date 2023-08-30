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

        // 先删除之前的数据
        billboardTemporaryFeignApi.deleteByCreateBy(userId);

        ApiResult apiResult = new ApiResult();
        List<BillboardTemporaryDTO> batchList = new ArrayList();
        batchList.clear();

        String unitName = userResponse.getUnitName();
        String unitLogo = userResponse.getUnitLogo();

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
                                billboardDTO.setType(value.contains("政") == true ? 0 : 1);
                                break;
                            case 1:
                                billboardDTO.setTitle(value);
                                break;
                            case 2:
                                billboardDTO.setCategories(getDicCode(value));
                                break;
                            case 3:
                                billboardDTO.setTechKeys(value);
                                break;
                            case 4:
                                break;
                            case 5:
                                billboardDTO.setContent(value);
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
                    }
                }
                billboardDTO.setUnitName(unitName);
                billboardDTO.setUnitLogo(unitLogo);
                billboardDTO.setCreateBy(userResponse.getCreateBy());
                billboardDTO.setCreateAt(new Date());
                billboardDTO.setProvinceId(userResponse.getProvinceId());
                billboardDTO.setProvinceName(userResponse.getProvinceName());
                billboardDTO.setCityId(userResponse.getCityId());
                billboardDTO.setCityName(userResponse.getCityName());
                billboardDTO.setAreaId(userResponse.getAreaId());
                billboardDTO.setAreaName(userResponse.getAreaName());
                if(billboardDTO.getTitle() == null || "".equals(billboardDTO.getTitle().trim())){
                    log.info("空行抛弃");
                }else{
                    batchList.add(billboardDTO);
                }
            }

            // 批量插入
            BillboardTemporaryDTO[]  objects = new BillboardTemporaryDTO[batchList.size()];
            batchList.toArray(objects);
            billboardTemporaryFeignApi.batchInsert(objects);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("导入失败");
            apiResult.setMessage("导入失败");
            apiResult.setCode(-1);
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
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
