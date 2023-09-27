package com.gxa.jbgsw.admin.controller;

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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
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
    final static String commBucketName = "oss-gxa-pc-common";

    private static AuthTypeEnum authType = AuthTypeEnum.OBS;
    public static ObsClient obsClient;


    //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


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

            String os = System.getProperty("os.name").toLowerCase();
            byte[] bytes = null;

            if (os.contains("win")) {
                // Windows操作系统
                String tempPath = "C:\\\\temp";

                File directory = new File(tempPath);
                if (!directory.exists()){
                    directory.mkdirs();
                }

                File destFile = new File(tempPath, filename);

                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                bytes = getBytesByFile(destFile.getPath());
            } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
                // Linux操作系统
                bytes = file.getBytes();
            }else{
                // Linux操作系统
                bytes = file.getBytes();
            }
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
