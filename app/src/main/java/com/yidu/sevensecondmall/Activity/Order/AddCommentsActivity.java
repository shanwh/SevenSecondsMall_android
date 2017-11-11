package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class AddCommentsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rating_bar)
    ProperRatingBar ratingBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.iftv_comments_isanonymity)
    IconFontTextView iftvCommentsIsanonymity;
    @BindView(R.id.rating_bar_goods_wrap)
    ProperRatingBar ratingBarGoodsWrap;
    @BindView(R.id.rating_bar_delivery_goods_v)
    ProperRatingBar ratingBarDeliveryGoodsV;
    @BindView(R.id.rating_bar_delivery_service)
    ProperRatingBar ratingBarDeliveryService;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private String img = "";
    private String id = "1";
    private String orderid = "";

    @Override
    protected int setViewId() {
        return R.layout.activity_add_new_comments;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("评价");
        toolbarRight.setText("提交");
    }

    @Override
    protected void initEvent() {
        backButton.setOnClickListener(this);
        toolbarRight.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        Intent i = getIntent();
        if (i.hasExtra("id")) {
            id = i.getStringExtra("id");
        }
        if (i.hasExtra("img")) {
            img = i.getStringExtra("img");
        }
        if (i.hasExtra("orderid")) {
            orderid = i.getStringExtra("orderid");
        }
        Glide.with(AddCommentsActivity.this)
                .load(HttpApi.getFullImageUrl(img))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.default_loading_pic)
                .into(ivImg);
        ratingBar.setRating(5);
        ratingBarGoodsWrap.setRating(5);
        ratingBarDeliveryService.setRating(5);
        ratingBarDeliveryGoodsV.setRating(5);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.submit:
                //提交
                submitReview();
                break;
        }
    }

    private void submitReview() {
        if (checkContent()) {
            String content = etContent.getText().toString();

            int deliveryrating = (int) ratingBarDeliveryGoodsV.getRating();
            if (deliveryrating == 0) {
                deliveryrating = 5;
            }
            int goodsrating = (int) ratingBarGoodsWrap.getRating();
            if (goodsrating == 0) {
                goodsrating = 5;
            }
            int servicerating = (int) ratingBarDeliveryService.getRating();
            if (servicerating == 0) {
                servicerating = 5;
            }

            GoodsDAO.addComment(id, orderid, servicerating + "", deliveryrating + "", goodsrating + "", content,
                    "", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            showPopupWindow();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(AddCommentsActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private boolean checkContent() {
        if (etContent.getText().toString().trim().length() < 6) {
            showShortToast("最少6个字才能提交呦~");
            return false;
        }
        return true;
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(AddCommentsActivity.this).inflate(R.layout.pop_window_commend_success, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, AddCommentsActivity.this.getResources().getDisplayMetrics());

        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(v, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        SystemUtil.setSharedBoolean("closeDetail", true);
                        EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRSHLIST));
                        AddCommentsActivity.this.finish();
                    }
                })
                .create()//创建PopupWindow
                .showAtLocation(toolbarRight, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        TextView tv = (TextView) contentView.findViewById(R.id.tv);
        tv.setText("评价成功");
        contentView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dissmiss();
            }
        });
    }

}
