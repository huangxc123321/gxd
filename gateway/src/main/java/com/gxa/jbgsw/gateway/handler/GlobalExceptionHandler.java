package com.gxa.jbgsw.gateway.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gxa.jbgsw.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 全局异常处理，异常返回统一的格式
 * @author huangxc
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler, Ordered {
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        MediaType contentType = request.getHeaders().getContentType();
        if(contentType == null){
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        }else if(contentType.equals(MediaType.APPLICATION_JSON)){
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        }else{
            response.getHeaders().setContentType(contentType);
        }

        response.getHeaders().setAccept(request.getHeaders().getAccept());
        response.setStatusCode(HttpStatus.OK);

        Map<String, Object> map = new HashMap<>(4);

        if(ex instanceof BizException){
            BizException res = (BizException)ex;
            String message = res.getMessage();
            int code = res.getCode();
            map.put("message", message);
            map.put("code", code);
            map.put("data", new ArrayList());
        }else{
            String message = ex.getMessage();
            map.put("message", message);
            map.put("code", -1);
            map.put("data", null);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return response.writeWith(Mono.fromSupplier(()->{
            DataBufferFactory bufferFactory = response.bufferFactory();
            byte[] bytes;
            try{
                bytes = objectMapper.writeValueAsBytes(map);
            }catch (JsonProcessingException e){
                String msg = String.format("网关异常处理程序：%s,将响应数据处理成 byte 异常", this.getClass());
                bytes = msg.getBytes(StandardCharsets.UTF_8);
                log.error(msg, e);
            }

            return bufferFactory.wrap(bytes);
        }));


    }
}
