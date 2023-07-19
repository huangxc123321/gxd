package com.gxa.jbgsw.website.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.UploadReslutDTO;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.AuthTypeEnum;
import com.obs.services.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Api(tags = "文件上传管理")
@RestController
@Slf4j
public class UploadFileController extends BaseController {
    @Value("${oss.endport}")
    String endport;
    @Value("${oss.ak}")
    String ak;
    @Value("${oss.sk}")
    String sk;
    @Value("${oss.bucketName}")
    String defalutBucketName;
    @Resource
    OssUtil ossUtil;
    final static String commBucketName = "oss-gxa-common";

    private static AuthTypeEnum authType = AuthTypeEnum.OBS;
    public static ObsClient obsClient;

    @ApiOperation("上传文件")
    @PostMapping(value = "/file/uploadfile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadReslutDTO uploadfile(@RequestParam("bucketName") String bucketName, @RequestParam("file") MultipartFile file) throws IOException {
        if(!StrUtil.isNotBlank(bucketName)){
            bucketName = this.defalutBucketName;
        }

        StringBuffer sb = new StringBuffer();
        obsClient = getInstance();
        if(file != null){
            //一个一个文件上传
            String fileTyle = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
            String filename = UUID.randomUUID().toString().replace("-", "") +fileTyle;

            byte[] bytes = file.getBytes();
            // upload(bytes, filename, file.getContentType());
            PutObjectResult putObjectResult = obsClient.putObject(bucketName, filename, new ByteArrayInputStream(bytes));

            String url = putObjectResult.getObjectUrl();
            // 特殊处理，图片直接下载
            if(commBucketName.equals(bucketName)){
                sb.append(url);
            }else {
                sb.append(ossUtil.getShowUrl(url));
            }
        }

        UploadReslutDTO reslut = new UploadReslutDTO();
        reslut.setUrl(sb.toString());
        reslut.setTitle(file.getOriginalFilename());

        log.info("上传文件返回的JSON：{}", JSONObject.toJSONString(reslut));

        return reslut;
    }


    @ApiOperation("上传文件")
    @PostMapping(value = "/file/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadReslutDTO fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String bucketName = this.defalutBucketName;
        return uploadfile(bucketName, file);
    }

    //流上传
    public void upload(String bucketName, byte[] bytes, String filename, String contentType) throws IOException{
        obsClient.putObject(bucketName, filename, new ByteArrayInputStream(bytes));
    }

    private ObsClient getInstance(){
        if (obsClient == null) {
            ObsConfiguration config = new ObsConfiguration();
            config.setSocketTimeout(30000);
            config.setConnectionTimeout(10000);
            config.setAuthType(authType);
            config.setEndPoint(endport);
            obsClient = new ObsClient(ak, sk, config);
        }
        return obsClient;
    }




}
