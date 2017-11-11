package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 * 名字编辑界面
 */
public class EditActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.delete)
    IconFontTextView delete;
    @BindView(R.id.edittext)
    EditText edittext;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = edittext.getText().length();
                if (length > 0) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void init() {
        titleName.setText("修改昵称");
        toolbarTitle.setText("修改昵称");
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.back, R.id.save, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
//                Intent i = new Intent(EditActivity.this,SettingActivity.class);
//                i.putExtra("name",edittext.getText().toString());
//                startActivity(i);
                finish();
//                startActivityForResult(i,0);
                break;
            case R.id.save:
                Intent i = new Intent(EditActivity.this, SettingActivity.class);
                i.putExtra("name", edittext.getText().toString());
                i.putExtra("changeName", true);
                startActivity(i);
                finish();
                break;
            case R.id.delete:
                edittext.setText("");
                break;
        }
    }

}
