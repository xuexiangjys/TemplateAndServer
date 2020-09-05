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

import com.xuexiang.constant.MemoryConstants;

/**
 * 服务器配置
 *
 * @author xuexiang
 * @since 2020/8/30 2:51 AM
 */
public final class ServerConfig {

    private ServerConfig() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * web页面服务资源的加载路径
     */
    public static final String WEB_ASSENTS = "/web";

    /**
     * 服务器端口
     */
    public static final int SERVER_PORT = 8080;

    /**
     * 服务器响应超时时间(秒）
     */
    public static final int SERVER_TIMEOUT = 10;


    /**
     * 文件上传配置
     */
    public static final class Upload {
        /**
         * 文件上传的缓存目录
         */
        public static final String CACHE_DIR = "_server_upload_cache_";

        /**
         * 一次上传的所有文件的最大大小
         */
        public static final int ALL_FILE_MAX_SIZE = MemoryConstants.MB * 20;

        /**
         * 一次上传的单个文件的最大大小
         */
        public static final int SINGLE_FILE_MAX_SIZE = MemoryConstants.MB * 5;

        /**
         * 上传文件写入磁盘前读取的缓存池大小
         */
        public static final int MAX_IN_MEMORY_SIZE = MemoryConstants.KB * 10;
    }


}
