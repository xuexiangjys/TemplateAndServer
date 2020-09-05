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
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.common.StringUtils;

import java.sql.SQLException;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author xuexiang
 * @since 2020/9/5 5:11 PM
 */
@Page
public class RegisterFragment extends BaseFragment {

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
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setTitle("账户注册");
        titleBar.addAction(new TitleBar.TextAction("提交") {
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

        User user = new User().setLoginName(etLoginName.getEditValue())
                .setPassword(etPassword.getEditValue())
                .setName(etName.getEditValue())
                .setAge(StringUtils.toInt(etAge.getEditValue()))
                .setGender(msGender.getSelectedIndex())
                .setPhone(etPhone.getEditValue());


        if (saveUser(user)) {
            XToastUtils.success("注册成功");
            setFragmentResult(RESULT_OK, null);
            popToBack();
        } else {
            XToastUtils.success("注册失败");
        }
    }

    @Override
    protected void initViews() {

    }

    private boolean validateInput() {
        return etLoginName.validate() && etPassword.validate() && etAge.validate() && etPhone.validate();
    }

    public boolean saveUser(User user) {
        DBService<User> dbService = AndServerDataBaseRepository.getInstance().getDataBase(User.class);
        try {
            return dbService.insert(user) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
