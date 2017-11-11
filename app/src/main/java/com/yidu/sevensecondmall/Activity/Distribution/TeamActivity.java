package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.TeamInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 已弃用
 */
public class TeamActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_team_num)
    TextView tvTeamNum;
    @BindView(R.id.tv_sell_value)
    TextView tvSellValue;
    @BindView(R.id.tv_sell_present)
    TextView tvSellPresent;

    Context context;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_team;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        context = TeamActivity.this;
        DistributionDAO.getTeamInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                TeamInfo info = (TeamInfo) data;
                tvTitle.setText(info.getName());
                Glide.with(context)
                        .load(HttpApi.getFullImageUrl(info.getLogo()))
                        .placeholder(R.drawable.default_loading_pic)
                        .transform(new GlideCircleTransform(context))
                        .into(iv);
                String s = "领队：" + info.getUserinfo().getNickname();
                tvName.setText(s);
                toolbarTitle.setText("s");
                tvId.setText(info.getTeam_id());
                tvTime.setText(TimeFormatUtils.format(Long.parseLong(info.getCreatetime())));
                tvAddress.setText(info.getCityinfo().getMergename());
                tvNum.setText(info.getMembers());
                tvTeamNum.setText(info.getExtension_team_nums());
                tvSellValue.setText(info.getSales_volume());
                tvSellPresent.setText(info.getSales_rebate());
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, data + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
