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

package com.xuexiang.templateandserver.adapter;

import androidx.annotation.NonNull;

import com.xuexiang.server.model.User;
import com.xuexiang.templateandserver.R;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

/**
 * @author xuexiang
 * @since 2020/9/5 4:51 PM
 */
public class UserManageAdapter extends BaseRecyclerAdapter<User> {

    private boolean mIsShowPassword;

    public UserManageAdapter() {
        this(true);
    }

    public UserManageAdapter(boolean isShowPassword) {
        mIsShowPassword = isShowPassword;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_user_list_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, User item) {
        if (item == null) {
            return;
        }
        holder.text(R.id.tv_user_id, String.valueOf(item.getId()));
        holder.text(R.id.tv_login_name, item.getLoginName());
        holder.text(R.id.tv_password, mIsShowPassword ? item.getPassword() : "****");
        holder.text(R.id.tv_user_name, item.getName());
        holder.text(R.id.tv_age, String.valueOf(item.getAge()));
        holder.text(R.id.tv_gender, item.getGenderName());
        holder.text(R.id.tv_phone, item.getPhone());
    }
}
