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

package com.xuexiang.templateandserver.fragment.net;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.rxutil2.lifecycle.RxLifecycle;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.templateandserver.core.http.subscriber.TipProgressLoadingSubscriber;
import com.xuexiang.templateandserver.utils.XToastUtils;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.file.FileUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xaop.consts.PermissionConsts.STORAGE;

/**
 * @author xuexiang
 * @since 2020/9/6 1:15 AM
 */
@Page(name = "文件上传")
public class FileUploadFragment extends BaseFragment {

    public static final int REQUEST_PICK = 100;

    @BindView(R.id.tv_upload_path)
    TextView tvUploadPath;
    @BindView(R.id.tv_save_path)
    TextView tvSavePath;

    String mUploadPath;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file_upload;
    }

    @Override
    protected void initViews() {

    }

    @SingleClick
    @OnClick({R.id.bth_select, R.id.bth_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bth_select:
                selectFile();
                break;
            case R.id.bth_upload:
                uploadFile();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择系统图片并解析
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    mUploadPath = PathUtils.getFilePathByUri(uri);
                    tvUploadPath.setText(mUploadPath);
                    tvSavePath.setText("");
                }
            }
        }
    }

    @Permission(STORAGE)
    private void selectFile() {
        startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.ANY), REQUEST_PICK);
    }

    @SuppressLint("CheckResult")
    private void uploadFile() {
        if (StringUtils.isEmpty(mUploadPath)) {
            ToastUtils.toast("请先选择需要上传的文件!");
            selectFile();
            return;
        }
        getProgressLoader().updateMessage("上传中...");
        XHttp.post("/file/upload")
                .params("type", "common")
                .uploadFile("file", FileUtils.getFileByPath(mUploadPath), (bytesWritten, contentLength, done) -> {

                }).execute(String.class)
                .compose(RxLifecycle.with(this).bindToLifecycle())
                .subscribeWith(new TipProgressLoadingSubscriber<String>(this) {
                    @Override
                    public void onSuccess(String savePath) {
                        XToastUtils.success("文件上传成功");
                        tvSavePath.setText(savePath);
                    }
                });
    }


}
