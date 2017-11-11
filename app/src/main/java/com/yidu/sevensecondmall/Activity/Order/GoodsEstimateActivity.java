package com.yidu.sevensecondmall.Activity.Order;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.CommentBean;
import com.yidu.sevensecondmall.bean.Others.CommentListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;
import com.yidu.sevensecondmall.utils.OnRcvScrollListener;
import com.yidu.sevensecondmall.views.adapter.EstimateAdapter;
import com.yidu.sevensecondmall.views.adapter.OrderPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/12.
 */
public class GoodsEstimateActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


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
    @BindView(R.id.tv_icon1)
    TextView tvIcon1;
    @BindView(R.id.rl_icon1)
    RelativeLayout rlIcon1;
    @BindView(R.id.tv_icon2)
    TextView tvIcon2;
    @BindView(R.id.rl_icon2)
    RelativeLayout rlIcon2;
    @BindView(R.id.tv_icon3)
    TextView tvIcon3;
    @BindView(R.id.rl_icon3)
    RelativeLayout rlIcon3;
    @BindView(R.id.tv_icon4)
    TextView tvIcon4;
    @BindView(R.id.rl_icon4)
    RelativeLayout rlIcon4;
    @BindView(R.id.tv_icon5)
    TextView tvIcon5;
    @BindView(R.id.rl_icon5)
    RelativeLayout rlIcon5;
    @BindView(R.id.bottom_tab)
    LinearLayout bottomTab;
    @BindView(R.id.cursor)
    ImageView cursor;
    @BindView(R.id.viewpager_order)
    ViewPager viewpagerOrder;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.tv_num3)
    TextView tvNum3;
    @BindView(R.id.tv_num4)
    TextView tvNum4;
    @BindView(R.id.tv_num5)
    TextView tvNum5;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    private OrderPagerAdapter pagerAdapter;
    private int orderBmpw = 0; // 游标宽度
    private int orderOffset = 0;// // 动画图片偏移量
    private int orderCurrIndex = 0;// 当前页卡编号

    private ArrayList<View> viewList = new ArrayList<View>();//view数组
    private List<TextView> btnlist = new ArrayList<TextView>();//按键数组
    private List<TextView> txtlist = new ArrayList<TextView>();
    private int currentPage;
    private View allview;
    private View goodview;
    private View midview;
    private View lowview;
    private View picview;
    private SwipeRefreshLayout allrefresh;
    private SwipeRefreshLayout goodrefresh;
    private SwipeRefreshLayout midrefresh;
    private SwipeRefreshLayout lowrefresh;
    private SwipeRefreshLayout picrefresh;
    private EstimateAdapter alleadapter;
    private EstimateAdapter goodeadapter;
    private EstimateAdapter mideadapter;
    private EstimateAdapter loweadapter;
    private EstimateAdapter piceadapter;
    private LinearLayoutManager allmanager;
    private LinearLayoutManager goodmanager;
    private LinearLayoutManager midmanager;
    private LinearLayoutManager lowmanager;
    private LinearLayoutManager picmanager;
    private List<CommentBean> alllist;
    private List<CommentBean> goodlist;
    private List<CommentBean> midlist;
    private List<CommentBean> lowlist;
    private List<CommentBean> piclist;
    private String id;
    private int page;
    private Context context;
    private RecyclerView alllistview;
    private RecyclerView goodlistview;
    private RecyclerView midlistview;
    private RecyclerView lowlistview;
    private RecyclerView piclistview;
    private int allpage = 1;
    private int goodpage = 1;
    private int midpage = 1;
    private int lowpage = 1;
    private int picpage = 1;
    private int clickpage;
    private CommentListBean allbean;
    private CommentListBean goodbean;
    private CommentListBean commentbean;
    private CommentListBean badbean;
    private CommentListBean imgbean;

    @Override
    protected int setViewId() {
        return R.layout.estimate_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        context = GoodsEstimateActivity.this;
        Intent i = getIntent();
        if (i.hasExtra("id") && i.getStringExtra("id") != null) {
            id = i.getStringExtra("id");
        }
        titleName.setText("商品评价");
        toolbarTitle.setText("商品评价");
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void init() {
        initPager();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private Handler handler = new Handler();


    public void initPager() {
        btnlist.add(tvIcon1);
        btnlist.add(tvIcon2);
        btnlist.add(tvIcon3);
        btnlist.add(tvIcon4);
        btnlist.add(tvIcon5);
        txtlist.add(tvNum1);
        txtlist.add(tvNum2);
        txtlist.add(tvNum3);
        txtlist.add(tvNum4);
        txtlist.add(tvNum5);

        LayoutInflater inflater = LayoutInflater.from(this);
        allview = inflater.inflate(R.layout.layout_orderlist, null);
        goodview = inflater.inflate(R.layout.layout_orderlist, null);
        midview = inflater.inflate(R.layout.layout_orderlist, null);
        lowview = inflater.inflate(R.layout.layout_orderlist, null);
        picview = inflater.inflate(R.layout.layout_orderlist, null);
        alllistview = (RecyclerView) allview.findViewById(R.id.order_list);
        goodlistview = (RecyclerView) goodview.findViewById(R.id.order_list);
        midlistview = (RecyclerView) midview.findViewById(R.id.order_list);
        lowlistview = (RecyclerView) lowview.findViewById(R.id.order_list);
        piclistview = (RecyclerView) picview.findViewById(R.id.order_list);
        allmanager = new LinearLayoutManager(context);
        goodmanager = new LinearLayoutManager(context);
        midmanager = new LinearLayoutManager(context);
        lowmanager = new LinearLayoutManager(context);
        picmanager = new LinearLayoutManager(context);

        viewList.add(allview);
        viewList.add(goodview);
        viewList.add(midview);
        viewList.add(lowview);
        viewList.add(picview);
        pagerAdapter = new OrderPagerAdapter(viewList);
        viewpagerOrder.setAdapter(pagerAdapter);
        viewpagerOrder.addOnPageChangeListener(this);
        allrefresh = (SwipeRefreshLayout) allview.findViewById(R.id.orderrefresh);

        GoodsDAO.getComment(id, "1", "", new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    allbean = (CommentListBean) data;
                    alllist = allbean.getList();
                    alleadapter = new EstimateAdapter(context, alllist);
                    alllistview.setLayoutManager(allmanager);
                    alllistview.setAdapter(alleadapter);
                    tvNum1.setText(allbean.getAll_count());
                    tvNum2.setText(allbean.getGood_count());
                    tvNum3.setText(allbean.getCommonly_count());
                    tvNum4.setText(allbean.getBad_count());
                    tvNum5.setText(allbean.getImg_count());
                    alllistview.setOnScrollListener(new OnRcvScrollListener() {
                        @Override
                        public void isBottom() {
                            allpage++;
                            GoodsDAO.getComment(id, String.valueOf(allpage), "", new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    CommentListBean bean2 = (CommentListBean) data;
                                    List<CommentBean> list2 = bean2.getList();
                                    alllist.addAll(list2);
                                    alleadapter.notifyDataSetChanged();
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    allpage--;
                                    Toast.makeText(GoodsEstimateActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }
        });

        allrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (EasyPermissionsEx.isNetworkAvailable(GoodsEstimateActivity.this)) {
                    allrefresh.setRefreshing(true);
                    GoodsDAO.getComment(id, "1", "", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            allpage = 1;
                            alllist.clear();
                            CommentListBean bean2 = (CommentListBean) data;
                            List<CommentBean> list2 = bean2.getList();
                            alllist.addAll(list2);
                            alleadapter.notifyDataSetChanged();
                            allrefresh.setRefreshing(false);
                            tvNum1.setText(bean2.getAll_count());
                            tvNum2.setText(bean2.getGood_count());
                            tvNum3.setText(bean2.getCommonly_count());
                            tvNum4.setText(bean2.getBad_count());
                            tvNum5.setText(bean2.getImg_count());
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            allrefresh.setRefreshing(false);
                            if (errorCode == 1001) {
                                Log.e("error", data + "");
                            } else {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    allrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }

            }
        });


        goodrefresh = (SwipeRefreshLayout) goodview.findViewById(R.id.orderrefresh);
        GoodsDAO.getComment(id, "1", "good", new BaseCallBack() {
            @Override
            public void success(Object data) {
                goodbean = (CommentListBean) data;
                goodlist = goodbean.getList();
                goodeadapter = new EstimateAdapter(context, goodlist);
                goodlistview.setLayoutManager(goodmanager);
                goodlistview.setAdapter(goodeadapter);
                goodlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        goodpage++;
                        GoodsDAO.getComment(id, String.valueOf(goodpage), "", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                CommentListBean bean2 = (CommentListBean) data;
                                List<CommentBean> list2 = bean2.getList();
                                goodlist.addAll(list2);
                                goodeadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                goodpage--;
                                Toast.makeText(GoodsEstimateActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }
        });

        goodrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(GoodsEstimateActivity.this)) {
                    GoodsDAO.getComment(id, "1", "good", new BaseCallBack() {

                        @Override
                        public void success(Object data) {
                            goodlist.clear();
                            CommentListBean bean2 = (CommentListBean) data;
                            List<CommentBean> list2 = bean2.getList();
                            goodlist.addAll(list2);
                            goodeadapter.notifyDataSetChanged();
                            goodrefresh.setRefreshing(false);
                            tvNum1.setText(bean2.getAll_count());
                            tvNum2.setText(bean2.getGood_count());
                            tvNum3.setText(bean2.getCommonly_count());
                            tvNum4.setText(bean2.getBad_count());
                            tvNum5.setText(bean2.getImg_count());
                            Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            goodrefresh.setRefreshing(false);
                            if (errorCode == 1001) {
                                Log.e("error", data + "");
                            } else {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    goodrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });


        midrefresh = (SwipeRefreshLayout) midview.findViewById(R.id.orderrefresh);
        GoodsDAO.getComment(id, "1", "commonly", new BaseCallBack() {
            @Override
            public void success(Object data) {
                commentbean = (CommentListBean) data;
                midlist = commentbean.getList();
                mideadapter = new EstimateAdapter(context, midlist);
                midlistview.setLayoutManager(midmanager);
                midlistview.setAdapter(mideadapter);
                midlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        midpage++;
                        GoodsDAO.getComment(id, String.valueOf(midpage), "", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                CommentListBean bean2 = (CommentListBean) data;
                                List<CommentBean> list2 = bean2.getList();
                                midlist.addAll(list2);
                                mideadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                midpage--;
                                Toast.makeText(GoodsEstimateActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }
        });

        midrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                midrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(GoodsEstimateActivity.this)) {
                    GoodsDAO.getComment(id, "1", "commonly", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            midrefresh.setRefreshing(false);
                            midlist.clear();
                            CommentListBean bean2 = (CommentListBean) data;
                            List<CommentBean> list2 = bean2.getList();
                            midlist.addAll(list2);
                            mideadapter.notifyDataSetChanged();
                            Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                            tvNum1.setText(bean2.getAll_count());
                            tvNum2.setText(bean2.getGood_count());
                            tvNum3.setText(bean2.getCommonly_count());
                            tvNum4.setText(bean2.getBad_count());
                            tvNum5.setText(bean2.getImg_count());

                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            midrefresh.setRefreshing(false);
                            if (errorCode == 1001) {
                                Log.e("error", data + "");
                            } else {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    midrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });


        lowrefresh = (SwipeRefreshLayout) lowview.findViewById(R.id.orderrefresh);
        GoodsDAO.getComment(id, "1", "bad", new BaseCallBack() {
            @Override
            public void success(Object data) {
                badbean = (CommentListBean) data;
                lowlist = badbean.getList();
                loweadapter = new EstimateAdapter(context, lowlist);
                lowlistview.setLayoutManager(lowmanager);
                lowlistview.setAdapter(loweadapter);
                lowlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        lowpage++;
                        GoodsDAO.getComment(id, String.valueOf(lowpage), "", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                CommentListBean bean2 = (CommentListBean) data;
                                List<CommentBean> list2 = bean2.getList();
                                lowlist.addAll(list2);
                                loweadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                lowpage--;
                                Toast.makeText(GoodsEstimateActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }
        });


        lowrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lowrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(GoodsEstimateActivity.this)) {
                    GoodsDAO.getComment(id, "1", "", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            lowlist.clear();
                            CommentListBean bean2 = (CommentListBean) data;
                            List<CommentBean> list2 = bean2.getList();
                            lowlist.addAll(list2);
                            loweadapter.notifyDataSetChanged();
                            lowrefresh.setRefreshing(false);
                            tvNum1.setText(bean2.getAll_count());
                            tvNum2.setText(bean2.getGood_count());
                            tvNum3.setText(bean2.getCommonly_count());
                            tvNum4.setText(bean2.getBad_count());
                            tvNum5.setText(bean2.getImg_count());
                            Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            lowrefresh.setRefreshing(false);
                            if (errorCode == 1001) {
                                Log.e("error", data + "");
                            } else {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    lowrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });


        picrefresh = (SwipeRefreshLayout) picview.findViewById(R.id.orderrefresh);
        GoodsDAO.getComment(id, "1", "img", new BaseCallBack() {
            @Override
            public void success(Object data) {
                imgbean = (CommentListBean) data;
                piclist = imgbean.getList();
                piceadapter = new EstimateAdapter(context, piclist);
                piclistview.setLayoutManager(picmanager);
                piclistview.setAdapter(piceadapter);
                piclistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        picpage++;
                        GoodsDAO.getComment(id, String.valueOf(picpage), "", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                CommentListBean bean2 = (CommentListBean) data;
                                List<CommentBean> list2 = bean2.getList();
                                piclist.addAll(list2);
                                piceadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                picpage--;
                                Toast.makeText(GoodsEstimateActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
            }
        });

        picrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                picrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(GoodsEstimateActivity.this)) {
                    GoodsDAO.getComment(id, "1", "img", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            piclist.clear();
                            CommentListBean bean2 = (CommentListBean) data;
                            List<CommentBean> list2 = bean2.getList();
                            piclist.addAll(list2);
                            piceadapter.notifyDataSetChanged();
                            picrefresh.setRefreshing(false);
                            Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                            tvNum1.setText(bean2.getAll_count());
                            tvNum2.setText(bean2.getGood_count());
                            tvNum3.setText(bean2.getCommonly_count());
                            tvNum4.setText(bean2.getBad_count());
                            tvNum5.setText(bean2.getImg_count());

                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            picrefresh.setRefreshing(false);
                            if (errorCode == 1001) {
                                Log.e("error", data + "");
                            } else {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    picrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (getIntent().hasExtra("clickIndex")) {
            viewpagerOrder.setCurrentItem(getIntent().getIntExtra("clickIndex", 0));
            reSetImageMatrix(getIntent().getIntExtra("clickIndex", 0));
        }
        initCursorPos();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int one = orderOffset * 2 + orderBmpw;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        int three = one * 3;//1->4
        int four = one * 4;//1->5
        if (position != currentPage) {
            for (int i = 0; i < btnlist.size(); i++) {
                btnlist.get(i).setTextColor(getResources().getColor(R.color.colorBottomBlack));
                txtlist.get(i).setTextColor(getResources().getColor(R.color.colorBottomBlack));
            }
            btnlist.get(position).setTextColor(getResources().getColor(R.color.colorBottomRed));
            txtlist.get(position).setTextColor(getResources().getColor(R.color.colorBottomRed));
        }
        currentPage = viewpagerOrder.getCurrentItem();
        Animation animation = null;
        switch (position) {
            case 0:
                if (orderCurrIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (orderCurrIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                } else if (orderCurrIndex == 3) {
                    animation = new TranslateAnimation(three, 0, 0, 0);
                } else if (orderCurrIndex == 4) {
                    animation = new TranslateAnimation(four, 0, 0, 0);
                }
                break;
            case 1:
                if (orderCurrIndex == 0) {
                    animation = new TranslateAnimation(orderOffset, one, 0, 0);
                } else if (orderCurrIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                } else if (orderCurrIndex == 3) {
                    animation = new TranslateAnimation(three, one, 0, 0);
                } else if (orderCurrIndex == 4) {
                    animation = new TranslateAnimation(four, one, 0, 0);
                }
                break;
            case 2:
                if (orderCurrIndex == 0) {
                    animation = new TranslateAnimation(orderOffset, two, 0, 0);
                } else if (orderCurrIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                } else if (orderCurrIndex == 3) {
                    animation = new TranslateAnimation(three, two, 0, 0);
                } else if (orderCurrIndex == 4) {
                    animation = new TranslateAnimation(four, two, 0, 0);
                }
                break;
            case 3:
                if (orderCurrIndex == 0) {
                    animation = new TranslateAnimation(orderOffset, three, 0, 0);
                } else if (orderCurrIndex == 1) {
                    animation = new TranslateAnimation(one, three, 0, 0);
                } else if (orderCurrIndex == 2) {
                    animation = new TranslateAnimation(two, three, 0, 0);
                } else if (orderCurrIndex == 4) {
                    animation = new TranslateAnimation(four, three, 0, 0);
                }
                break;
            case 4:
                if (orderCurrIndex == 0) {
                    animation = new TranslateAnimation(orderOffset, four, 0, 0);
                } else if (orderCurrIndex == 1) {
                    animation = new TranslateAnimation(one, four, 0, 0);
                } else if (orderCurrIndex == 2) {
                    animation = new TranslateAnimation(two, four, 0, 0);
                } else if (orderCurrIndex == 3) {
                    animation = new TranslateAnimation(three, four, 0, 0);
                }
                break;
        }
        orderCurrIndex = position;
        if (animation != null) {
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void initCursorPos() {
        // 初始化动画
        orderBmpw = BitmapFactory.decodeResource(getResources(), R.drawable.xiahuaxian).getWidth();// 获取图片宽度

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        orderOffset = (screenW / viewList.size() - orderBmpw) / 2;// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(orderOffset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    public void reSetImageMatrix(int currentPage) {
        try {

            Animation animation = null;
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenW = dm.widthPixels;// 获取分辨率宽度
            orderBmpw = BitmapFactory.decodeResource(getResources(), R.drawable.xiahuaxian).getWidth();// 获取图片宽度
            orderOffset = (screenW / viewList.size() - orderBmpw) / 2;// 计算偏移量
            int one = orderOffset * 2 + orderBmpw;// 页卡1 -> 页卡2 偏移量
            int two = one * 2;// 页卡1 -> 页卡3 偏移量
            int three = one * 3;//1->4
            int four = one * 4;//1->5
//            int currentItem = viewpagerOrder.getCurrentItem();

            switch (currentPage) {
                case 1:
                    animation = new TranslateAnimation(orderOffset, one, 0, 0);
                    break;
                case 2:
                    animation = new TranslateAnimation(orderOffset, two, 0, 0);
                    break;
                case 3:
                    animation = new TranslateAnimation(orderOffset, three, 0, 0);
                    break;
                case 4:
                    animation = new TranslateAnimation(orderOffset, four, 0, 0);
                    break;
            }

            if (animation != null) {
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                cursor.startAnimation(animation);
            }
        } catch (Exception e) {
//            Log.e(TAG, "reSetImageMatrix: ", e);
        }
    }

    @OnClick({R.id.back_button, R.id.rl_icon1, R.id.rl_icon2, R.id.rl_icon3, R.id.rl_icon4, R.id.rl_icon5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.rl_icon1:
                clickpage = 0;
                break;
            case R.id.rl_icon2:
                clickpage = 1;
                break;
            case R.id.rl_icon3:
                clickpage = 2;
                break;
            case R.id.rl_icon4:
                clickpage = 3;
                break;
            case R.id.rl_icon5:
                clickpage = 4;
                break;
        }

        if (currentPage != clickpage) {
            viewpagerOrder.setCurrentItem(clickpage);
        }
    }

}
