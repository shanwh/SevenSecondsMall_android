package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 */
public class SelectSexActivity extends BaseActivity {
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.man)
    RadioButton man;
    //    @BindView(R.id.man_select)
//    ImageView manSelect;
    @BindView(R.id.woman)
    RadioButton woman;
    //    @BindView(R.id.woman_select)
//    ImageView womanSelect;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String sex = "";

    private Context context;

    @Override
    protected int setViewId() {
        return R.layout.activity_selectsex;
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
        context = SelectSexActivity.this;
        titleName.setText("修改性别");
        toolbarTitle.setText("修改性别");
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.man, R.id.woman, R.id.back})
    public void onClick(View view) {
        Intent i = new Intent(SelectSexActivity.this, SettingActivity.class);
        switch (view.getId()) {
            case R.id.man:
//                manSelect.setVisibility(View.VISIBLE);
//                womanSelect.setVisibility(View.INVISIBLE);
                man.setBackground(ContextCompat.getDrawable(context, R.drawable.to_buy_bg));
                man.setTextColor(ContextCompat.getColor(context, R.color.white));
                woman.setBackground(ContextCompat.getDrawable(context, R.drawable.button_bg));
                woman.setTextColor(ContextCompat.getColor(context, R.color.light_red));
                sex = "男";
                i.putExtra("sex", sex);
                startActivity(i);
                finish();
                break;
            case R.id.woman:
//                manSelect.setVisibility(View.INVISIBLE);
//                womanSelect.setVisibility(View.VISIBLE);
                woman.setBackground(ContextCompat.getDrawable(context, R.drawable.to_buy_bg));
                woman.setTextColor(ContextCompat.getColor(context, R.color.white));
                man.setBackground(ContextCompat.getDrawable(context, R.drawable.button_bg));
                man.setTextColor(ContextCompat.getColor(context, R.color.light_red));
                sex = "女";
                i.putExtra("sex", sex);
                startActivity(i);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

}
