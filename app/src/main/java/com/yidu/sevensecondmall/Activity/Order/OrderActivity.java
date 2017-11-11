package com.yidu.sevensecondmall.Activity.Order;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;
import com.yidu.sevensecondmall.utils.OnRcvScrollListener;
import com.yidu.sevensecondmall.views.adapter.OrderPagerAdapter;
import com.yidu.sevensecondmall.views.adapter.orderAdapter;
import com.yidu.sevensecondmall.views.widget.dispatchViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 订单activity
 */
public class OrderActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.back_button)
    ImageView backButton;
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
    dispatchViewPager viewpagerOrder;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.tv_icon6)
    TextView tvIcon6;
    @BindView(R.id.rl_icon6)
    RelativeLayout rlIcon6;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private OrderPagerAdapter pagerAdapter;
    private int orderBmpw = 0; // 游标宽度
    private int orderOffset = 0;// // 动画图片偏移量
    private int orderCurrIndex = 0;// 当前页卡编号


    private ArrayList<View> viewList = new ArrayList<View>();//view数组
    private List<TextView> btnlist = new ArrayList<TextView>();//按键数组
    private int currentPage;
    private View allview;
    private View payview;
    private View sendview;
    private View getview;
    private View finishview;
    private View returnview;
    private SwipeRefreshLayout allrefresh;
    private SwipeRefreshLayout payrefresh;
    private SwipeRefreshLayout sendrefresh;
    private SwipeRefreshLayout getrefresh;
    private SwipeRefreshLayout finishrefresh;
    private SwipeRefreshLayout returnrefresh;
    private orderAdapter alladapter;
    private orderAdapter payadapter;
    private orderAdapter sendadapter;
    private orderAdapter getadapter;
    private orderAdapter finishadapter;
    private orderAdapter returnadapter;
    private ArrayList<OrderListBean> alllist = new ArrayList<>();
    private ArrayList<OrderListBean> paylist = new ArrayList<>();
    private ArrayList<OrderListBean> sendlist = new ArrayList<>();
    private ArrayList<OrderListBean> getlist = new ArrayList<>();
    private ArrayList<OrderListBean> finishlist = new ArrayList<>();
    private ArrayList<OrderListBean> returnlist = new ArrayList<>();
    private LinearLayoutManager allmanager;
    private LinearLayoutManager paymanager;
    private LinearLayoutManager sendmanager;
    private LinearLayoutManager getmanager;
    private LinearLayoutManager finishmanager;
    private LinearLayoutManager returnmanager;
    private RecyclerView alllistview;
    private RecyclerView paylistview;
    private RecyclerView sendlistview;
    private RecyclerView getlistview;
    private RecyclerView finishlistview;
    private RecyclerView returnlistview;

    private int clickPage;
    private Context context;
    private int animationTime;
    private int allpage = 1;
    private int paypage = 1;
    private int sendpage = 1;
    private int getpage = 1;
    private int finishpage = 1;
    private int returnpage = 1;

    @Override
    protected int setViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        this.context = OrderActivity.this;
    }

    @Override
    protected void initEvent() {
        initPager();
    }

    @Override
    protected void init() {
        titleName.setText("个人订单");
        toolbarTitle.setText("个人订单");
        if (getIntent().hasExtra("clickIndex")) {
            animationTime = 0;
            viewpagerOrder.setCurrentItem(getIntent().getIntExtra("clickIndex", 0));
//            reSetImageMatrix(getIntent().getIntExtra("clickIndex", 0));
        }
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
        btnlist.add(tvIcon6);
        LayoutInflater inflater = LayoutInflater.from(this);
        allview = inflater.inflate(R.layout.layout_orderlist, null);
        sendview = inflater.inflate(R.layout.layout_orderlist, null);
        payview = inflater.inflate(R.layout.layout_orderlist, null);
        getview = inflater.inflate(R.layout.layout_orderlist, null);
        finishview = inflater.inflate(R.layout.layout_orderlist, null);
        returnview = inflater.inflate(R.layout.layout_orderlist, null);
        viewList.add(allview);
        viewList.add(payview);
        viewList.add(sendview);
        viewList.add(getview);
        viewList.add(finishview);
        viewList.add(returnview);
        pagerAdapter = new OrderPagerAdapter(viewList);
        viewpagerOrder.setAdapter(pagerAdapter);
        viewpagerOrder.setOnPageChangeListener(this);
        allrefresh = (SwipeRefreshLayout) allview.findViewById(R.id.orderrefresh);
        payrefresh = (SwipeRefreshLayout) payview.findViewById(R.id.orderrefresh);
        sendrefresh = (SwipeRefreshLayout) sendview.findViewById(R.id.orderrefresh);
        getrefresh = (SwipeRefreshLayout) getview.findViewById(R.id.orderrefresh);
        finishrefresh = (SwipeRefreshLayout) finishview.findViewById(R.id.orderrefresh);
        returnrefresh = (SwipeRefreshLayout) returnview.findViewById(R.id.orderrefresh);
        alllistview = (RecyclerView) allview.findViewById(R.id.order_list);
        paylistview = (RecyclerView) payview.findViewById(R.id.order_list);
        sendlistview = (RecyclerView) sendview.findViewById(R.id.order_list);
        getlistview = (RecyclerView) getview.findViewById(R.id.order_list);
        finishlistview = (RecyclerView) finishview.findViewById(R.id.order_list);
        returnlistview = (RecyclerView) returnview.findViewById(R.id.order_list);

        allmanager = new LinearLayoutManager(context);
        allmanager.setOrientation(LinearLayoutManager.VERTICAL);
        paymanager = new LinearLayoutManager(context);
        paymanager.setOrientation(LinearLayoutManager.VERTICAL);
        sendmanager = new LinearLayoutManager(context);
        sendmanager.setOrientation(LinearLayoutManager.VERTICAL);
        getmanager = new LinearLayoutManager(context);
        getmanager.setOrientation(LinearLayoutManager.VERTICAL);
        finishmanager = new LinearLayoutManager(context);
        finishmanager.setOrientation(LinearLayoutManager.VERTICAL);
        returnmanager = new LinearLayoutManager(context);
        returnmanager.setOrientation(LinearLayoutManager.VERTICAL);


        initCursorPos();

        getNetData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlecenter(RefreshEvent ev) {
        switch (ev.founctionTag) {
            case IEventTag.REFRSHLIST:
                OrderDAO.getOrderList(1, "", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            if (alladapter != null) {
                                alllist.clear();
                                alladapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                alllist.addAll(list2);
                                alladapter.notifyDataSetChanged();
                            } else {
                                alllist = (ArrayList<OrderListBean>) data;
                                alllistview.setLayoutManager(allmanager);
                                alladapter = new orderAdapter(context, alllist);
                                alllistview.setAdapter(alladapter);
                            }

                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        } else {
                            if (alladapter != null && alllist != null) {
                                alllist.clear();
                                alladapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                OrderDAO.getOrderList(1, "waitpay", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (payadapter != null) {
                            paylist.clear();
                            payadapter.clearPoition();
                            ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                            paylist.addAll(list2);
                            payadapter.notifyDataSetChanged();
                            paypage = 1;
                        } else {
                            paylist = (ArrayList<OrderListBean>) data;
                            paylistview.setLayoutManager(paymanager);
                            payadapter = new orderAdapter(context, paylist);
                            paylistview.setAdapter(payadapter);
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        } else {
                            if (paylist != null && payadapter != null) {
                                paylist.clear();
                                payadapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                OrderDAO.getOrderList(1, "waitsend", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (sendadapter != null) {
                            sendlist.clear();
                            sendadapter.clearPoition();
                            ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                            sendlist.addAll(list2);
                            sendadapter.notifyDataSetChanged();
                            sendpage = 1;
                        } else {
                            sendlist = (ArrayList<OrderListBean>) data;
                            sendlistview.setLayoutManager(sendmanager);
                            sendadapter = new orderAdapter(context, sendlist);
                            sendlistview.setAdapter(sendadapter);
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        } else {
                            if (sendlist != null && sendadapter != null) {
                                sendlist.clear();
                                sendadapter.notifyDataSetChanged();
                            }

                        }
                    }
                });

                OrderDAO.getOrderList(1, "waitget", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (getadapter != null) {
                            getlist.clear();
                            getadapter.clearPoition();
                            ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                            getlist.addAll(list2);
                            getadapter.notifyDataSetChanged();
                            getpage = 1;
                        } else {
                            getlist = (ArrayList<OrderListBean>) data;
                            getlistview.setLayoutManager(getmanager);
                            getadapter = new orderAdapter(context, getlist);
                            getlistview.setAdapter(getadapter);
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        } else {
                            if (getlist != null && getadapter != null) {
                                getlist.clear();
                                getadapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                OrderDAO.getOrderList(1, "waitevaluate", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (finishadapter != null) {
                            finishlist.clear();
                            finishadapter.clearPoition();
                            ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                            finishlist.addAll(list2);
                            finishadapter.notifyDataSetChanged();
                            finishpage = 1;
                        } else {
                            finishlist = (ArrayList<OrderListBean>) data;
                            finishlistview.setLayoutManager(finishmanager);
                            finishadapter = new orderAdapter(context, finishlist);
                            finishlistview.setAdapter(finishadapter);
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        } else {
                            if (finishadapter != null && finishlist != null) {
                                finishlist.clear();
                                finishadapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                OrderDAO.getOrderList(1, "return_goods", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (returnadapter != null) {
                            returnlist.clear();
                            returnadapter.clearPoition();
                            ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                            returnlist.addAll(list2);
                            returnadapter.notifyDataSetChanged();
                            returnpage = 1;
                        } else {
                            returnlist = (ArrayList<OrderListBean>) data;
                            returnlistview.setLayoutManager(returnmanager);
                            returnadapter = new orderAdapter(context, returnlist);
                            returnlistview.setAdapter(returnadapter);
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode != 1001) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
    }

    //网络请求
    public void getNetData() {
        OrderDAO.getOrderList(1, "", new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    alllist = (ArrayList<OrderListBean>) data;
                    alllistview.setLayoutManager(allmanager);
                    alladapter = new orderAdapter(context, alllist);
                    alllistview.setAdapter(alladapter);
                    alllistview.setOnScrollListener(new OnRcvScrollListener() {
                        @Override
                        public void isBottom() {
                            allpage++;
                            OrderDAO.getOrderList(allpage, "", new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                    alllist.addAll(list2);
                                    alladapter.notifyDataSetChanged();
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    allpage--;
                                    Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        allrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                allrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (alladapter != null) {
                                alllist.clear();
                                alladapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                alllist.addAll(list2);
                                alladapter.notifyDataSetChanged();
                                allpage = 1;
                                Toast.makeText(OrderActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                allrefresh.setRefreshing(false);
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                            allrefresh.setRefreshing(false);
                        }
                    });

                } else {
                    allrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        OrderDAO.getOrderList(1, "waitpay", new BaseCallBack() {
            @Override
            public void success(Object data) {
                paylist = (ArrayList<OrderListBean>) data;
                paylistview.setLayoutManager(paymanager);
                payadapter = new orderAdapter(context, paylist);
                paylistview.setAdapter(payadapter);
                paylistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        paypage++;
                        OrderDAO.getOrderList(paypage, "waitpay", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                paylist.addAll(list2);
                                payadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                paypage--;
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        payrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                payrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "waitpay", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (payadapter != null) {
                                paylist.clear();
                                payadapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                paylist.addAll(list2);
                                payadapter.notifyDataSetChanged();
                                paypage = 1;
                                Toast.makeText(OrderActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                payrefresh.setRefreshing(false);
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            payrefresh.setRefreshing(false);
                            Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    payrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        OrderDAO.getOrderList(1, "waitsend", new BaseCallBack() {
            @Override
            public void success(Object data) {
                sendlist = (ArrayList<OrderListBean>) data;
                sendlistview.setLayoutManager(sendmanager);
                sendadapter = new orderAdapter(context, sendlist);
                sendlistview.setAdapter(sendadapter);
                sendlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        sendpage++;
                        OrderDAO.getOrderList(sendpage, "waitsend", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                sendlist.addAll(list2);
                                sendadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                sendpage--;
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "waitsend", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (sendadapter != null) {
                                sendlist.clear();
                                sendadapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                sendlist.addAll(list2);
                                sendadapter.notifyDataSetChanged();
                                sendpage = 1;
                                Toast.makeText(OrderActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                sendrefresh.setRefreshing(false);
                            }

                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                            sendrefresh.setRefreshing(false);
                        }
                    });

                } else {
                    sendrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        OrderDAO.getOrderList(1, "waitget", new BaseCallBack() {
            @Override
            public void success(Object data) {
                getlist = (ArrayList<OrderListBean>) data;
                getlistview.setLayoutManager(getmanager);
                getadapter = new orderAdapter(context, getlist);
                getlistview.setAdapter(getadapter);
                getlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        getpage++;
                        OrderDAO.getOrderList(getpage, "waitget", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                getlist.addAll(list2);
                                getadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                getpage--;
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        getrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "waitget", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (getadapter != null) {
                                getlist.clear();
                                getadapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                getlist.addAll(list2);
                                getadapter.notifyDataSetChanged();
                                getpage = 1;
                                Toast.makeText(OrderActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                getrefresh.setRefreshing(false);
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                            getrefresh.setRefreshing(false);
                        }
                    });


                } else {
                    getrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        OrderDAO.getOrderList(1, "waitevaluate", new BaseCallBack() {
            @Override
            public void success(Object data) {
                finishlist = (ArrayList<OrderListBean>) data;
                finishlistview.setLayoutManager(finishmanager);
                finishadapter = new orderAdapter(context, finishlist);
                finishlistview.setAdapter(finishadapter);
                finishlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        finishpage++;
                        OrderDAO.getOrderList(finishpage, "waitevaluate", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                finishlist.addAll(list2);
                                finishadapter.notifyDataSetChanged();

                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                finishpage--;
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finishrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "waitevaluate", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (finishadapter != null) {
                                Toast.makeText(SystemUtil.getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                finishlist.clear();
                                finishadapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                finishlist.addAll(list2);
                                finishadapter.notifyDataSetChanged();
                                finishpage = 1;
                                finishrefresh.setRefreshing(false);
                            }

                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            finishrefresh.setRefreshing(false);
                        }
                    });


                } else {
                    finishrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        OrderDAO.getOrderList(1, "return_goods", new BaseCallBack() {
            @Override
            public void success(Object data) {
                returnlist = (ArrayList<OrderListBean>) data;
                returnlistview.setLayoutManager(returnmanager);
                returnadapter = new orderAdapter(context, returnlist);
                returnlistview.setAdapter(returnadapter);
                returnlistview.setOnScrollListener(new OnRcvScrollListener() {
                    @Override
                    public void isBottom() {
                        returnpage++;
                        OrderDAO.getOrderList(returnpage, "return_goods", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                returnlist.addAll(list2);
                                returnadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {

                            }
                        });
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode != 1001) {
                    Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                returnrefresh.setRefreshing(true);
                if (EasyPermissionsEx.isNetworkAvailable(OrderActivity.this)) {
                    OrderDAO.getOrderList(1, "return_goods", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (returnadapter != null) {
                                Toast.makeText(SystemUtil.getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                returnlist.clear();
                                returnadapter.clearPoition();
                                ArrayList<OrderListBean> list2 = (ArrayList<OrderListBean>) data;
                                returnlist.addAll(list2);
                                returnadapter.notifyDataSetChanged();
                                returnpage = 1;
                                returnrefresh.setRefreshing(false);
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(OrderActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            returnrefresh.setRefreshing(false);
                        }
                    });


                } else {
                    returnrefresh.setRefreshing(false);
                    Toast.makeText(SystemUtil.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        int five = one * 5;//1->6

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
                } else if (orderCurrIndex == 5) {
                    animation = new TranslateAnimation(five, 0, 0, 0);
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
                } else if (orderCurrIndex == 5) {
                    animation = new TranslateAnimation(five, one, 0, 0);
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
                } else if (orderCurrIndex == 5) {
                    animation = new TranslateAnimation(five, two, 0, 0);
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
                } else if (orderCurrIndex == 5) {
                    animation = new TranslateAnimation(five, three, 0, 0);
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
                } else if (orderCurrIndex == 5) {
                    animation = new TranslateAnimation(five, four, 0, 0);
                }
                break;
            case 5:
                if (orderCurrIndex == 0) {
                    animation = new TranslateAnimation(orderOffset, five, 0, 0);
                } else if (orderCurrIndex == 1) {
                    animation = new TranslateAnimation(one, five, 0, 0);
                } else if (orderCurrIndex == 2) {
                    animation = new TranslateAnimation(two, five, 0, 0);
                } else if (orderCurrIndex == 3) {
                    animation = new TranslateAnimation(three, five, 0, 0);
                } else if (orderCurrIndex == 4) {
                    animation = new TranslateAnimation(four, five, 0, 0);
                }
                break;
        }
        orderCurrIndex = position;
        if (animation != null) {
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(animationTime);
            cursor.startAnimation(animation);
        }

        if (position != currentPage) {
            for (int i = 0; i < btnlist.size(); i++) {
                btnlist.get(i).setTextColor(getResources().getColor(R.color.colorBottomBlack));
            }
            btnlist.get(position).setTextColor(getResources().getColor(R.color.colorBottomRed));
        }
        currentPage = viewpagerOrder.getCurrentItem();
//        animationTime = 300;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void initCursorPos() {
        // 初始化动画
        if (orderBmpw != 0) return;
        orderBmpw = BitmapFactory.decodeResource(getResources(), R.drawable.xiahuaxian).getWidth();// 获取图片宽度

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        orderOffset = (screenW / viewList.size() - orderBmpw) / 2;// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(orderOffset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }


    @OnClick({R.id.rl_icon1, R.id.rl_icon2, R.id.rl_icon3, R.id.rl_icon4, R.id.rl_icon5, R.id.rl_icon6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_icon1:
                clickPage = 0;
                break;
            case R.id.rl_icon2:
                clickPage = 1;
                break;
            case R.id.rl_icon3:
                clickPage = 2;
                break;
            case R.id.rl_icon4:
                clickPage = 3;
                break;
            case R.id.rl_icon5:
                clickPage = 4;
                break;
            case R.id.rl_icon6:
                clickPage = 5;
                break;
        }

        if (clickPage != currentPage) {
            viewpagerOrder.setCurrentItem(clickPage);
//            btnlist.get(clickPage).setTextColor(getResources().getColor(R.color.colorBottomRed));
        }
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
                animation.setDuration(0);
                cursor.startAnimation(animation);
            }
        } catch (Exception e) {
//            Log.e(TAG, "reSetImageMatrix: ", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
