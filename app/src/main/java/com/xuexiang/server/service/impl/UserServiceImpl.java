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

package com.xuexiang.server.service.impl;

import com.xuexiang.server.model.User;
import com.xuexiang.server.service.UserService;
import com.xuexiang.xormlite.AndServerDataBaseRepository;
import com.xuexiang.xormlite.db.DBService;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户服务实现类
 *
 * @author xuexiang
 * @since 2020/8/31 12:42 AM
 */
public class UserServiceImpl implements UserService {

    private DBService<User> mService;

    public UserServiceImpl() {
        mService = AndServerDataBaseRepository.getInstance().getDataBase(User.class);
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        return mService.insert(user) > 0;
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        return mService.deleteById(userId) > 0;
    }

    @Override
    public boolean updateUser(User record) throws SQLException {
        return mService.updateData(record) > 0;
    }

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<User> findAllUser() throws SQLException {
        return mService.queryAll();
    }

    @Override
    public User findUserByAccount(String loginName) throws SQLException {
        return mService.queryForColumnFirst(User.KEY_LOGIN_NAME, loginName);
    }

    @Override
    public User login(String loginName, String password) throws SQLException {
        return mService.queryForColumnFirst(User.KEY_LOGIN_NAME, loginName, User.KEY_PASSWORD, password);
    }
}
