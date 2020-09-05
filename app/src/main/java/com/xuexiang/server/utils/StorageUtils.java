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

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.xuexiang.xutil.file.FileUtils;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yanzhenjie.andserver.util.IOUtils;
import com.yanzhenjie.andserver.util.StringUtils;

import java.io.File;
import java.util.UUID;

/**
 * 存储工具类
 *
 * @author xuexiang
 * @since 2020/9/6 12:40 AM
 */
public final class StorageUtils {

    private StorageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static File mRootDir;

    public static void initRootPath(Context context) {
        if (mRootDir != null) {
            return;
        }

        if (FileUtils.isSDCardExist()) {
            mRootDir = Environment.getExternalStorageDirectory();
        } else {
            mRootDir = context.getFilesDir();
        }
        mRootDir = new File(mRootDir, "AndServer");
        IOUtils.createFolder(mRootDir);
    }

    public static String getDateBasePath() {
        return new File(mRootDir, "databases").getAbsolutePath();
    }

    public static File getRootDir() {
        return mRootDir;
    }

    public static File getUploadDir(String dirPath) {
        return new File(mRootDir, dirPath);
    }

    /**
     * Create a random file based on mimeType.
     *
     * @param file file.
     * @return file object.
     */
    public static File createRandomFile(MultipartFile file, String dirPath) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(file.getContentType().toString());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeMap.getFileExtensionFromUrl(file.getFilename());
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + extension;
        File uploadDir = getUploadDir(dirPath);
        IOUtils.createFolder(uploadDir);
        return new File(uploadDir, fileName);
    }

    public static String getServerSaveFile(File file) {
        String rootPath = getRootDir().getAbsolutePath();
        String filePath = file.getAbsolutePath();
        return filePath.replaceFirst(rootPath, "");
    }

}
