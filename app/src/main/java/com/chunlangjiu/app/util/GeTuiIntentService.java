package com.chunlangjiu.app.util;

import android.content.Context;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.socks.library.KLog;

/**
 * @CreatedbBy: liucun on 2018/7/24
 * @Describe: 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId  接收cid <br>
 * onReceiveOnlineState  cid离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class GeTuiIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    /**
     * 接收cid
     */
    @Override
    public void onReceiveClientId(Context context, String cid) {
        KLog.e("--clientId---", cid);
    }

    /**
     * 处理透传消息
     *
     * @param context
     * @param gtTransmitMessage
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

}
