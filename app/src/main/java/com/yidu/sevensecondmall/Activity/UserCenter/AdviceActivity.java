package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.PicAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by Administrator on 2017/5/26.
 */
public class AdviceActivity extends BaseActivity {


    @BindView(R.id.v)
    View v;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.advice_edit)
    EditText adviceEdit;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.grid_linear)
    LinearLayout gridLinear;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private PicAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> piclist = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    private String type = "";

    @Override
    protected int setViewId() {
        return R.layout.advice_activity;
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
        titleName.setText("意见反馈");
        toolbarTitle.setText("意见反馈");
        Intent i = getIntent();
        if (i.hasExtra("type")) {
            type = i.getStringExtra("type");
        }
        adapter = new PicAdapter(AdviceActivity.this, list);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    MultiImageSelector.create(AdviceActivity.this)
                            .showCamera(false)
                            .count(4)
                            .single()
                            .multi()
                            .origin(piclist)
                            .start(AdviceActivity.this, REQUEST_IMAGE);
                } else {
                    piclist.remove(position);
                    MultiImageSelector.create(AdviceActivity.this)
                            .showCamera(false)
                            .count(4)
                            .single()
                            .multi()
                            .origin(piclist)
                            .start(AdviceActivity.this, REQUEST_IMAGE);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                piclist = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : piclist) {
                    sb.append(p);
                    sb.append("\n");
                }
                Log.e("piclist", sb.toString());
                list.clear();
                list.addAll(piclist);
                adapter.notifyDataSetChanged();
            }
        }
    }


    @OnClick({R.id.back_button, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.submit:
                UserDAO.sendAdvice(adviceEdit.getText().toString(), "0", "反馈", "", type, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Toast.makeText(SystemUtil.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(AdviceActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

}
