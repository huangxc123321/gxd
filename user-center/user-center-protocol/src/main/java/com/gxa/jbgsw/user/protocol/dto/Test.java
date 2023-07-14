package com.gxa.jbgsw.user.protocol.dto;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @Author Mr. huang
 * @Date 2023/7/13 0013 13:17
 * @Version 2.0
 */
public class Test {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .url("https://eolink.o.apispace.com/678678/high_res/v001/nowcasting?lonlat=104.06956,30.55366")
                .method("GET",null)
                .addHeader("X-APISpace-Token","hgf9xoiwu2dij9rhr1np3n1weazbgmdf")
                .addHeader("Authorization-Type","apikey")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
