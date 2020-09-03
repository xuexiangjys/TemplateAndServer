package com.xuexiang.server.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 用户数据库表
 *
 * @author xuexiang
 * @since 2020/8/31 12:22 AM
 */
@DatabaseTable(tableName = "User")
public class User {
    public static final String KEY_ID = "Id";
    public static final String KEY_LOGIN_NAME = "loginName";
    public static final String KEY_PASSWORD = "password";

    @DatabaseField(generatedId = true)
    private long Id;

    @DatabaseField
    private String loginName;

    @DatabaseField
    private String password;

    @DatabaseField
    private String name;

    @DatabaseField
    private int gender;

    @DatabaseField
    private int age;

    @DatabaseField
    private String phone;

    public long getId() {
        return Id;
    }

    public User setId(long id) {
        Id = id;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public User setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public User setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}