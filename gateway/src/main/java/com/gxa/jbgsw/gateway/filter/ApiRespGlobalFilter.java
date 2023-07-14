package com.gxa.jbgsw.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class ApiRespGlobalFilter implements GlobalFilter, Ordered {
    private final ObjectMapper jsonMapper;

    public ApiRespGlobalFilter(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (path.contains("websocket")) {
            return chain.filter(exchange);
        }
        //获取response的 返回数据
        ServerHttpResponse response = exchange.getResponse();

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.OK) {

            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
                @Override
                public boolean setStatusCode(HttpStatus status) {
                    return super.setStatusCode(HttpStatus.OK);
                }

                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    ServerHttpResponse delegateResponse = this.getDelegate();
                    MediaType contentType = delegateResponse.getHeaders().getContentType();
                    log.debug("contentType:{}", contentType);

                    if (null == contentType) {
                        String resp = "{\"success\":true,\"code\":\"0\",\"message\":\"成功\"}";
                        byte[] newRs = resp.getBytes(StandardCharsets.UTF_8);
                        delegateResponse.setStatusCode(HttpStatus.OK);
                        delegateResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                        delegateResponse.getHeaders().setContentLength(newRs.length);
                        DataBuffer buffer = delegateResponse.bufferFactory().wrap(newRs);
                        return delegateResponse.writeWith(Flux.just(buffer));
                    }

                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);

                        Charset contentCharset = contentType.getCharset();
                        contentCharset = (null == contentCharset) ? StandardCharsets.UTF_8 : contentCharset;
                        if (contentType.equalsTypeAndSubtype(MediaType.TEXT_HTML)) {
                            String responseData = new String(content, contentCharset);
                            log.debug("响应内容:{}", responseData);
                            final String redirectCommand = "redirect:";
                            int redirectCommandIndex = responseData.indexOf(redirectCommand);
                            if (redirectCommandIndex >= 0) {
                                String redirectUri = responseData.substring(redirectCommandIndex + redirectCommand.length()).trim();

                                delegateResponse.setStatusCode(HttpStatus.SEE_OTHER);
                                delegateResponse.getHeaders().set(HttpHeaders.LOCATION, redirectUri);
                            }
                        }
                        if (contentType.includes(MediaType.APPLICATION_JSON) || contentType.includes(MediaType.APPLICATION_JSON_UTF8)) {

                            String responseData = new String(content, contentCharset);
                            JSONObject jsonObject = JSONObject.parseObject(responseData);
                            log.debug("响应内容:{}", responseData);
                            ApiResult apiResult = new ApiResult();

                            try {

                                if(jsonObject.size() != 3){
                                    apiResult.setData(jsonObject);
                                }else {
                                    apiResult.setMessage(jsonObject.getString("message"));
                                    apiResult.setCode(jsonObject.getInteger("code"));
                                }

                                byte[] newRs = jsonMapper.writeValueAsBytes(apiResult);
                                delegateResponse.getHeaders().setContentLength(newRs.length);
                                return delegateResponse.bufferFactory().wrap(newRs);


                            } catch (Exception e) {
                                String errResp = "";
                                if(e instanceof BizException){
                                    apiResult.setMessage(((BizException) e).getMessage());
                                    apiResult.setCode(((BizException) e).getCode());
                                }else {
                                    apiResult.setMessage("系统错误");
                                    apiResult.setCode(500);
                                }

                                //errResp = "{\"success\":false,\"code\":\"500\",\"message\":\"System Error\"}";
                                byte[] newRs = JSONObject.toJSONString(apiResult).getBytes(contentCharset);
                                delegateResponse.getHeaders().setContentLength(newRs.length);
                                return delegateResponse.bufferFactory().wrap(newRs);
                            }
                        }

                        return delegateResponse.bufferFactory().wrap(content);
                    }));
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
