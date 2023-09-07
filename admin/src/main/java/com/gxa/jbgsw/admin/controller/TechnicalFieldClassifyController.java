package com.gxa.jbgsw.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.gxa.jbgsw.admin.feignapi.TechnicalFieldClassifyFeignApi;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyDTO;
import com.gxa.jbgsw.basis.protocol.dto.TechnicalFieldClassifyPO;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.common.utils.RedisKeys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "技术领域分类管理")
@RestController
@Slf4j
@ResponseBody
public class TechnicalFieldClassifyController extends BaseController {
    @Resource
    TechnicalFieldClassifyFeignApi technicalFieldClassifyFeignApi;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("根据ID获取技术领域分类: 获取所有分类： pid：0， 获取某个分类把这个分类得ID值作为pid的值传入即可。")
    @GetMapping("/techical/field/classify/getAllById")
    ApiResult<List<TechnicalFieldClassifyPO>> getCommentById(@RequestParam("pid") Long pid) throws BizException {
        List<TechnicalFieldClassifyPO> responress = technicalFieldClassifyFeignApi.getAllById(pid);

        ApiResult<List<TechnicalFieldClassifyPO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }

    @ApiOperation("获取pid下的所有分类， pid 不能是0， 否则会嵌套死")
    @GetMapping("/techical/field/classify/getAllByParentId")
    ApiResult<List<TechnicalFieldClassifyDTO>> getAllByParentId(@RequestParam("pid") Long pid) throws BizException {
        List<TechnicalFieldClassifyDTO> responress = technicalFieldClassifyFeignApi.getAllByParentId(pid);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }


    @ApiOperation("获取所有数据")
    @GetMapping("/techical/field/classify/getAll")
    ApiResult<List<TechnicalFieldClassifyDTO>> getAll() throws BizException {

        String json = stringRedisTemplate.opsForValue().get(RedisKeys.TECH_DOMAIN_VALUE);
        List<TechnicalFieldClassifyDTO> responress = JSONArray.parseArray(json, TechnicalFieldClassifyDTO.class);

        ApiResult<List<TechnicalFieldClassifyDTO>> apiResult = new ApiResult();
        apiResult.setData(responress);

        return apiResult;
    }



    @ApiOperation("上传文件")
    @GetMapping(value = "/readExcelTeam",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void fileUpload() throws Exception {
        InputStream inputStream = null;

        Map<String, Long> map = new HashMap<>();
        map.put("生物与新医药", 1693111045016500001L);
        map.put("先进制造与自动化", 1693111045016500002L);
        map.put("现代农业", 1693111045016500003L);
        map.put("航空航天", 1693111045016500004L);
        map.put("新能源与节能", 1693111045016500005L);
        map.put("资源与环境", 1693111045016500006L);
        map.put("建筑业", 1693111045016500007L);
        map.put("新材料", 1693111045016500008L);
        map.put("电子信息",1693111045016500009L);
        map.put("海洋资源与利用", 1693111045016500010L);
        map.put("其他", 1693111045016500011L);

        try{
            File file = new File("C:\\Users\\Administrator\\Desktop\\101\\11.xlsx");
            // 获得文件流
            FileInputStream fileInputStream=new FileInputStream(file);

            //1.创建工作簿,使用excel能操作的这边都看看操作
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            //2.得到表
            Sheet sheet = workbook.getSheetAt(0);
            // 起始行第0行
            int firstRowNum = 0;
            // 一直读到最后一行
            int lasrRowNum = sheet.getLastRowNum();

            for (int i = 1; i <= lasrRowNum; i++) {
                Row row =  sheet.getRow(i);

                Cell cell0 = row.getCell(0);
                Long pid = null;
                if(cell0 != null){
                    String value0 = cell0.getStringCellValue();
                    pid = map.get(value0);
                }

                TechnicalFieldClassifyPO po1 = null;
                Cell cell1 = row.getCell(1);
                Long p1 = null;
                if(cell1 != null){
                    String value1 = cell1.getStringCellValue();
                    System.out.println(value1);

                    po1 = new TechnicalFieldClassifyPO();
                    po1.setPid(pid);
                    po1.setName(value1);
                    p1 = technicalFieldClassifyFeignApi.insert(po1);
                }

                TechnicalFieldClassifyPO po2 = null;
                Cell cell2 = row.getCell(2);
                if(cell2 != null){
                    String value2 = cell2.getStringCellValue();
                    String[] names = value2.split("；");
                    if(names != null && names.length>0){
                        for(int m =0; m< names.length; m++){
                            po2 = new TechnicalFieldClassifyPO();
                            po2.setPid(p1);
                            po2.setName(names[m]);

                            technicalFieldClassifyFeignApi.insert(po2);
                        }
                    }

                }

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
