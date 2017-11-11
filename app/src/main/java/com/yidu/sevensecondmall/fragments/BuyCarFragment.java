package com.yidu.sevensecondmall.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.OrderSureActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.PriceEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.views.adapter.trolleyAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class BuyCarFragment extends BaseFragment {
    private static final String TAG = "BuyCarFragment";

    @BindView(R.id.car_list)
    RecyclerView carList;
    @BindView(R.id.tv_rmb)
    TextView tvRmb;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.toalcount2)
    TextView toalcount2;
    @BindView(R.id.total)
    RelativeLayout total;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.select_state)
    IconFontTextView selectState;
    @BindView(R.id.select_all)
    LinearLayout selectAll;
    @BindView(R.id.tv_to_buy)
    TextView tvToBuy;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    Unbinder unbinder;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    private boolean allstate = false;

    private CartlistBean bean = new CartlistBean();
    private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
    private SpecHelper specHelper = SpecHelper.getInstance();

    private trolleyAdapter adapter;
    private boolean state = false;
    private JSONArray array;
    private ProgressDialog dialog;
    private boolean hidden = true;

    @Override
    protected int setViewId() {
        return R.layout.fragment_buy_car;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
        toolbarTitle.setText("购物车");
        toolbarRight.setText("编辑");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEdit();
            }
        });
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void init() {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("同步数据中");
//        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getCurrentActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        carList.setLayoutManager(layoutManager);
//        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                adapter.changeAllState(isChecked);
//            }
//        });
        if (!LoginUtils.isLogin()) {
            getCartList();
        } else {
            getCartWithToken();
        }
        if (adapter != null) {
            adapter.changeAllState(false);
            selectState.setText(R.string.icon_un_select);
            selectState.setTextColor(Color.GRAY);
        }
        EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            if (!LoginUtils.isLogin()) {
                getCartList();
            } else {
                getCartWithToken();
            }
            if (adapter != null) {
                adapter.changeAllState(false);
                selectState.setText(R.string.icon_un_select);
                selectState.setTextColor(Color.GRAY);
            }
            EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
        }
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
            this.hidden = true;
        } else {// 重新显示到最前端中
            //清空选择
            this.hidden = false;
            if (!LoginUtils.isLogin()) {
                getCartList();
            } else {
                getCartWithToken();
            }
            if (adapter != null) {
                adapter.changeAllState(false);
                selectState.setText(R.string.icon_un_select);
                selectState.setTextColor(Color.GRAY);
            }
            EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
        }
    }

    private Activity getCurrentActivity() {
        return getActivity();
    }


    @Override
    protected void loadData() {
        selectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allstate) {
                    allstate = true;
                    selectState.setText(R.string.icon_select);
                    selectState.setTextColor(Color.RED);
                } else {
                    allstate = false;
                    selectState.setText(R.string.icon_un_select);
                    selectState.setTextColor(Color.GRAY);
                }
                adapter.changeAllState(allstate);
                dialog.show();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        obj.put("selected", "1");
                        array.put(i, obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (LoginUtils.isLogin()) {
                    GoodsDAO.sendCartJsonWithToken(array.toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            dialog.dismiss();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    GoodsDAO.sendCartJsonWithOutToken(array.toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            dialog.dismiss();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(PriceEvent event) {
//        adapter.changesingleState(event.founctionTag);
        if (!event.tag.equals("") && event.tag.equals("refresh")) {
            llEmpty.setVisibility(View.VISIBLE);
            toolbarRight.setVisibility(View.GONE);
            total.setVisibility(View.INVISIBLE);
        } else if (event.founctionTag > -1 && adapter != null) {
            double price = adapter.getTotalPrice();
            int count = adapter.getCount();
            totalPrice.setText("" + price);
            toolbarRight.setVisibility(View.VISIBLE);
            toalcount2.setText("结算(" + count + ")");
            total.setVisibility(View.VISIBLE);
            Log.e("price", price + "");
            Log.e("count", count + "");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void openEdit() {
        if (adapter != null) {
            state = !state;
            adapter.setEditState(state);
        }
    }


    @OnClick({R.id.toalcount2, R.id.tv_to_buy})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.toalcount2:
                if (LoginUtils.isLogin()) {
                    int count = adapter.getCount();
                    if (count != 0) {
                        intent = new Intent(getCurrentActivity(), OrderSureActivity.class);
                        startActivity(intent);
                    } else {
                        showShortToast("您还未选择要结算的商品");
                    }
                } else {
                    intent = new Intent(getCurrentActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_to_buy:
                EventBus.getDefault().post(new LoginEvent(IEventTag.SKIP_TO_HOME));
                break;
        }
    }

    public void getCartWithToken() {
        GoodsDAO.getCartListWithToken(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    carList.setVisibility(View.VISIBLE);
                    total.setVisibility(View.VISIBLE);
                    bean = (CartlistBean) data;
                    if (adapter == null) {
                        array = bean.getArray();
                        list = bean.getCartList();
                        specHelper.setJson(array);
                        carList.setFocusableInTouchMode(false);//设置不需要焦点
                        carList.requestFocus();
                        adapter = new trolleyAdapter(getCurrentActivity(), list);
                        carList.setAdapter(adapter);
                        if (list.size() > 0) {
                            carList.setVisibility(View.VISIBLE);
                            total.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                            carList.setVisibility(View.VISIBLE);
                            toolbarRight.setVisibility(View.VISIBLE);
                        } else {
                            carList.setVisibility(View.GONE);
                            total.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            toolbarRight.setVisibility(View.GONE);
                            carList.setVisibility(View.GONE);
                        }
                    } else {
                        Log.e(TAG, "refreshData: buy car " + list.size());
                        //刷新数据
                        list.clear();
                        List<CartlistBean.CartListBean> list2 = bean.getCartList();
                        list.addAll(list2);
                        adapter.notifyDataSetChanged();
                        if (list2.size() > 0) {
                            carList.setVisibility(View.VISIBLE);
                            total.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                            toolbarRight.setVisibility(View.VISIBLE);
                            carList.setVisibility(View.VISIBLE);
                        } else {
                            carList.setVisibility(View.GONE);
                            total.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            toolbarRight.setVisibility(View.GONE);
                            carList.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode == 1006 || errorCode == 1005) {
                    Log.e(TAG, "failed: " + data);
                    getCartList();
                } else if (errorCode == 1001) {
                    carList.setVisibility(View.GONE);
                    total.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                    toolbarRight.setVisibility(View.GONE);
                } else {
                    showShortToast(data + "");
                }
            }
        });
    }

    public void getCartList() {
        Log.e(TAG, "getCartList");
        GoodsDAO.getCartList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    bean = (CartlistBean) data;
                    if (adapter == null) {
                        list = bean.getCartList();
                        array = bean.getArray();
                        specHelper.setJson(array);
                        Log.e("array", array + "");
                        adapter = new trolleyAdapter(getCurrentActivity(), list);
                        carList.setAdapter(adapter);
                        if (list.size() > 0) {
                            carList.setVisibility(View.VISIBLE);
                            total.setVisibility(View.VISIBLE);
                            toolbarRight.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                            toolbarRight.setVisibility(View.VISIBLE);
                            carList.setVisibility(View.VISIBLE);
                        } else {
                            carList.setVisibility(View.GONE);
                            total.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            toolbarRight.setVisibility(View.GONE);
                            carList.setVisibility(View.GONE);
                            toolbarRight.setVisibility(View.GONE);
                        }
                    } else {
                        //刷新数据
                        list.clear();
                        List<CartlistBean.CartListBean> list2 = bean.getCartList();
                        list.addAll(list2);
                        adapter.notifyDataSetChanged();
                        if (list2.size() > 0) {
                            carList.setVisibility(View.VISIBLE);
                            total.setVisibility(View.VISIBLE);
                            llEmpty.setVisibility(View.GONE);
                            toolbarRight.setVisibility(View.VISIBLE);
                            carList.setVisibility(View.VISIBLE);
                        } else {
                            carList.setVisibility(View.GONE);
                            toolbarRight.setVisibility(View.GONE);
                            total.setVisibility(View.GONE);
                            llEmpty.setVisibility(View.VISIBLE);
                            carList.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode == 1006 || errorCode == 1005) {
                    Log.e(TAG, "failed: " + data);
                } else if (errorCode == 1001) {
                    carList.setVisibility(View.GONE);
                    total.setVisibility(View.GONE);
                    llEmpty.setVisibility(View.VISIBLE);
                    toolbarRight.setVisibility(View.GONE);
                } else {
                    showShortToast(data + "");
                }
            }
        });
    }

    public void loadCartList() {
//        if (!LoginUtils.isLogin()) {
//            getCartList();
//        } else {
//            getCartWithToken();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
