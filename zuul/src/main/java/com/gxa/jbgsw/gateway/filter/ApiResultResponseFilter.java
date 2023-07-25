package com.gxa.jbgsw.gateway.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.common.utils.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
@RefreshScope
public class ApiResultResponseFilter extends ZuulFilter {
	Logger logger = LoggerFactory.getLogger(ApiResultResponseFilter.class);

	// @Value("${skip.should-filter.request.urls}")
	private List<String> skipRequestUrls = Collections.emptyList();

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
	}

	@Override
	public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
	    String uri = context.getRequest().getRequestURI();
        if((StrUtil.isNotBlank(uri) && uri.contains("export"))
                || (StrUtil.isNotBlank(uri) && uri.contains("cwting"))  ){
            return false;
        }

		return true;
	}


    @Override
    public Object run() throws ZuulException {
        logger.info("进入post处理的filter 中...");

        ApiResult apiResult = new ApiResult<>(0, "操作成功");
        String message = "";

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse httpServletResponse = context.getResponse();

        InputStream stream = context.getResponseDataStream();
        String body = null;
        try {
            if(stream != null){
                body = getStr(stream);
            }else{
                logger.error("返回null");
                apiResult.setCode(-1);
                apiResult.setMessage(null);
            }
        } catch (Exception e) {
           logger.error("返回解析发生错误:"+e.getMessage());
           apiResult.setCode(-1);
           apiResult.setMessage(e.getMessage());
        }

        if(StringUtils.isBlank(body)||body.contains("redirect:")){
            apiResult.setCode(0);
            apiResult.setMessage("操作成功");
            //直接返回
        }else {
            Object obj = JSONObject.parse(body);
            if (body.startsWith("{") && body.endsWith("}")) {
                Object code = ((JSONObject) obj).get("code");
                message = ((JSONObject) obj).getString("message") ;
                String error = ((JSONObject) obj).getString("error") ;

                if(code != null && message != null){
                    // 异常直接输出
                    apiResult.setCode(Integer.valueOf(code.toString()));
                    apiResult.setMessage( message == null ? "" : message.toString());
                }else if(StrUtil.isNotBlank(error)){
                    String status =  ((JSONObject) obj).getString("status") ;
                    apiResult.setCode(Integer.valueOf(status));
                    apiResult.setMessage( error == null ? "" : error.toString());
                } else {
                    apiResult.setData(obj);
                }
            }else{
                //直接放在data中
                apiResult.setData(obj);
            }

            logger.info("--------------------->{}", JSONObject.toJSONString(apiResult));
        }
/*        context.setResponseBody(JSONObject.toJSONString(apiResult,
                // 保留 Map 空的字段
                SerializerFeature.WriteMapNullValue,
                // 将 String 类型的 null 转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将 Number 类型的 null 转成 0
                SerializerFeature.WriteNullNumberAsZero,
                // 将 List 类型的 null 转成 []
                SerializerFeature.WriteNullListAsEmpty,
                // 将 Boolean 类型的 null 转成 false
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteBigDecimalAsPlain,

                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect));*/

        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
           /* byte[] dataByteArr = JSONObject.toJSONString(apiResult,  // 保留 Map 空的字段
                    SerializerFeature.WriteMapNullValue,
                    // 将 String 类型的 null 转成""
                    SerializerFeature.WriteNullStringAsEmpty,
                    // 将 Number 类型的 null 转成 0
                    SerializerFeature.WriteNullNumberAsZero,
                    // 将 List 类型的 null 转成 []
                    SerializerFeature.WriteNullListAsEmpty,
                    // 将 Boolean 类型的 null 转成 false
                    SerializerFeature.WriteNullBooleanAsFalse,
                    // 避免循环引用
                    SerializerFeature.DisableCircularReferenceDetect).getBytes();*/


            byte[] dataByteArr = JSONObject.toJSONBytes(apiResult);
            outputStream.write(dataByteArr);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return null;
    }

    private String getStr(InputStream inputStream) throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        String str = result.toString(String.valueOf(Charset.forName("UTF-8")));
        return str;
    }


}
