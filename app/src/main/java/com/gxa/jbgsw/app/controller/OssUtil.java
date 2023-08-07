package com.gxa.jbgsw.app.controller;


import cn.hutool.core.util.StrUtil;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.AuthTypeEnum;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class OssUtil {

    @Value("${oss.endport}")
    String endport;
    @Value("${oss.ak}")
    String ak;
    @Value("${oss.sk}")
    String sk;
    @Value("${oss.bucketName}")
    String bucketName;

    private static AuthTypeEnum authType = AuthTypeEnum.OBS;
    public static ObsClient obsClient;

    public ObsClient getInstance(){
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

    //流上传
    public void upload(byte[] bytes, String filename) throws IOException {
        obsClient.putObject(bucketName, filename, new ByteArrayInputStream(bytes));
    }

    //流上传
    public void uploadWebsite(byte[] bytes, String filename) throws IOException {
        obsClient.putObject("officialwebsite", filename, new ByteArrayInputStream(bytes));
    }

    public String getShowUrl(String url, String bucketName){
        List<String> str = StrUtil.split(url, StrUtil.C_SLASH);
        String filename = str.get(str.size()-1);
        if(StrUtil.isBlank(filename)){
            return null;
        }

        // 通过临时URL访问OBS
        // OBS客户端支持通过访问密钥、请求方法类型、请求参数等信息生成一个在Query参数中携带鉴权信息的URL，
        // 可将该URL提供给其他用户进行临时访问。在生成URL时，您需要指定URL的有效期来限制访客用户的访问时长
        Long expireSeconds = 315360000L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setRequestDate(new Date());
        request.setBucketName(bucketName);
        request.setObjectKey(filename);

        obsClient = getInstance();
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        String path = response.getSignedUrl();
        return path;
    }

    public String getShowUrl(String url){
        List<String> str = StrUtil.split(url, StrUtil.C_SLASH);
        String filename = str.get(str.size()-1);
        if(StrUtil.isBlank(filename)){
            return null;
        }

        // 通过临时URL访问OBS
        // OBS客户端支持通过访问密钥、请求方法类型、请求参数等信息生成一个在Query参数中携带鉴权信息的URL，
        // 可将该URL提供给其他用户进行临时访问。在生成URL时，您需要指定URL的有效期来限制访客用户的访问时长
        Long expireSeconds = 315360000L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setRequestDate(new Date());
        request.setBucketName(bucketName);
        request.setObjectKey(filename);

        obsClient = getInstance();
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        String path = response.getSignedUrl();
        return path;
    }


}
