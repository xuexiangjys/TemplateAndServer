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
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.rxutil2.lifecycle.RxLifecycle;
import com.xuexiang.server.api.request.PageQuery;
import com.xuexiang.server.model.User;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.adapter.UserManageAdapter;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.templateandserver.core.http.TestApi;
import com.xuexiang.templateandserver.core.http.callback.NoTipCallBack;
import com.xuexiang.templateandserver.core.http.subscriber.TipProgressLoadingSubscriber;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.common.CollectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * @author xuexiang
 * @since 2020/9/5 4:11 PM
 */
@Page(name = "接口测试")
public class ServerTestFragment extends BaseFragment {

    public static final int REQUEST_REGISTER = 100;
    public static final int REQUEST_EDIT = 101;

    public static final int REFRESH = 0;
    public static final int LOAD_MODE = 1;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private UserManageAdapter mAdapter;

    private int mPageIndex = 0;

    private int mMode = REFRESH;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_server_test;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.addAction(new TitleBar.TextAction("注册") {
            @SingleClick
            @Override
            public void performAction(View view) {
                openPageForResult(RegisterTestFragment.class, REQUEST_REGISTER);
            }
        });
        return titleBar;
    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new UserManageAdapter(false));
    }

    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mMode = REFRESH;
            mPageIndex = 0;
            pageQuery(mPageIndex);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mMode = LOAD_MODE;
            mPageIndex++;
            pageQuery(mPageIndex);
        });
        //第一次进刷新
        autoRefresh();

        mAdapter.setOnItemLongClickListener((itemView, item, position) -> showMenuDialog(item));
    }

    private void showMenuDialog(User item) {
        new MaterialDialog.Builder(getContext())
                .title(R.string.tip_options)
                .items(R.array.menu_values)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if (position == 0) {
                        editUser(item);
                    } else {
                        deleteUser(item);
                    }
                })
                .show();
    }


    private void editUser(User item) {
        openPageForResult(EditUserTestFragment.class, "user", item, REQUEST_EDIT);
    }

    @SuppressLint("CheckResult")
    private void deleteUser(User item) {
        XHttpProxy.proxy(TestApi.UserService.class)
                .deleteUser(item.getId())
                .compose(RxLifecycle.with(this).bindToLifecycle())
                .subscribeWith(new TipProgressLoadingSubscriber<Boolean>(this) {
                    @Override
                    protected void onSuccess(Boolean aBoolean) {
                        autoRefresh();
                    }
                });
    }

    private void autoRefresh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            autoRefresh();
        }
    }

    /**
     * 分页查询
     *
     * @param pageIndex 页面索引
     * @return
     */
    private void pageQuery(int pageIndex) {
        XHttp.post("/user/queryUser")
                .upJson(new PageQuery(pageIndex, 10).toString())
                .execute(new NoTipCallBack<List<User>>() {
                    @Override
                    public void onSuccess(List<User> response) throws Throwable {
                        refreshAdapter(response);
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        refreshAdapter(null);
                    }
                });
    }

    private void refreshAdapter(List<User> response) {
        if (mMode == REFRESH) {
            mAdapter.refresh(response);
            if (refreshLayout != null) {
                refreshLayout.finishRefresh();
            }
        } else {
            if (CollectionUtils.isNotEmpty(response)) {
                mAdapter.loadMore(response);
            } else {
                mPageIndex--;
            }
            if (refreshLayout != null) {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @SingleClick
    @OnClick(R.id.fab_recycler_view)
    public void onViewClicked(View view) {
        openPage(FileUploadFragment.class);

    }


}
