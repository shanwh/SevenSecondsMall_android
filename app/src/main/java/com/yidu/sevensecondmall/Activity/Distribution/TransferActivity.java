package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.TransferBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.RecyclerAdapter;
import com.yidu.sevensecondmall.views.adapter.RecyclerViewAdapter;
import com.yidu.sevensecondmall.views.adapter.TransferOrderAdapter;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.views.widget.itemdecorator.GridSpacingItemDecoration;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;
import com.yidu.sevensecondmall.widget.RecyclerOnScrollListener;
import com.yidu.sevensecondmall.widget.convenientbanner.CBViewHolderCreator;
import com.yidu.sevensecondmall.widget.convenientbanner.ConvenientBanner;
import com.yidu.sevensecondmall.widget.convenientbanner.holder.TransferBannerHolder;
import com.yidu.sevensecondmall.widget.view.RatioLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/1.
 */

public class TransferActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.message)
    VerticalTextview message;
    @BindView(R.id.recycle_order)
    RecyclerView recycleOrder;
    ConvenientBanner transferBanner;
    RatioLayout ratioLayout;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_transfer)
    RecyclerView rvTransfer;
    @BindView(R.id.et_min_price)
    EditText etMinPrice;
    @BindView(R.id.et_max_price)
    EditText etMaxPrice;
    @BindView(R.id.et_min_progress)
    EditText etMinProgress;
    @BindView(R.id.et_max_progress)
    EditText etMaxProgress;
    @BindView(R.id.et_min_discount)
    EditText etMinDiscount;
    @BindView(R.id.et_max_discount)
    EditText etMaxDiscount;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.sure)
    TextView sure;

    private TransferOrderAdapter adapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_transfer_drawlayout;
    }

    private MyAdapter myAdapter;

    String minPrice = "0";
    String maxPrice = "0";
    String minProgress = "0";
    String maxProgress = "0";
    String minDiscount = "0";
    String maxDiscount = "0";

    //设置智能收入两位小数
    private void setEdit(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});

    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        //初始化侧滑栏
        toolbarTitle.setText("加速区");
        setEdit(etMinPrice);
        setEdit(etMaxPrice);
        setEdit(etMinDiscount);
        setEdit(etMaxDiscount);
        setEdit(etMinProgress);
        setEdit(etMaxProgress);

        toolbarRightIv.setVisibility(View.VISIBLE);
        toolbarRightIv.setImageDrawable(getResources().getDrawable(R.drawable.toolbar_right_screen));
        toolbarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showToast(TransferActivity.this, "筛选");
                drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });
        recycleOrder.setHasFixedSize(true);
        recycleOrder.setFocusable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleOrder.setLayoutManager(linearLayoutManager);
        recycleOrder.addItemDecoration(new DividerItemDecoration(this, 10));
        ratioLayout = (RatioLayout) findViewById(R.id.ratioLayout);
        transferBanner = (ConvenientBanner) ratioLayout.findViewById(R.id.transferBanner);
        transferBanner.setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_focus}).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
        adapter = new TransferOrderAdapter(this);
        recycleOrder.setAdapter(adapter);
        //
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(TransferActivity.this, TransferDetailActivity.class));
            }
        });

        // 初始化时间的rv
        myAdapter = new MyAdapter(this);
        rvTransfer.setLayoutManager(new GridLayoutManager(this, 3));
        rvTransfer.addItemDecoration(new GridSpacingItemDecoration(3, 24, false));
        rvTransfer.setAdapter(myAdapter);
        //时间选择监听
        myAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                for (int i = 0; i < timeList.size(); i++) {
                    flagList.set(i, false);
                }
                flagList.set(position, true);
                myAdapter.notifyDataSetChanged();
                switch (position) {
                    case 0:
                        date = "all";
                        break;
                    case 1:
                        date = "today";
                        break;
                    case 2:
                        date = "threeday";
                        break;
                    case 3:
                        date = "week";
                        break;
                    case 4:
                        date = "onemonth";
                        break;
                }
            }
        });
        //重置
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        //确定
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMinPrice.getText().toString().equals("")) {
                    minPrice = "0";
                } else {
                    minPrice = etMinPrice.getText().toString();
                }
                if (etMaxPrice.getText().toString().equals("")) {
                    maxPrice = "0";
                } else {
                    maxPrice = etMaxPrice.getText().toString();
                }

                if (etMinDiscount.getText().toString().equals("")) {
                    minDiscount = "0";
                } else {
                    minDiscount = etMinDiscount.getText().toString();
                }
                if (etMaxDiscount.getText().toString().equals("")) {
                    maxDiscount = "0";
                } else {
                    maxDiscount = etMaxDiscount.getText().toString();
                }
                if (etMinProgress.getText().toString().equals("")) {
                    minProgress = "0";
                } else {
                    minProgress = etMinProgress.getText().toString();
                }
                if (etMaxProgress.getText().toString().equals("")) {
                    maxProgress = "0";
                } else {
                    maxProgress = etMaxProgress.getText().toString();
                }

                if (Integer.valueOf(minPrice) > Integer.valueOf(maxPrice)) {
                    ToastUtil.showToast(TransferActivity.this, "最小金额不能大于最大金额");
                    return;
                }

                if (Integer.valueOf(minProgress) > Integer.valueOf(maxProgress)) {
                    ToastUtil.showToast(TransferActivity.this, "最小进度不能大于最大进度");
                    return;
                }
                if (Integer.valueOf(minDiscount) > Integer.valueOf(maxDiscount)) {
                    ToastUtil.showToast(TransferActivity.this, "最小折扣不能大于最大折扣");
                    return;
                }
                if (Integer.valueOf(maxProgress) > 100) {
                    ToastUtil.showToast(TransferActivity.this, "最大进度不能超过100");
                    return;
                }
                if (Integer.valueOf(maxDiscount) > 100) {
                    ToastUtil.showToast(TransferActivity.this, "最大折扣不能超过100");
                    return;
                }
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                params.put("token", LoginUtils.getToken());
                params.put("page", "1");
                params.put("rate", minDiscount + "-" + maxDiscount);
                params.put("discount", minDiscount + "-" + maxDiscount);
                params.put("left_amount", minPrice + "-" + maxPrice);
                OkHttpUtil.postSubmitForm(HttpApi.quickOrderList, params, new OkHttpUtil.OnDownDataListener() {
                    @Override
                    public void onResponse(String url, String json) {
                        Log.e("搜索界面返回的json", json);
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                        TransferBean bean = new Gson().fromJson(json, TransferBean.class);
                        if (bean.getCode() == 0) {

                            if (bean.getResult().getList().size() > 0 && bean.getResult().getList() != null) {
                                //轮播图以及添加数据
                                if (page == 1) {
                                    transferBanner.setPages(new CBViewHolderCreator() {
                                        @Override
                                        public TransferBannerHolder createHolder() {
                                            return new TransferBannerHolder();
                                        }
                                    }, bean.getResult().getBanner()).startTurning(3000);

                                    //加载广告栏
                                    message.setTextList((ArrayList<String>) bean.getResult().getAd_list());
                                }
                                adapter.reset(bean.getResult().getList());
                            }
                        } else {
                            ToastUtil.showToast(TransferActivity.this, bean.getMessage());
                        }
                        reset();
                    }

                    @Override
                    public void onFailure(String url, String error) {

                    }
                });

            }
        });


    }

    private String date = "all";
    List<String> timeList;
    List<Boolean> flagList;
    int page = 1;

    private void loadMoreData() {


    }

    @Override
    protected void initEvent() {
        message.setText(10, 0, getResources().getColor(R.color.verticalTextColor));
        message.setTextStillTime(3000);//设置停留时长间隔
        message.setAnimTime(300);//设置进入和退出的时间间隔
        message.startAutoScroll();

        //初始化时间数据
        timeList = new ArrayList<>();
        timeList.add("全部");
        timeList.add("今天");
        timeList.add("最近三天");
        timeList.add("最近一周");
        timeList.add("最近一个月");
        myAdapter.addAll(timeList);
        //时间点击标记
        flagList = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++) {
            if (i == 0) {
                flagList.add(true);
            } else {
                flagList.add(false);
            }
        }

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        loadDate(page);
    }


    class MyAdapter extends RecyclerViewAdapter<String, MyAdapter.MyHolder> {
        Context context;


        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_transfer_time;
        }

        @Override
        public MyHolder getViewHolder(View view) {
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            String timeData = getItem(position);
            holder.time.setText(timeData);
            if (flagList.get(position)) {
                holder.time.setTextColor(getResources().getColor(R.color.textColor));
                holder.linearlayout.setBackground(getResources().getDrawable(R.drawable.item_bg_f1));
            } else {
                holder.time.setTextColor(getResources().getColor(R.color.textColor_bg));
                holder.linearlayout.setBackground(getResources().getDrawable(R.drawable.item_bg_f0));
            }
        }

        class MyHolder extends BaseHolder {
            @BindView(R.id.time)
            TextView time;
            @BindView(R.id.linearlayout)
            LinearLayout linearlayout;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    //首次加载数据
    private void loadDate(final int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("page", page + "");

        OkHttpUtil.postSubmitForm(HttpApi.quickOrderList, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("json", json);
                TransferBean bean = new Gson().fromJson(json, TransferBean.class);
                if (bean.getCode() == 0) {
                    if (bean.getResult().getList().size() > 0 && bean.getResult().getList() != null) {
//                        轮播图以及添加数据
                        if (page == 1) {
                            transferBanner.setPages(new CBViewHolderCreator() {
                                @Override
                                public TransferBannerHolder createHolder() {
                                    return new TransferBannerHolder();
                                }
                            }, bean.getResult().getBanner()).startTurning(3000);

                            //加载广告栏
                            message.setTextList((ArrayList<String>) bean.getResult().getAd_list());
                        }
                        adapter.addAll(bean.getResult().getList());
                    }
                } else {
                    ToastUtil.showToast(TransferActivity.this, bean.getMessage());
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    //重置输入框
    private void reset() {
        for (int i = 0; i < timeList.size(); i++) {
            flagList.set(i, false);
        }
        flagList.set(0, true);
        myAdapter.notifyDataSetChanged();
        etMinPrice.setText("");
        etMaxPrice.setText("");
        etMinDiscount.setText("");
        etMaxDiscount.setText("");
        etMinProgress.setText("");
        etMaxProgress.setText("");
    }
}
