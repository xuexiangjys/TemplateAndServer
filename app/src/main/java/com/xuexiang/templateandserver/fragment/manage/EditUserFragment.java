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

import android.view.View;

import com.xuexiang.server.model.User;
import com.xuexiang.templateandserver.R;
import com.xuexiang.templateandserver.core.BaseFragment;
import com.xuexiang.templateandserver.utils.XToastUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xormlite.AndServerDataBaseRepository;
import com.xuexiang.xormlite.db.DBService;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.common.StringUtils;

import java.sql.SQLException;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author xuexiang
 * @since 2020/9/5 6:31 PM
 */
@Page
public class EditUserFragment extends BaseFragment {

    @AutoWired
    User user;

    @BindView(R.id.et_login_name)
    MaterialEditText etLoginName;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.et_name)
    MaterialEditText etName;
    @BindView(R.id.et_age)
    MaterialEditText etAge;
    @BindView(R.id.ms_gender)
    MaterialSpinner msGender;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info_manage;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setTitle("账户编辑");
        titleBar.addAction(new TitleBar.TextAction("保存") {
            @SingleClick
            @Override
            public void performAction(View view) {
                handleSubmit();
            }
        });
        return titleBar;
    }

    private void handleSubmit() {
        if (!validateInput()) {
            return;
        }

        user.setLoginName(etLoginName.getEditValue())
                .setPassword(etPassword.getEditValue())
                .setName(etName.getEditValue())
                .setAge(StringUtils.toInt(etAge.getEditValue()))
                .setGender(msGender.getSelectedIndex())
                .setPhone(etPhone.getEditValue());

        if (saveUser(user)) {
            XToastUtils.success("编辑成功");
            setFragmentResult(RESULT_OK, null);
            popToBack();
        } else {
            XToastUtils.success("编辑失败");
        }
    }

    @Override
    protected void initViews() {
        etLoginName.setText(StringUtils.getString(user.getLoginName()));
        etPassword.setText(StringUtils.getString(user.getPassword()));
        etName.setText(StringUtils.getString(user.getName()));
        etAge.setText(String.valueOf(user.getAge()));
        msGender.setSelectedIndex(user.getGender());
        etPhone.setText(StringUtils.getString(user.getPhone()));
    }


    private boolean validateInput() {
        return etLoginName.validate() && etPassword.validate() && etAge.validate() && etPhone.validate();
    }

    public boolean saveUser(User user) {
        DBService<User> dbService = AndServerDataBaseRepository.getInstance().getDataBase(User.class);
        try {
            return dbService.updateData(user) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
