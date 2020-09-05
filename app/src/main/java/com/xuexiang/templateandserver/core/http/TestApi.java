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

package com.xuexiang.templateandserver.core.http;

import com.xuexiang.server.model.User;
import com.xuexiang.xhttp2.annotation.NetMethod;
import com.xuexiang.xhttp2.model.ApiResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author xuexiang
 * @since 2020/9/5 11:19 PM
 */
public final class TestApi {


    public interface UserService {

        /**
         * 这里使用的是retrofit的接口定义
         */
        @POST("/user/registerUser/")
        @Headers({"Content-Type: application/json", "Accept: application/json"})
        Observable<ApiResult<Boolean>> register(@Body User user);

        /**
         * 这里使用的是retrofit的接口定义
         */
        @POST("/user/editUser/")
        @Headers({"Content-Type: application/json", "Accept: application/json"})
        Observable<ApiResult<Boolean>> editUser(@Body User user);

        /**
         * 这里使用的是自定义的接口定义
         *
         * @param userId 用户id
         */
        @NetMethod(parameterNames = {"Id"}, url = "/user/deleteUser/", accessToken = false)
        Observable<Boolean> deleteUser(long userId);

    }


}
