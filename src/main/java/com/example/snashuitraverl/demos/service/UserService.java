package com.example.snashuitraverl.demos.service;

import com.example.snashuitraverl.demos.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 32551
* @description 针对表【user(用户表
)】的数据库操作Service
* @createDate 2024-06-23 18:59:56
*/
public interface UserService extends IService<User> {


    /**
     * 用户登录
     *
     * @param sno  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String sno, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @Param HttpServletRequest
     */
    int userLoginOUt(HttpServletRequest request);

    /**
     * 获取当前登录用户信息
     * @return
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);


    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    User getLoginUserVO(User user);

}
