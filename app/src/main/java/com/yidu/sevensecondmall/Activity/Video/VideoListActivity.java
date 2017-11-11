package com.yidu.sevensecondmall.Activity.Video;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;
import com.yidu.sevensecondmall.views.adapter.VideoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListActivity extends BaseActivity {

    @BindView(R.id.rv_videolist)
    RecyclerView videoList;

    @Override
    protected int setViewId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void findViews() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoList.setLayoutManager(layoutManager);
        List<VideoListBean> datas  =null;// TODO: 2017/8/21
        videoList.setAdapter(new VideoAdapter(this,datas));
    }

    @Override
    protected void loadData() {

    }
}
