package com.example.snashuitraverl.demos.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.snashuitraverl.demos.common.ErrorCode;
import com.example.snashuitraverl.demos.contant.UserConstant;
import com.example.snashuitraverl.demos.domain.User;
import com.example.snashuitraverl.demos.exception.BusinessException;
import com.example.snashuitraverl.demos.service.UserService;
import com.example.snashuitraverl.demos.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.snashuitraverl.demos.contant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 32551
 * @description 针对表【user(用户表
 * )】的数据库操作Service实现
 * @createDate 2024-06-23 18:59:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User userLogin(String account, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(account, userPassword)) {
            return null;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", userPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }


    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setOpenID(originUser.getOpenID());
        safetyUser.setAvatural(originUser.getAvatural());
        safetyUser.setAge(originUser.getAge());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        return safetyUser;
    }

    @Override
    public int userLoginOUt(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User User = (User) userObj;
        return User != null && User.getRole() == UserConstant.ADMIN_ROLE;
    }

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public User getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        User loginUserVO = new User();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

}




