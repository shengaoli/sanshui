package com.example.snashuitraverl.demos.controller;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.snashuitraverl.demos.common.BaseResponse;
import com.example.snashuitraverl.demos.common.ErrorCode;
import com.example.snashuitraverl.demos.common.ResultUtils;
import com.example.snashuitraverl.demos.domain.User;
import com.example.snashuitraverl.demos.exception.BusinessException;
import com.example.snashuitraverl.demos.mapper.UserMapper;
import com.example.snashuitraverl.demos.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 32551
 */
@RestController
@RequestMapping("/Alipay")
public class AlipaySystemOauthToken {


    @Value("${alipay.appId}")
    public String appId;

    @Value("${alipay.appPrivateKey}")
    public String appPrivateKey;

    @Value("${alipay.alipayPublicKey}")
    public String PublicKey;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    private static String authToken;

    /**
     * 解析code获取open_id和token
     *
     * @return ikun
     * @throws AlipayApiException
     */
    @GetMapping("/parseCode")
    public BaseResponse<String> login(String authcode) throws AlipayApiException {
        System.out.println(authcode);
        String privateKey = appPrivateKey;
        String alipayPublicKey = PublicKey;
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authcode);
        request.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        authToken = response.getAccessToken();

        return response.isSuccess() == true ? ResultUtils.success(authToken, "解析成功!") : ResultUtils.error(ErrorCode.PARAMS_ERROR, "解析失败");
    }

    @GetMapping("/login")
    public BaseResponse<User> getMessage() throws AlipayApiException {
        System.out.println(authToken);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, appPrivateKey, "json", "GBK", PublicKey, "RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        request.putOtherTextParam("auth_token", authToken);
        AlipayUserInfoShareResponse response = alipayClient.execute(request, authToken);
        User openId = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenID, response.getOpenId()));
        if (!response.isSuccess()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (openId != null) {
            return ResultUtils.success(openId, "登录成功");
        }
        User user = new User();
        user.setOpenID(response.getOpenId());
        String s = RandomUtil.randomString(10);
        user.setAccount("随机名字" + s);
        user.setPassword("123456");
        user.setPhone(response.getPhone());
        user.setAge(response.getAge());
        user.setGender(response.getGender());
        user.setEmail(response.getEmail());
        user.setRole(0);
        user.setType(0);
        user.setAvatural(response.getAvatar());
        user.setAddress(response.getAddress());
        int insert = userMapper.insert(user);
        if (insert != 1) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "注册失败");
        }
        return ResultUtils.success(user, "注册成功");
    }
}
