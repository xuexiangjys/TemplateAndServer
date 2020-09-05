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

import com.xuexiang.server.utils.StorageUtils;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author xuexiang
 * @since 2020/9/6 12:37 AM
 */
@RestController
@RequestMapping(path = "/file")
public class FileController {

    @PostMapping(path = "/upload")
    String upload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "type") String type) throws IOException {
        File localFile = StorageUtils.createRandomFile(file, type);
        file.transferTo(localFile);
        return StorageUtils.getServerSaveFile(localFile);
    }


}
