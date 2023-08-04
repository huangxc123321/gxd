package com.gxa.jbgsw.website.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.basis.protocol.dto.DictionaryResponse;
import com.gxa.jbgsw.basis.protocol.enums.DictionaryTypeEnum;
import com.gxa.jbgsw.business.protocol.dto.BillboardDTO;
import com.gxa.jbgsw.business.protocol.errcode.BusinessErrorCode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import com.gxa.jbgsw.website.feignapi.BillboardFeignApi;
import com.gxa.jbgsw.website.feignapi.DictionaryTypeFeignApi;
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
    BillboardFeignApi billboardFeignApi;

    @ApiOperation("上传文件")
    @PostMapping(value = "/readExcel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        List<BillboardDTO> batchList = new ArrayList();
        InputStream inputStream = null;

        Long userId = this.getUserId();
        if(userId == null){
            throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
        }
        String unitName = this.getUnitName();


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
            for (int i = 1; i < lasrRowNum; i++) {
                BillboardDTO billboardDTO = new BillboardDTO();

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
                                billboardDTO.setType(value.equals("政府榜") == true ? 0 : 1);
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

                    billboardDTO.setUnitName(unitName);
                    billboardDTO.setCreateBy(userId);
                    billboardDTO.setCreateAt(new Date());
                    batchList.add(billboardDTO);
                }
            }

            // 批量插入
            BillboardDTO[]  objects = new BillboardDTO[batchList.size()];
            batchList.toArray(objects);
            billboardFeignApi.batchInsert(objects);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }

    }

    private void deal(String value, BillboardDTO billboardDTO) {
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
