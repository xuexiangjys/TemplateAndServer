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

package com.xuexiang.templateandserver.fragment.manage;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.server.model.User;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.adapter.UserManageAdapter;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xormlite.AndServerDataBaseRepository;
import com.xuexiang.xormlite.db.DBService;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.common.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author xuexiang
 * @since 2020/9/5 4:02 PM
 */
@Page(name = "数据管理后台")
public class ServerManageFragment extends BaseFragment {

    public static final int REQUEST_REGISTER = 100;
    public static final int REQUEST_EDIT = 101;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private DBService<User> mDBService;
    private UserManageAdapter mAdapter;

    private int mPageIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_manage;
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.addAction(new TitleBar.TextAction("新增") {
            @SingleClick
            @Override
            public void performAction(View view) {
                openPageForResult(RegisterFragment.class, REQUEST_REGISTER);
            }
        });
        return titleBar;
    }

    @Override
    protected void initArgs() {
        mDBService = AndServerDataBaseRepository.getInstance().getDataBase(User.class);
    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new UserManageAdapter());
    }

    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mAdapter.refresh(pageQuery(mPageIndex = 0));
            refreshLayout.finishRefresh();
        }, 500));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            mPageIndex++;
            List<User> userList = pageQuery(mPageIndex);
            if (CollectionUtils.isNotEmpty(userList)) {
                mAdapter.loadMore(userList);
            } else {
                mPageIndex--;
            }
            refreshLayout.finishLoadMore();
        }, 500));
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
        openPageForResult(EditUserFragment.class, "user", item, REQUEST_EDIT);

    }

    private void deleteUser(User item) {
        try {
            boolean result = mDBService.deleteData(item) > 0;
            if (result) {
                autoRefresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    private List<User> pageQuery(int pageIndex) {
        try {
            return mDBService.queryPage(pageIndex, 10, "Id", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
