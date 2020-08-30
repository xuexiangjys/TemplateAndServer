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

package com.xuexiang.server;

/**
 * 服务状态监听
 *
 * @author xuexiang
 * @since 2020/8/30 3:01 AM
 */
public interface OnServerStatusListener {

    /**
     * 服务开启
     *
     * @param ip 服务器的地址
     */
    void onServerStart(String ip);

    /**
     * 服务出错
     *
     * @param message 出错信息
     */
    void onServerError(String message);

    /**
     * 服务停止
     */
    void onServerStop();


}
