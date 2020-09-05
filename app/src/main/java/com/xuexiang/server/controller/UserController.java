/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.server.controller;

import com.xuexiang.server.api.base.ApiException;
import com.xuexiang.server.api.request.PageQuery;
import com.xuexiang.server.api.response.LoginInfo;
import com.xuexiang.server.model.User;
import com.xuexiang.server.service.UserService;
import com.xuexiang.server.service.impl.UserServiceImpl;
import com.xuexiang.server.utils.TokenUtils;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;

import java.util.List;

import static com.xuexiang.server.api.base.ApiException.ERROR.COMMON_BUSINESS_ERROR;

/**
 * 用户服务
 *
 * @author xuexiang
 * @since 2020/8/30 10:25 PM
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService mUserService;

    private UserService getUserService() {
        if (mUserService == null) {
            mUserService = new UserServiceImpl();
        }
        return mUserService;
    }

    /**
     * 登陆，获取token
     *
     * @param loginName 用户名
     * @param password  密码
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/action/login")
    LoginInfo login(@RequestParam(name = "loginName") String loginName,
                    @RequestParam(name = "password") String password) throws Exception {
        return handleLoginRequest(loginName, password);
    }

    /**
     * 注册用户
     *
     * @param loginName 用户名
     * @param password  密码
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/action/register")
    boolean registerUser(@RequestParam(name = "loginName") String loginName,
                         @RequestParam(name = "password") String password) throws Exception {
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        return handleRegisterRequest(user);
    }

    /**
     * 注册用户
     */
    @PostMapping(path = "/registerUser")
    boolean registerUser(@RequestBody User user) throws Exception {
        return handleRegisterRequest(user);
    }

    /**
     * 编辑用户信息
     *
     * @param user 用户
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/editUser")
    boolean editUser(@RequestBody User user) throws Exception {
        return getUserService().updateUser(user);
    }

    /**
     * 删除用户
     *
     * @param user 用户
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/deleteUser")
    boolean deleteUser(@RequestBody User user) throws Exception {
        return getUserService().deleteUser((int) user.getId());
    }

    /**
     * 登陆，获取token
     *
     * @param loginUser 登录信息
     * @return 登录
     */
    @PostMapping(path = "/login")
    LoginInfo login(@RequestBody User loginUser) throws Exception {
        return handleLoginRequest(loginUser.getLoginName(), loginUser.getPassword());
    }


    /**
     * 分页查询用户信息
     *
     * @param pageQuery 分页查询
     * @return 登录
     */
    @PostMapping(path = "/queryUser")
    List<User> queryUser(@RequestBody PageQuery pageQuery) throws Exception {
        return getUserService().findAllUser(pageQuery.pageNum, pageQuery.pageSize);
    }

    /**
     * 处理登录请求
     *
     * @param loginName 用户名
     * @param password  密码
     * @return 登录信息
     * @throws Exception
     */
    private LoginInfo handleLoginRequest(String loginName, String password) throws Exception {
        if (getUserService().findUserByAccount(loginName) == null) {
            throw new ApiException("账号不存在！", COMMON_BUSINESS_ERROR);
        }

        User user = getUserService().login(loginName, password);

        if (user != null) {
            return new LoginInfo()
                    .setUser(user)
                    .setToken(TokenUtils.createJwtToken(user.getLoginName()));
        } else {
            throw new ApiException("用户名或密码错误！", COMMON_BUSINESS_ERROR);
        }
    }

    /**
     * 处理注册请求
     *
     * @param user 用户
     */
    private boolean handleRegisterRequest(User user) throws Exception {
        if (getUserService().findUserByAccount(user.getLoginName()) != null) {
            throw new ApiException("账号已存在！", COMMON_BUSINESS_ERROR);
        }
        return getUserService().addUser(user);
    }

}
