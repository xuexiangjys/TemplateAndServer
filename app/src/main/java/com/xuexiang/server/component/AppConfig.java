/*
 * Copyright © 2019 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuexiang.server.component;

import android.content.Context;

import com.xuexiang.server.ServerConfig;
import com.xuexiang.xutil.file.FileUtils;
import com.yanzhenjie.andserver.annotation.Config;
import com.yanzhenjie.andserver.framework.config.Multipart;
import com.yanzhenjie.andserver.framework.config.WebConfig;
import com.yanzhenjie.andserver.framework.website.AssetsWebsite;

/**
 * web页面配置
 *
 * @author xuexiang
 * @since 2020/8/30 3:51 AM
 */
@Config
public class AppConfig implements WebConfig {

    @Override
    public void onConfig(Context context, Delegate delegate) {
        delegate.addWebsite(new AssetsWebsite(context, ServerConfig.WEB_ASSENTS));

        delegate.setMultipart(Multipart.newBuilder()
                .allFileMaxSize(ServerConfig.Upload.ALL_FILE_MAX_SIZE)
                .fileMaxSize(ServerConfig.Upload.SINGLE_FILE_MAX_SIZE)
                .maxInMemorySize(ServerConfig.Upload.MAX_IN_MEMORY_SIZE)
                .uploadTempDir(FileUtils.getFileByPath(FileUtils.getDiskCacheDir(ServerConfig.Upload.CACHE_DIR)))
                .build());
    }
}