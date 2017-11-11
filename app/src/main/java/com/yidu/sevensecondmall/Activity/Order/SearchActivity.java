package com.yidu.sevensecondmall.Activity.Order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.GoodListBean;
import com.yidu.sevensecondmall.bean.Order.SearchHotBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.SearchResultAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/23.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.back_button)
    IconFontTextView backButton;
//    @BindView(R.id.iv_default)
//    IconFontTextView ivDefault;
//    @BindView(R.id.layout_default)
//    LinearLayout layoutDefault;

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_title)
    RelativeLayout searchTitle;
    //    @BindView(R.id.hot_list)
//    RecyclerView hotList;

    @BindView(R.id.result)
    RecyclerView rvResult;
    @BindView(R.id.search)
    TextView tvSearch;
    @BindView(R.id.fl_hot_search)
    TagFlowLayout flHotSearch;
    @BindView(R.id.search_hot)
    LinearLayout searchHot;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.rl_tab)
    LinearLayout rlTab;

    private SearchHotBean hotbean;
    private List<String> hotlist = new ArrayList<>();

    TagAdapter<String> mHotSearchAdapter = null;//热门搜索
    List<String> mHotSearchInfo = new ArrayList<>();

    private SearchResultAdapter adapter;
    private ArrayList<GoodListBean> list = new ArrayList<>();
    private GridLayoutManager layoutManager;


    @Override
    protected int setViewId() {
        return R.layout.search_activity;
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
        searchEdit.setFocusableInTouchMode(true);

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    showSearchByKey(searchEdit.getText().toString());
                }
                return false;
            }
        });

        if (mHotSearchAdapter == null) {
            initHotSearchView();
        }
        adapter = new SearchResultAdapter(this, list);
        layoutManager = new GridLayoutManager(this, 2);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvResult.setLayoutManager(layoutManager);
        rvResult.setAdapter(adapter);
        loadDataByIntent();
    }

    private void initHotSearchView() {

        mHotSearchAdapter = new TagAdapter<String>(mHotSearchInfo) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_search_tag, parent, false);
                tv.setText(s);
                return tv;
            }
        };

        flHotSearch.setAdapter(mHotSearchAdapter);


        flHotSearch.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String sear = mHotSearchInfo.get(position);
                searchEdit.setText(sear);
                showSearchByKey(sear);
                return true;
            }
        });
    }

    @Override
    protected void loadData() {
        GoodsDAO.Hotkeywords(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    hotbean = (SearchHotBean) data;
                    hotlist = hotbean.getHotkeywords();
                    mHotSearchInfo.clear();
                    mHotSearchInfo.addAll(hotlist);
                    mHotSearchAdapter.notifyDataChanged();

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(SearchActivity.this, "" + data, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadDataByIntent() {

//       if(getIntent().hasExtra("ad_id")){
//           String ad_id =  getIntent().getStringExtra("ad_id");
//           Log.e("ad_id",ad_id+"loadDataByIntent");
//            GoodsDAO.SearchGoods(ad_id, new BaseCallBack() {
//                @Override
//                public void success(Object data) {
//                    ArrayList<GoodListBean> list = (ArrayList<GoodListBean>) data;
//                    if (list.size() != 0) {
//                        searchHot.setVisibility(View.GONE);
//                        rvResult.setVisibility(View.VISIBLE);
//                    } else {
//                        Toast.makeText(SearchActivity.this, "没有找到相应的商品", Toast.LENGTH_SHORT).show();
//                    }
//                    adapter.refreshData(list);
//                }
//
//                @Override
//                public void failed(int errorCode, Object data) {
//                    Toast.makeText(SearchActivity.this, "" + data, Toast.LENGTH_SHORT).show();
//                }
//            });
//
//       }else
//        if (getIntent().hasExtra("id")) {
//            GoodsDAO.searchGoods(getIntent().getIntExtra("id", 0), new BaseCallBack() {
//                @Override
//                public void success(Object data) {
//                    Log.e("id___",getIntent().getIntExtra("id",0)+"");
//                    ArrayList<GoodListBean> list = (ArrayList<GoodListBean>) data;
//                    if (list.size() != 0) {
//                        searchHot.setVisibility(View.GONE);
//                        rvResult.setVisibility(View.VISIBLE);
//                    } else {
//                        Toast.makeText(SearchActivity.this, "没有找到相应的商品", Toast.LENGTH_SHORT).show();
//                    }
//                    adapter.refreshData(list);
//                }
//
//                @Override
//                public void failed(int errorCode, Object data) {
//                    Toast.makeText(SearchActivity.this, "" + data, Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else if (getIntent().hasExtra("keyword")) {
//            String keyword = getIntent().getStringExtra("keyword");
//            searchEdit.setText(keyword);
//            searchEdit.setSelection(keyword.length() - 1);
//            keyword = keyword == null ? "" : keyword;
//            showSearchByKey(keyword);
//        }else{
//            searchHot.setVisibility(View.VISIBLE);
//        }

        if(getIntent().hasExtra("keyword")){
            showSearchByKey(getIntent().getStringExtra("keyword"));
        } else
        if (getIntent() .hasExtra("nav_type")&& getIntent().hasExtra("more_id")) {
            String nav_type = getIntent() .getStringExtra("nav_type");
            String more_id = getIntent().getStringExtra("more_id");
            SearchGoods(nav_type,more_id);
        } else {
            searchHot.setVisibility(View.VISIBLE);
        }


    }


    private void SearchGoods(String nav_type,String more_id) {
        GoodsDAO.SearchGoods(nav_type, more_id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                ArrayList<GoodListBean> list = (ArrayList<GoodListBean>) data;
                    if (list.size() != 0) {
                        searchHot.setVisibility(View.GONE);
                        rvResult.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(SearchActivity.this, "没有找到相应的商品", Toast.LENGTH_SHORT).show();
                    }
                    adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(SearchActivity.this, "" + data, Toast.LENGTH_SHORT).show();
            }
        });
//        OkHttpUtil.postSubmitForm(HttpApi.search, params, new OkHttpUtil.OnDownDataListener() {
//            @Override
//            public void onResponse(String url, String json) {
//                Log.e("搜索的URL", url);
//                Log.e("搜索返回的JSON", json);
//                SearchMoreBean bean = new Gson().fromJson(json, SearchMoreBean.class);
//                if (bean.getCode() == 0) {
//                    List<SearchMoreBean.ResultBean.ListBean> listBean = bean.getResult().getList();
//                    if (listBean.size() > 0 && listBean != null) {
//                        searchHot.setVisibility(View.GONE);
//                        rvResult.setVisibility(View.VISIBLE);
//                        list.addAll(listBean);
//                        adapter.refreshData(list);
//                    } else {
//                        Toast.makeText(SearchActivity.this, "没有找到相应的商品", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    ToastUtil.showToast(SearchActivity.this, bean.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(String url, String error) {
//
//            }
//        });


    }


    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.back_button, R.id.search, R.id.tv_normal, R.id.tv_sell, R.id.tv_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.search:
                String keyword = searchEdit.getText().toString();
                showSearchByKey(keyword);
                break;
        }
    }

    private void showSearchByKey(String keyword) {
        if (keyword == null) keyword = "";

//        HashMap<String ,String > params = new HashMap<>();
//        params.put("q",keyword);
//        params.put("unique_id", SystemUtil.getSharedString("deviceId"));
//        OkHttpUtil.postSubmitForm(HttpApi.search, params, new OkHttpUtil.OnDownDataListener() {
//            @Override
//            public void onResponse(String url, String json) {
//                SearchMoreBean bean = new Gson().fromJson(json,SearchMoreBean.class);
//                if (bean.getCode() == 0) {
//                    List<SearchMoreBean.ResultBean.ListBean> listBean = bean.getResult().getList();
//                    if (listBean.size() > 0 && listBean != null) {
//                        searchHot.setVisibility(View.GONE);
//                        rvResult.setVisibility(View.VISIBLE);
//                        list.addAll(listBean);
//                        adapter.refreshData(list);
//                    } else {
//                        showShortToast("没有找到相应的商品");
//                    searchHot.setVisibility(View.VISIBLE);
//                    rvResult.setVisibility(View.GONE);
////                        Toast.makeText(SearchActivity.this, "没有找到相应的商品", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    ToastUtil.showToast(SearchActivity.this, bean.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(String url, String error) {
//
//            }
//        });


        GoodsDAO.searchGoods(keyword, new BaseCallBack() {
            @Override
            public void success(Object data) {

                ArrayList<GoodListBean> list = (ArrayList<GoodListBean>) data;
                if (list.size() != 0) {
                    searchHot.setVisibility(View.GONE);
                    rvResult.setVisibility(View.VISIBLE);
                } else {
                    showShortToast("没有找到相应的商品");
                    searchHot.setVisibility(View.VISIBLE);
                    rvResult.setVisibility(View.GONE);
                }
                adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

}
