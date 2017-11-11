package com.yidu.sevensecondmall.utils;


import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.IBottom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UpdateManager {

    private final Context mContext;
    private IBottom iBottom;
    // 提示语

    private final String updateMsg = "有最新的软件包哦.亲快下载吧~";

    // 返回的安装包url

    private String apkUrl = null;

    private Dialog noticeDialog;

    private Dialog downloadDialog;

    /* 下载包安装路径 */

    private static final String savePath = "/mnt/sdcard/";

    private static final String saveFileName = savePath + "DING网.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */

    private ProgressBar mProgress;
    private int preprogress = -1;
    private TextView m_textPer;

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    public int SetApkUrl(final String url) {
        apkUrl = url;
        return 0;
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {

                case DOWN_UPDATE:
                    if (preprogress == progress) break;
                    preprogress = progress;
                    mProgress.setProgress(progress);
                    m_textPer.setText(String.valueOf(progress) + "%");

                    break;

                case DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk();

                    break;

                default:

                    break;

            }

        }

        ;

    };


    public UpdateManager(final Context context) {

        this.mContext = context;

    }

    // 外部接口让主Activity调用

    public void checkUpdateInfo() {

        showNoticeDialog();

    }


    private void showNoticeDialog() {
        showDownloadDialog();
    }

    private void showDownloadDialog() {

        Builder builder = new Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("DING网正在更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        m_textPer = (TextView) v.findViewById(R.id.textPer);
        builder.setView(v);


        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                // GL2JNIActivity.ShowExitCommitDialog(mContext);
//		android.os.Process.killProcess(android.os.Process.myPid());
                interceptFlag = true;
                iBottom.isBottom(true);

            }

        });

        downloadDialog = builder.create();

        downloadDialog.show();

        downloadApk();

    }

    public void setI(IBottom i) {
        iBottom = i;
    }

    private final Runnable mdownApkRunnable = new Runnable() {

        @Override
        public void run() {

            try {

                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();

                conn.connect();

                int length = conn.getContentLength();

                InputStream is = conn.getInputStream();

                File file = new File(savePath);

                if (!file.exists()) {

                    file.mkdir();

                }

                String apkFile = saveFileName;

                File ApkFile = new File(apkFile);

                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;

                byte buf[] = new byte[1024];

                do {

                    int numread = is.read(buf);

                    count += numread;

                    progress = (int) (((float) count / length) * 100);

                    // 更新进度

                    mHandler.sendEmptyMessage(DOWN_UPDATE);

                    if (numread <= 0) {

                        // 下载完成通知安装

                        mHandler.sendEmptyMessage(DOWN_OVER);

                        break;

                    }

                    fos.write(buf, 0, numread);

                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();

                is.close();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    };

    /**
     * 下载apk
     */
    private static void deleteConfigFile(final String filepath) {
        File configFile = new File(filepath);
        if (configFile.exists()) {
            configFile.delete();
        }
    }

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */

    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(i);

    }

}