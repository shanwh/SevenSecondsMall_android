package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.typeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/26.
 */
public class ReportActivity extends BaseActivity {
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
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.edit_report)
    EditText editReport;
    @BindView(R.id.tel_edit)
    EditText telEdit;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.type)
    TextView type;

    private PopupWindow popupWindow;
    private ListView list;
    private typeAdapter adapter;
    private ArrayList<String> typelist = new ArrayList<>();
    private String result = "";
    private int stype = 0;
    private String mOrderid="";
    private String mOrdersn = "";
    private String mGoodid = "";
    private String mSpeckey = "";

    @Override
    protected int setViewId() {
        return R.layout.report_activity_layout;
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
        Intent i = getIntent();
        if(i.hasExtra("type")){
            stype = i.getIntExtra("type",0);
        }
        if(i.hasExtra("orderid")){
            mOrderid = i.getStringExtra("orderid");

        }
        if(i.hasExtra("ordersn")){
            mOrdersn = i.getStringExtra("ordersn");
        }
        if(i.hasExtra("goodsid")){
            mGoodid = i.getStringExtra("goodsid");
        }
        if(i.hasExtra("spec_key")){
            mSpeckey = i.getStringExtra("spec_key");
        }
        if(stype == 0){
            typelist.add("商品投诉");
            typelist.add("物流投诉");
            typelist.add("其他");
        }else {
            typelist.add("退货退款");
            typelist.add("换货");
            type.setText("请选择服务类型");
        }

        adapter = new typeAdapter(this,typelist);
        titleName.setText("在线投诉");

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back_button, R.id.submit,R.id.rl_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.submit:
                if(stype == 0){
                    if (editReport.getText().toString().equals("")) {
                        Toast.makeText(ReportActivity.this, "请填入投诉信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UserDAO.sendAdvice(editReport.getText().toString(), String.valueOf(result), "投诉",telEdit.getText().toString(),result, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            Toast.makeText(SystemUtil.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(ReportActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    OrderDAO.returnGoods(mOrderid,mOrdersn,mGoodid,String.valueOf(result),
                            editReport.getText().toString(),mSpeckey,new BaseCallBack(){
                                @Override
                                public void success(Object data) {
                                    Toast.makeText(SystemUtil.getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    Toast.makeText(ReportActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                break;
            case R.id.rl_return:
                showpopupwindows();
                break;
        }
    }

    public void showpopupwindows() {
        View view = LayoutInflater.from(ReportActivity.this).inflate(R.layout.report_type_pop, null);
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.showAsDropDown(rlReturn);
            list = (ListView) view.findViewById(R.id.list);
            list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type.setText(typelist.get(position));
                if(stype == 0){
                    switch (position){
                        case 0:
                            result = "4";
                            break;
                        case 1:
                            result = "5";
                            break;
                        case 2:
                            result = "6";
                            break;
                    }
                }else {
                    switch (position){
                        case 0:
                            result = "0";
                            break;
                        case 1:
                            result = "1";
                            break;
                    }
                }
                popupWindow.dismiss();
            }
        });
    }

}
