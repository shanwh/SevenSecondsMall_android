package com.yidu.sevensecondmall.Activity.Distribution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.TeamInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 已弃用
 */
public class MemberListActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //item_member_list
    //item_member_list_add
    MultiTypeAdapter adapter;
    ArrayList<Visitable> list = new ArrayList<>();
    int type;
    @BindView(R.id.if_tv_add)
    IconFontTextView ifTvAdd;

    int id;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    @Override
    protected int setViewId() {
        return R.layout.activity_member_list;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("我推荐的人");
        toolbarRight.setBackground(getResources().getDrawable(R.drawable.toolbar_add));
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistributionDAO.getTeamInfo(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        TeamInfo info = (TeamInfo) data;
                        String routerURL = "partner" + "/" + info.getId();

                        Bitmap bitmap = EncodingUtils.createQRCode(routerURL, 500, 500, null);

                        showPopupWindow(toolbarRight, bitmap);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(MemberListActivity.this, data + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MemberListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter(list, MemberListActivity.this);
        rv.setAdapter(adapter);

        id = getIntent().getIntExtra("id", 1);
        type = getIntent().getIntExtra("type", 0);

        switch (type) {
            case 1:
                tvTitle.setText("团队成员");
                break;
            case 2:
                tvTitle.setText("我推荐的团队");
                ifTvAdd.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void loadData() {
        switch (type) {
            case 0:
                DistributionDAO.getMyRecommend(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        list = (ArrayList<Visitable>) data;
                        adapter.refreshData(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(MemberListActivity.this, data + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1:
                DistributionDAO.getMyTeamMember(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        list = (ArrayList<Visitable>) data;
                        adapter.refreshData(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(MemberListActivity.this, data + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                DistributionDAO.getMyInviteTeam(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        list = (ArrayList<Visitable>) data;
                        adapter.refreshData(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(MemberListActivity.this, data + "", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @OnClick({R.id.back, R.id.if_tv_add})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.if_tv_add:
                DistributionDAO.getTeamInfo(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        TeamInfo info = (TeamInfo) data;
                        String routerURL = "partner" + "/" + info.getId();

                        Bitmap bitmap = EncodingUtils.createQRCode(routerURL, 500, 500, null);

                        showPopupWindow(view, bitmap);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(MemberListActivity.this, data + "", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }

    PopupWindow popupWindow;

    private void showPopupWindow(View view, Bitmap bitmap) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_code_window, null);
        // 设置按钮的点击事件

        ImageView iv = (ImageView) contentView.findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.alertdiag_bg));

        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

}
