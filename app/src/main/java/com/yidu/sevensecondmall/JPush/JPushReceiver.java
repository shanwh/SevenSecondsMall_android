package com.yidu.sevensecondmall.JPush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private final String OrderDelivery = "order_delivery";//订单发货
    String userid = "";
    String teamid = "";
    String matchid = "";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            try {
                String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.e(TAG, "[MyReceiver]: EXTRA_EXTRA"+string );
                JSONObject jsonObject = new JSONObject(string);

                if (jsonObject.has("type")){
                    if (jsonObject.getString("type").equals("1") ||jsonObject.getString("type").equals("2")) {
                        String value = jsonObject.getString("value");
                        Log.e(TAG, "[MyReceiver]:value " + value);
//                JSONObject vobj = new JSONObject(value);
                        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                        String msgobj = bundle.getString(JPushInterface.EXTRA_TITLE);
                        Log.e(TAG, "onReceive:EXTRA_TITLE " + msgobj);
                        ArrayList<String> list = new ArrayList<>();
                        list.add(jsonObject.getString("type"));
                        list.add(value);
                        list.add(msgobj);
                        processCustomMessage(context, list);
                    }
                }else {
                    String value = jsonObject.getString("value");
                    JSONObject vobj = new JSONObject(value);

                    String msgobj = bundle.getString(JPushInterface.EXTRA_TITLE);
                    processCustomMessage(context, msgobj);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Intent skipIntent = null;
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
//            try {
//                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                JSONObject value = jsonObject.getJSONObject("value");
//                String teamid = value.getString("team_id");
//                Log.e("team_id",teamid);
//                String user_id = value.getString("user_id");
//                Log.e("user_id",user_id);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


            //打开自定义的Activity
//            Intent i = new Intent(context, TestActivity.class);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, ArrayList<String> title) {
        Intent msgIntent = new Intent();
        msgIntent.setAction("com.yidu.sevensecondmall.FREE_MESSAGE_RECEIVED_ACTION");
        msgIntent.putExtra("title", title.get(0));
        msgIntent.putExtra("icon", title.get(1));
        msgIntent.putExtra("name", title.get(2));
        context.sendBroadcast(msgIntent);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, String title) {
        Intent msgIntent = new Intent();
        msgIntent.setAction("com.yidu.sevensecondmall.MESSAGE_RECEIVED_ACTION");
        msgIntent.putExtra("title", title);
        context.sendBroadcast(msgIntent);
    }

    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }


}
