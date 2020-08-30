/*
 * Copyright © 2018 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuexiang.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xuexiang.xutil.app.BroadcastUtils;

/**
 * 服务管理者
 *
 * @author xuexiang
 * @since 2020/8/30 2:53 AM
 */
public class ServerManager extends BroadcastReceiver {

    private static final String ACTION = "com.xuexiang.server.receiver";

    private static final String CMD_KEY = "cmd_key";
    private static final String MESSAGE_KEY = "message_key";

    private static final int CMD_VALUE_START = 1;
    private static final int CMD_VALUE_ERROR = 2;
    private static final int CMD_VALUE_STOP = 4;

    /**
     * Notify serverStart.
     *
     * @param context context.
     */
    public static void onServerStart(Context context, String hostAddress) {
        sendBroadcast(context, CMD_VALUE_START, hostAddress);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public static void onServerError(Context context, String error) {
        sendBroadcast(context, CMD_VALUE_ERROR, error);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public static void onServerStop(Context context) {
        sendBroadcast(context, CMD_VALUE_STOP);
    }

    private static void sendBroadcast(Context context, int cmd) {
        sendBroadcast(context, cmd, null);
    }

    private static void sendBroadcast(Context context, int cmd, String message) {
        Intent broadcast = new Intent(ACTION);
        broadcast.putExtra(CMD_KEY, cmd);
        broadcast.putExtra(MESSAGE_KEY, message);
        context.sendBroadcast(broadcast);
    }

    private OnServerStatusListener mOnServerStatusListener;
    private Context mContext;
    private Intent mService;

    public ServerManager(Context context, OnServerStatusListener listener) {
        mContext = context;
        mOnServerStatusListener = listener;
        mService = new Intent(mContext, CoreService.class);
    }

    /**
     * 注册广播
     */
    public void register() {
        BroadcastUtils.registerReceiver(mContext, this, ACTION);
    }

    /**
     * 注销广播
     */
    public void unRegister() {
        BroadcastUtils.unregisterReceiver(mContext, this);
    }

    public void startServer() {
        mContext.startService(mService);
    }

    public void stopServer() {
        mContext.stopService(mService);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION.equals(action)) {
            int cmd = intent.getIntExtra(CMD_KEY, 0);
            switch (cmd) {
                case CMD_VALUE_START:
                    String ip = intent.getStringExtra(MESSAGE_KEY);
                    mOnServerStatusListener.onServerStart(ip);
                    break;
                case CMD_VALUE_ERROR:
                    String error = intent.getStringExtra(MESSAGE_KEY);
                    mOnServerStatusListener.onServerError(error);
                    break;
                case CMD_VALUE_STOP:
                    mOnServerStatusListener.onServerStop();
                    break;
                default:
                    break;
            }
        }
    }


}