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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuexiang.server.OnServerStatusListener;
import com.xuexiang.server.ServerManager;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.templateandserver.fragment.manage.ServerManageFragment;
import com.xuexiang.templateandserver.fragment.net.ServerTestFragment;
import com.xuexiang.templateandserver.utils.Utils;
import com.xuexiang.templateandserver.utils.XToastUtils;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.net.NetworkUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.xaop.consts.PermissionConsts.STORAGE;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:16
 */
@Page(name = "模版服务器", anim = CoreAnim.none)
public class MainFragment extends BaseFragment implements OnServerStatusListener {

    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_service)
    LinearLayout llService;

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
    }

    @Override
    protected void initListeners() {
        // 开启服务
        startServer();
    }

    @SingleClick
    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_browse, R.id.btn_manage, R.id.btn_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startServer();
                break;
            case R.id.btn_stop:
                getProgressLoader().showLoading();
                mServerManager.stopServer();
                break;
            case R.id.btn_browse:
                if (!TextUtils.isEmpty(mRootUrl)) {
                    Utils.goWeb(this, mRootUrl);
                }
                break;
            case R.id.btn_manage:
                openPage(ServerManageFragment.class);
                break;
            case R.id.btn_test:
                confirmIpAddress();
                break;
            default:
                break;
        }
    }

    @Permission(STORAGE)
    private void startServer() {
        getProgressLoader().showLoading();
        mServerManager.startServer();
    }

    private void confirmIpAddress() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.tip_warning)
                .content(R.string.content_warning)
                .inputRange(10, 50)
                .input(getString(R.string.hint_please_input_server_ip_address), mRootUrl, false, (dialog, input) -> {
                    String url = input.toString();
                    if (NetworkUtils.isUrlValid(url)) {
                        dialog.dismiss();
                        gotoServerTest(url);
                    } else {
                        XToastUtils.error(R.string.tip_url_input_error);
                    }
                })
                .positiveText(R.string.lab_continue)
                .negativeText(R.string.lab_cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .cancelable(false)
                .autoDismiss(false)
                .show();
    }

    private void gotoServerTest(String url) {
        // 先设置服务器的地址
        XHttpSDK.setBaseUrl(url);
        openPage(ServerTestFragment.class);
    }

    @Override
    public void onServerStart(String ip) {
        getProgressLoader().dismissLoading();
        btnStart.setVisibility(View.GONE);
        llService.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(ip)) {
            List<String> addressList = new LinkedList<>();
            mRootUrl = "http://" + ip + ":8080/";
            addressList.add("服务器启动成功，可访问下方链接:");
            addressList.add(mRootUrl);
            addressList.add("http://" + ip + ":8080/login.html");
            addressList.add("http://" + ip + ":8080/register.html");
            tvMessage.setText(TextUtils.join("\n", addressList));
        } else {
            mRootUrl = null;
            tvMessage.setText(R.string.server_ip_error);
        }
    }

    @Override
    public void onServerError(String message) {
        getProgressLoader().dismissLoading();
        mRootUrl = null;
        btnStart.setVisibility(View.VISIBLE);
        llService.setVisibility(View.GONE);
        tvMessage.setText(message);
    }

    @Override
    public void onServerStop() {
        getProgressLoader().dismissLoading();
        mRootUrl = null;
        btnStart.setVisibility(View.VISIBLE);
        llService.setVisibility(View.GONE);
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
