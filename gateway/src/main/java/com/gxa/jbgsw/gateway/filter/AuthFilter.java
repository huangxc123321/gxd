package com.gxa.jbgsw.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.RedisKeys;
import com.gxa.jbgsw.common.utils.RedisUtils;
import com.gxa.jbgsw.gateway.props.AppAuthProperties;
import com.gxa.jbgsw.gateway.props.AuthProperties;
import com.gxa.jbgsw.gateway.props.WebAuthProperties;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/*

 */
/**
 * 鉴权认证
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    AuthProperties authProperties;
    @Resource
    WebAuthProperties webAuthProperties;
    @Resource
    AppAuthProperties appAuthProperties;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

        ServerHttpRequest request = exchange.getRequest();
        // 看请求是否需要鉴权，如果需要鉴权，那么就判断是否有token
        String path = request.getURI().getPath();
        if(authProperties != null && authProperties.getUrl().contains(path)){
            return chain.filter(exchange);
        }

        // web URL 是否需要登录判断
        if(StrUtil.isNotBlank(path) && !webAuthProperties.getUrl().contains(path)){
            return chain.filter(exchange);
        }

        // app URL 是否需要登录判断
        if(StrUtil.isNotBlank(path) && !appAuthProperties.getUrl().contains(path)){
            return chain.filter(exchange);
        }

        if(request != null){
            HttpHeaders headers = request.getHeaders();
            List<String> list = headers.get("token");
            if(list != null && list.size() > 0){
                String token = list.get(0);
                String redisKeys = RedisKeys.USER_TOKEN+token;
                Object redisValue = stringRedisTemplate.opsForValue().get(redisKeys);
                if(redisValue != null){
                    return chain.filter(exchange);
                }
            }
        }
        // TODO: 2023/6/21 0021
         throw new BizException(UserErrorCode.TOKEN_NOT_EXIST);
    }

    /**
     * 值越小，优先级越来越高
     */
    @Override
    public int getOrder() {
        return -1;
    }
}

