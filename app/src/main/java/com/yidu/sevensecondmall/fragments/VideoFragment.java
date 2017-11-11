package com.yidu.sevensecondmall.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.yidu.sevensecondmall.Activity.Video.VideoActivity;
import com.yidu.sevensecondmall.R;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class VideoFragment extends BaseFragment {

    @BindView(R.id.iv_scale)
    ImageView iv;

//    @BindView(R.id.iv_scale1)
//    ImageView iv1;
//    @BindView(R.id.iv_scale3)
//    ImageView iv3;
    @Override
    protected int setViewId() {
        return R.layout.fragment_video2;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void init() {
        //iv.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.videohome, 720, 1024));
    iv.setImageBitmap(readBitMap(getActivity(),R.drawable.videohome));
//        iv1.setImageBitmap(readBitMap(getActivity(),R.drawable.m2));
//        iv3.setImageBitmap(readBitMap(getActivity(),R.drawable.m3));
    }
    @OnClick({R.id.iv_scale})
//            ,R.id.iv_scale1,
//            R.id.iv_scale3})
    public void click(View v){
        switch (v.getId()) {
            case R.id.iv_scale:
                Intent i = new Intent(getActivity(), VideoActivity.class);
                i.putExtra("position",0);
                startActivity(i);
                break;
//            case R.id.iv_scale1:
//                Intent i1 = new Intent(getActivity(), VideoActivity.class);
//                i1.putExtra("position",1);
//                startActivity(i1);
//                break;
//            case R.id.iv_scale3:
//                Intent i3 = new Intent(getActivity(), VideoActivity.class);
//                i3.putExtra("position",3);
//                startActivity(i3);
//                break;

        }
    }
    public  Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    @Override
    protected void loadData() {

    }
}

