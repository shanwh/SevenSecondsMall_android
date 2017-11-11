package com.yidu.sevensecondmall.utils;

import android.app.Activity;

import com.yidu.sevensecondmall.Activity.Video.LiveActivity;

import java.util.Random;


public class PlayerControl {

    public static final int CMD_START = 1;
    public static final int CMD_STOP = 2;
    public static final int CMD_PAUSE = 3;
    public static final int CMD_RESUME = 4;
    public static final int CMD_SEEK = 5;
    public static final int CMD_VOLUME = 6;


    public static final int THREAD_START = 1;
    public static final int THREAD_STOP = 2;

    public interface ControllerListener {

        void notifyController(int cmd, int extra);
    }


    private ControllerListener mControllerListener;

    public void setControllerListener(ControllerListener listener) {
        mControllerListener = listener;
    }

    private Thread mThread;
    private Random mRandom;

    private int mStatus;
    private int mThreadStatus;

    private String getStatus(int status) {
        switch (status) {
            case LiveActivity.STATUS_PAUSE:
                return "pause";
            case LiveActivity.STATUS_RESUME:
                return "resume";
            case LiveActivity.STATUS_START:
                return "start";
            case LiveActivity.STATUS_STOP:
                return "stop";
        }

        return "null";
    }

    private StatusListener mStatusListerner = new StatusListener() {

        @Override
        public int notifyStatus(int status) {
            // TODO Auto-generated method stub
            mStatus = status;

            return 0;
        }
    };

    public PlayerControl(Activity activity) {
        mRandom = new Random();

        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                int cmd;
                int volume = 50;
                int seekPos = 40000;
                int sleepTimes;
                while (true) {

                    if (mThreadStatus == THREAD_STOP) {
                        break;
                    }

                    sleepTimes = mRandom.nextInt(10);

                    try {
                        Thread.sleep((sleepTimes + 7) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    switch (mStatus) {
                        case LiveActivity.STATUS_PAUSE:
                            cmd = mRandom.nextInt(4);
                            switch (cmd) {
                                case 0:
                                    mControllerListener.notifyController(CMD_RESUME, 0);
                                    break;
                                case 1:
                                    seekPos = mRandom.nextInt(10800000) + 1;
                                    mControllerListener.notifyController(CMD_SEEK, seekPos);
                                    break;
                                case 2:
                                    mControllerListener.notifyController(CMD_STOP, 0);
                                    break;
                                case 3:
                                    mControllerListener.notifyController(CMD_VOLUME, volume);
                                    break;
                            }
                            break;
                        case LiveActivity.STATUS_RESUME:
                        case LiveActivity.STATUS_START:
                            cmd = mRandom.nextInt(4);
                            switch (cmd) {
                                case 0:
                                    mControllerListener.notifyController(CMD_PAUSE, 0);
                                    break;
                                case 1:
                                    mControllerListener.notifyController(CMD_STOP, 0);
                                    break;
                                case 2:

                                    seekPos = mRandom.nextInt(10800000) + 1;
                                    mControllerListener.notifyController(CMD_SEEK, seekPos);
                                    break;
                                case 3:
                                    volume = mRandom.nextInt(100);
                                    mControllerListener.notifyController(CMD_VOLUME, volume);
                                    break;
                            }
                            break;

                        case LiveActivity.STATUS_STOP:
                            mControllerListener.notifyController(CMD_START, 0);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        mThread.setName("player_control");

        if(activity instanceof LiveActivity){
            ((LiveActivity)activity).setStatusListener(mStatusListerner);
        }

    }

    public void start() {
        mThreadStatus = THREAD_START;
        mThread.start();
    }

    public void stop() {
        mThreadStatus = THREAD_STOP;
    }
}


