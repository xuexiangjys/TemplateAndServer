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

package com.xuexiang.server.utils;

import androidx.annotation.NonNull;

import com.xuexiang.server.api.base.ApiException;
import com.xuexiang.server.api.base.ApiResult;
import com.xuexiang.xutil.net.JsonUtil;

/**
 * 接口工具类
 *
 * @author xuexiang
 * @since 2020/8/30 10:56 PM
 */
public final class ApiUtils {

    private ApiUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取出错返回
     *
     * @param ex 错误信息实体
     * @return 出错返回
     */
    public static ApiResult error(@NonNull ApiException ex) {
        ApiResult apiResult = new ApiResult();
        apiResult.setError(ex.getCode(), ex.getMessage());
        return apiResult;
    }

    /**
     * 获取出错返回
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return 出错返回
     */
    public static ApiResult error(int code, String msg) {
        ApiResult apiResult = new ApiResult();
        apiResult.setError(code, msg);
        return apiResult;
    }

    /**
     * Business is successful.
     *
     * @param data return data.
     * @return json.
     */
    public static <T> String successfulJson(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(data);
        return JsonUtil.toJson(apiResult);
    }

    /**
     * Business is failed.
     *
     * @param code    error code.
     * @param message message.
     * @return json.
     */
    public static String failedJson(int code, String message) {
        return JsonUtil.toJson(error(code, message));
    }

}
