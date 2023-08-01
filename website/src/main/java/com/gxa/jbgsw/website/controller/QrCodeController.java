package com.gxa.jbgsw.website.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gxa.jbgsw.common.exception.BizException;
import com.gxa.jbgsw.common.utils.ApiResult;
import com.gxa.jbgsw.common.utils.BaseController;
import com.gxa.jbgsw.user.protocol.dto.QrConfirmLoginDTO;
import com.gxa.jbgsw.user.protocol.dto.UserResponse;
import com.gxa.jbgsw.user.protocol.dto.UuidDTO;
import com.gxa.jbgsw.user.protocol.enums.QrCodeStatusEnum;
import com.gxa.jbgsw.user.protocol.errcode.UserErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


@Api(tags = "二维码管理")
@RestController
@Slf4j
@ResponseBody
public class QrCodeController extends BaseController {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    /**
     * token存储map
     */
    private static final Map<String, String> TOKEN_MAP = new ConcurrentHashMap<>();

    @ApiOperation("获取二维码返回的唯一ID，用户PC生成二维码")
    @GetMapping("/qr/initQRCodeInfo")
    UuidDTO initQRCodeInfo(){
        /**
         * 访问PC端二维码生成页面，PC端请求服务端获取二维码ID
         * 服务端生成相应的二维码ID，设置二维码的过期时间，状态等
         */
        String uuid = UUID.randomUUID().toString().replaceAll("-","");

        UuidDTO uuidDTO = new UuidDTO();
        uuidDTO.setUuid(uuid);
        // 设置待扫描状态(待扫描)
        uuidDTO.setStatus(QrCodeStatusEnum.WAITING.getStatusCode());

        // 保存redis, 过期时间为一小时
        stringRedisTemplate.opsForValue().set(uuid, JSONObject.toJSONString(uuidDTO),60, TimeUnit.MINUTES);

        return uuidDTO;
    }


    @ApiOperation("确认登录: 手机端通过带二维码信息（二维码ID）请求服务端，完成认证过程，确认PC端的登录")
    @PostMapping("/qr/confirmLogin")
    UuidDTO confirmLogin(@RequestBody QrConfirmLoginDTO qrConfirmLoginDTO){
        String uuidStr = stringRedisTemplate.opsForValue().get(qrConfirmLoginDTO.getUuid());
        if(uuidStr == null || StrUtil.isBlank(uuidStr)){
            throw new BizException(UserErrorCode.QR_CODE_EXPIRE);
        }

        UuidDTO uuidDTO = JSONObject.parseObject(uuidStr, UuidDTO.class);
        if(!QrCodeStatusEnum.SCANNED.getStatusCode().equals(uuidDTO.getStatus())){
            // 用户信息
            String userInfo = stringRedisTemplate.opsForValue().get(qrConfirmLoginDTO.getToken());
            if(userInfo == null || StrUtil.isBlank(userInfo)){
                throw new BizException(UserErrorCode.LOGIN_SESSION_EXPIRE);
            }
            //通过base64将UUID转码生成token
            TOKEN_MAP.put(qrConfirmLoginDTO.getUuid(), qrConfirmLoginDTO.getToken());

            // 已确认: 状态为2
            uuidDTO.setStatus(QrCodeStatusEnum.CONFIRMED.getStatusCode());
            // 保存redis, 过期时间为一小时
            stringRedisTemplate.opsForValue().set(qrConfirmLoginDTO.getUuid(), JSONObject.toJSONString(uuidDTO),60, TimeUnit.MINUTES);
        }else{
            // 取消
            uuidDTO.setStatus(QrCodeStatusEnum.CANCEL.getStatusCode());
        }

        return uuidDTO;
    }


    @ApiOperation("PC端通过轮询方式请求服务端，通过二维码ID获取二维码状态，如果已成功，返回PC token，登录成功")
    @GetMapping("/qr/pollingQrCodeState")
    ApiResult<UserResponse> pollingQrCodeState(@RequestParam("uuid") String uuid){
        ApiResult<UserResponse>apiResult = new ApiResult();

        String uuidStr = stringRedisTemplate.opsForValue().get(uuid);
        if(uuidStr == null || StrUtil.isBlank(uuidStr)){
            throw new BizException(UserErrorCode.QR_CODE_EXPIRE);
        }
        UuidDTO uuidDTO = JSONObject.parseObject(uuidStr, UuidDTO.class);
        if(uuidDTO.getStatus().equals(QrCodeStatusEnum.CONFIRMED.getStatusCode())){
            // 已经登录返回token
            String appToken = TOKEN_MAP.get(uuid);
            // 用户信息
            String userInfo = stringRedisTemplate.opsForValue().get(appToken);
            if(StrUtil.isNotBlank(userInfo)){
                UserResponse userResponse = JSONObject.parseObject(userInfo, UserResponse.class);
                apiResult.setData(userResponse);

                return apiResult;
            }
            // 清除临时变量
            TOKEN_MAP.remove(uuid);
        }

        return apiResult;
    }



}
