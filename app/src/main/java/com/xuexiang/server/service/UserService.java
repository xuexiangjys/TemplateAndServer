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

package com.xuexiang.server.service;


import com.xuexiang.server.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户服务
 *
 * @author xuexiang
 * @since 2020/8/31 12:36 AM
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 是否成功
     * @throws SQLException 数据库执行报错
     */
    boolean addUser(User user) throws SQLException;

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 是否成功
     * @throws SQLException 数据库执行报错
     */
    boolean deleteUser(int userId) throws SQLException;

    /**
     * 更新用户
     *
     * @param record 用户最新信息
     * @return 是否更新成功
     * @throws SQLException 数据库执行报错
     */
    boolean updateUser(User record) throws SQLException;

    /**
     * 分页查询所有用户信息
     *
     * @param pageNum  页号
     * @param pageSize 一页的数量
     * @return 指定页面的用户信息
     */
    List<User> findAllUser(int pageNum, int pageSize);

    /**
     * 查询所有用户信息
     *
     * @return 所有用户信息
     * @throws SQLException 数据库执行报错
     */
    List<User> findAllUser() throws SQLException;

    /**
     * 根据账户名找到用户信息
     *
     * @param loginName 登录名
     * @return 用户信息
     * @throws SQLException 数据库执行报错
     */
    User findUserByAccount(String loginName) throws SQLException;

    /**
     * 用户登陆
     *
     * @param loginName 登录名
     * @param password  密码
     * @return 用户信息
     * @throws SQLException 数据库执行报错
     */
    User login(String loginName, String password) throws SQLException;

}
