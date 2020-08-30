/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.templateandserver.fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuexiang.server.OnServerStatusListener;
import com.xuexiang.server.ServerManager;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.templateandserver.utils.Utils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xutil.XUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:16
 */
@Page(name = "模版服务器", anim = CoreAnim.none)
public class MainFragment extends BaseFragment implements OnServerStatusListener {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_browse)
    Button btnBrowse;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private ServerManager mServerManager;
    private String mRootUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews() {
        mServerManager = new ServerManager(getContext(), this);
        mServerManager.register();
        // 开启服务
        btnStart.performClick();
    }

    @SingleClick
    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_browse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                getMessageLoader().show();
                mServerManager.startServer();
                break;
            case R.id.btn_stop:
                getMessageLoader().show();
                mServerManager.stopServer();
                break;
            case R.id.btn_browse:
                if (!TextUtils.isEmpty(mRootUrl)) {
                    Utils.goWeb(this, mRootUrl);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onServerStart(String ip) {
        getMessageLoader().dismiss();
        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        btnBrowse.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(ip)) {
            List<String> addressList = new LinkedList<>();
            mRootUrl = "http://" + ip + ":8080/";
            addressList.add("服务器启动成功，可访问下方链接:");
            addressList.add(mRootUrl);
            addressList.add("http://" + ip + ":8080/login.html");
            tvMessage.setText(TextUtils.join("\n", addressList));
        } else {
            mRootUrl = null;
            tvMessage.setText(R.string.server_ip_error);
        }
    }

    @Override
    public void onServerError(String message) {
        getMessageLoader().dismiss();
        mRootUrl = null;
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
        btnBrowse.setVisibility(View.GONE);
        tvMessage.setText(message);
    }

    @Override
    public void onServerStop() {
        getMessageLoader().dismiss();
        mRootUrl = null;
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
        btnBrowse.setVisibility(View.GONE);
        tvMessage.setText(R.string.server_stop_succeed);
    }


    @Override
    public void onDestroyView() {
        mServerManager.unRegister();
        super.onDestroyView();
    }

    @Override
    protected TitleBar initTitle() {
        return super.initTitle().setLeftClickListener(view -> onBackPress());
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPress();
        }
        return true;
    }

    private void onBackPress() {
        DialogLoader.getInstance().showConfirmDialog(
                getContext(),
                "退出将关闭服务，是否继续？",
                "继续",
                (dialog, which) -> {
                    dialog.dismiss();
                    XUtil.exitApp();
                },
                "不了",
                (dialog, which) -> dialog.dismiss()
        );
    }

}
