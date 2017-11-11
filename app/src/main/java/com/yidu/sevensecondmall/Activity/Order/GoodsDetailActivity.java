package com.yidu.sevensecondmall.Activity.Order;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.readystatesoftware.viewbadger.BadgeView;
import com.se7en.utils.DeviceUtils;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.MainActivity;
import com.yidu.sevensecondmall.Activity.NewMainActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.CollectionActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.DistrictModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.utils.onekeyshare.OnekeyShare;
import com.yidu.sevensecondmall.utils.onekeyshare.OnekeyShareTheme;
import com.yidu.sevensecondmall.views.adapter.HomePagerAdapter;
import com.yidu.sevensecondmall.views.widget.AttrsPopwindow;
import com.yidu.sevensecondmall.views.widget.BottomScrollView;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;
import com.yidu.sevensecondmall.views.widget.refreshlayout.RefreshLoadMoreLayout;
import com.yidu.sevensecondmall.views.widget.widget.OnWheelChangedListener;
import com.yidu.sevensecondmall.views.widget.widget.WheelView;
import com.yidu.sevensecondmall.views.widget.widget.adapters.ArrayWheelAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/3/17.
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity{

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    @BindView(R.id.rb_picture)
    RadioButton rbPicture;
    @BindView(R.id.rb_format)
    RadioButton rbFormat;
    @BindView(R.id.rb_service)
    RadioButton rbService;
    @BindView(R.id.rg_moredetail)
    RadioGroup rgMoredetail;
    //    @BindView(R.id.fl_container)
//    FrameLayout flContainer;
    @BindView(R.id.refresh)
    RefreshLoadMoreLayout refresh;
    @BindView(R.id.elva_item)
    RelativeLayout elvaItem;
    @BindView(R.id.pop_add)
    IconFontTextView popAdd;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.add_item)
    RelativeLayout addItem;
    @BindView(R.id.pop_attrs)
    ImageView popAttrs;
    @BindView(R.id.good_select)
    TextView goodSelect;
    @BindView(R.id.select_name)
    TextView selectName;
    @BindView(R.id.select_num)
    TextView selectNum;
    @BindView(R.id.select_item)
    RelativeLayout selectItem;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.banner_good_flip)
    BGABanner bannerGoodFlip;
    @BindView(R.id.sv)
    BottomScrollView sv;
    @BindView(R.id.refreshloadmore)
    RefreshLoadMoreLayout refreshloadmore;
    @BindView(R.id.collection_txt)
    IconFontTextView collectionTxt;
    @BindView(R.id.collection)
    LinearLayout collection;
    @BindView(R.id.addcar)
    LinearLayout addcar;
    @BindView(R.id.Imbuy)
    LinearLayout Imbuy;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.back)
    IconFontTextView back;
    //    @BindView(R.id.rl_title)
//    RelativeLayout rlTitle;
    @BindView(R.id.banner)
    LinearLayout banner;
    @BindView(R.id.icon_share)
    IconFontTextView iconShare;
    @BindView(R.id.pop_img)
    IconFontTextView popImg;
    @BindView(R.id.img)
    TextView img;
    @BindView(R.id.img_item)
    RelativeLayout imgItem;
    @BindView(R.id.cat_txt)
    IconFontTextView catTxt;
    @BindView(R.id.cart)
    RelativeLayout cart;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.parm)
    TextView parm;
    @BindView(R.id.tip)
    BadgeView tip;
    @BindView(R.id.vp_container)
    ViewPager vp;


    private boolean isShow = false;
    private int offset;
    private String id = "";
    public GoodInfoBean bean;
    private BGABanner.Adapter adapter;
    private List<GoodInfoBean.GalleryBean> piclist = new ArrayList<>();

    private AttrsPopwindow popwindow;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private RadioButton lastRB;
    private boolean isCollected = false;

    private boolean hasType = true;

    protected int setViewId() {
        return R.layout.activity_goodsdetails;
    }


    protected void findViews() {
        ButterKnife.bind(this);
        Intent i = getIntent();
        if (i.hasExtra("id")) {
            id = String.valueOf(i.getIntExtra("id", 0));
        }
        popwindow = new AttrsPopwindow(GoodsDetailActivity.this, id);
        popwindow.setCancelable(true);
        tip.setVisibility(View.INVISIBLE);

    }


    protected void initEvent() {

    }

    private int getCo(int color) {
        return ContextCompat.getColor(GoodsDetailActivity.this, color);
    }

    private List<View> childViews = new ArrayList<>();
    private int position;
    private void setViewPager() {
        rgMoredetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_picture:
                        position = 0;
                        break;
                    case R.id.rb_format:
                        position = 1;
                        break;
                    case R.id.rb_service:
                        position = 2;
                        break;
                }
                vp.setCurrentItem(position);
            }
        });

        View webPic = getLayoutInflater().inflate(R.layout.text_fragment_layout, null);
        WebView wb = (WebView) webPic.findViewById(R.id.wb);
        Log.e("getGoods_content======",bean.getGoods().getGoods_content());
        initWebView(wb, bean.getGoods().getGoods_content());

        View detail = getLayoutInflater().inflate(R.layout.layout, null);
        ListView detaillist = (ListView) detail.findViewById(R.id.detaillist);
        getData();
        detaillist.setAdapter(new ArrayAdapter<String>(GoodsDetailActivity.this, R.layout.item_standard, list));

        View inflate = getLayoutInflater().inflate(R.layout.service_layout, null);
        TextView tv = (TextView) inflate.findViewById(R.id.service_txt);
        tv.setText(Html.fromHtml("\n" +
                "\n" +
                "\t服务承诺：</strong>\n" +
                "\n" +
                "商城向您保证所售商品均为正品行货，自营商品开具机打发票或电子发票。凭质保证书及商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。商城还为您提供具有竞争力的商品价格和运费政策，请您放心购买！\n" +
                "\n" +
                "注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\t权利声明：</strong>\n" +
                "\n" +
                "商城上的所有商品信息、客户评价、商品咨询、网友讨论等内容，是商城重要的经营资源，未经许可，禁止非法转载使用。\n" +
                "\t\n" +
                "\n" +
                "\t\t注：</b>本站商品信息均来自于厂商，其真实性、准确性和合法性由信息拥有者（厂商）负责。本站不提供任何保证，并不承担任何法律责任。\n" +
                "\t</p>\n" +
                "</div>"));

        childViews.add(webPic);
        childViews.add(detail);
        childViews.add(inflate);

        HomePagerAdapter pageAdapter = new HomePagerAdapter(childViews);
        vp.setAdapter(pageAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbPicture.setTextColor(getCo(R.color.colorBlack));
                        rbFormat.setTextColor(getCo(R.color.colorGray));
                        rbService.setTextColor(getCo(R.color.colorGray));
                        break;
                    case 1:
                        rbPicture.setTextColor(getCo(R.color.colorGray));
                        rbFormat.setTextColor(getCo(R.color.colorBlack));
                        rbService.setTextColor(getCo(R.color.colorGray));
                        break;
                    case 2:
                        rbPicture.setTextColor(getCo(R.color.colorGray));
                        rbFormat.setTextColor(getCo(R.color.colorGray));
                        rbService.setTextColor(getCo(R.color.colorBlack));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<String> list = new ArrayList<>();

    public void getData() {
        try {
            list.add("名称：" + bean.getGoods().getGoods_name());
            list.add("单号：" + bean.getGoods().getGoods_sn());
            list.add("重量：" + bean.getGoods().getWeight());
            list.add("数量：" + bean.getGoods().getStore_count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean istop = true;
    private void initWebView(WebView webView, String content) {
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (v.getScrollY() <= 0) {
                            istop = true;
                            refresh.setCanRefresh(true);
                        } else {
                            istop = false;
                            refresh.setCanRefresh(false);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        Log.e(TAG, "initWebView: ------");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
//        webView.setWebChromeClient(webChromeClient);
//        webView.setWebViewClient(webViewClient);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        webView.loadDataWithBaseURL("http://avatar.csdn.net", getNewContent(content), "text/html", "UTF-8", null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    private String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        return doc.toString();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshEvent ev) {
        if (ev.founctionTag == IEventTag.REFRESHTEXT) {
            if (popwindow.getKetName() != null) {
                selectNum.setText(popwindow.getKetName());
            }
        }else if(ev.founctionTag == IEventTag.UPNUM){
            updateCart();
            return;
        }
        loadNetData();
    }

    @Override
    protected void onDestroy() {
        popwindow.clearData();
        super.onDestroy();
    }

    public GoodInfoBean getBean() {
        return bean;
    }

    private static final String TAG = "GoodsDetailActivity";

    protected void init() {
        loadNetData();
    }


    private void loadNetData(){
        GoodsDAO.GetGoodAttrs(id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    AttrsBean bean = (AttrsBean) data;
                    if (bean.getMullist() != null && bean.getMullist().size() > 0) {

                    }
                    if (bean.getGoods_spec_list() == null || bean.getGoods_spec_list().size() == 0) {
                        hasType = false;
                        selectItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showToast(GoodsDetailActivity.this, "没有其他");
                            }
                        });
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
        //读取网络数据
        GoodsDAO.GetGoodsInfo(id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    bean = (GoodInfoBean) data;
                    piclist = bean.getGallery();
                    popwindow.setBanner(piclist);
                    if (piclist.size() > 0) {
                        bannerGoodFlip.setData(piclist, null);
                        adapter = new BGABanner.Adapter() {
                            @Override
                            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                                Glide.with(GoodsDetailActivity.this)
                                        .load(HttpApi.getFullImageUrl(((GoodInfoBean.GalleryBean) model).getImage_url()))
                                        .asBitmap()
                                        .centerCrop()
                                        .placeholder(R.drawable.default_loading_pic)
                                        .into((ImageView) view);
                            }
                        };
                        bannerGoodFlip.setAdapter(adapter);
                    }
                    if (bean.isCollected()) {
                        collectionTxt.setText(R.string.icon_collect);
                        collectionTxt.setTextColor(getResources().getColor(R.color.light_red));
                        isCollected = true;
                    } else {
                        collectionTxt.setText(R.string.icon_collect2);
                        collectionTxt.setTextColor(getResources().getColor(R.color.black));
                        isCollected = false;
                    }
                    if (bean.getGoods() != null) {
                        goodsName.setText(bean.getGoods().getGoods_name() + "");
                        price.setText(String.valueOf(bean.getGoods().getShop_price() + ""));

                        point.setText(+bean.getGoods().getGive_integral() + "积分奖励");
                        if (bean.getGoods().getProminfo() != null) {
                            parm.setText(bean.getGoods().getProminfo().getName() + "");
                        } else {
                            parm.setText("暂无优惠");
                        }
                        if (bean.getCartcount() > 0) {
                            tip.setVisibility(View.VISIBLE);
                            tip.setText(bean.getCartcount() + "");
                        } else {
                            tip.setVisibility(View.INVISIBLE);
                        }
                        int ivHeight = DeviceUtils.getScreenHeight() - DeviceUtils.getStatuBarHeight();
                        offset = ivHeight;
                        refresh.setTranslationY(offset);
                        loadAndRefreshAnimation();

                        setViewPager();
                    }

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if(errorCode == 1005){
                    LoginUtils.setIsLogin(false);
                    loadNetData();
                }else {
                    showShortToast(data+"");
                }
            }
        });
    }



    public void updateCart(){
        GoodsDAO.GetGoodsInfo(id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    bean = (GoodInfoBean) data;
                    piclist = bean.getGallery();
                    if (bean.getGoods() != null) {
                        if (bean.getCartcount() > 0) {
                            tip.setVisibility(View.VISIBLE);
                            tip.setText(bean.getCartcount() + "");
                        } else {
                            tip.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if(errorCode == 1005){
                    LoginUtils.setIsLogin(false);
                    loadNetData();
                }else {
                    showShortToast(data+"");
                }
            }
        });
    }


    //上拉下拉动画
    private void loadAndRefreshAnimation() {
        refreshloadmore.setLoadMoreReleaseText("松开查看更多");
        refreshloadmore.setLoadMoreUpText("上拉查看更多");
        refreshloadmore.setonBackNormalStatusText("正在查看");
        //上拉
        refreshloadmore.init(new RefreshLoadMoreLayout.Config(new RefreshLoadMoreLayout.CallBack() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                loadUpAnimation(true);
                refreshloadmore.stopLoadMore();
            }
        }));
        refreshloadmore.setCanRefresh(false);


        //下拉
        refresh.setPullDownText("下拉回到首页");
        refresh.setPullRefreshText("松开回到首页");
        refresh.setOnRefreshText("正在回到首页");
        refresh.init(new RefreshLoadMoreLayout.Config(new RefreshLoadMoreLayout.CallBack() {
            @Override
            public void onRefresh() {
                loadUpAnimation(false);
                refresh.stopRefresh();
            }

            @Override
            public void onLoadMore() {

            }
        }));
        refresh.setCanLoadMore(false);
    }

    @Override
    protected void onResume() {
        if (popwindow != null) {
            popwindow.clear();
        }
        super.onResume();
    }

    //开始动画
    private void loadUpAnimation(final boolean up) {
        int a;
        int b;
        //判断是向上动画还是向下
        if (up) {
            a = 0;
            b = 1;
        } else {
            a = 1;
            b = 0;
        }
        if (isShow) {
            return;
        }
        isShow = true;
        ObjectAnimator animator = null;
        animator = ObjectAnimator.ofFloat(this, "xxx", a, b).setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateView((float) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (up) {
                    back.setVisibility(View.GONE);
                } else {
                    back.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShow = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }


    private void updateView(float progress) {
        refreshloadmore.setTranslationY((-DeviceUtils.getScreenHeight() + DeviceUtils.getStatuBarHeight()) * progress);
        refresh.setTranslationY(offset - offset * progress);
    }

    protected void loadData() {

    }

    private void setUpViews(View view) {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        setEvent();
        updateCities();
        updateAreas();
    }

    private void setEvent() {
        mViewProvince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
        mViewCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });

        mViewDistrict.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
//        mViewDistrict.setCurrentItem(0);
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            provinceList = PositionDao.getInstance().list;
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    @OnClick({R.id.back, R.id.Imbuy, R.id.addcar, R.id.collection_txt, R.id.pop_attrs, R.id.add_item, R.id.select_item, R.id.cart,
            R.id.icon_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.Imbuy:
                popwindow.setTip(0);
                popwindow.show();
                break;
            case R.id.addcar:
                popwindow.setTip(1);
                popwindow.show();
                break;
            case R.id.select_item:
                popwindow.clearKeyName();
                popwindow.setTip(2);
                popwindow.show();
                break;

            case R.id.pop_attrs:
                popwindow.show();
                break;
            case R.id.add_item:
                Intent i = new Intent(GoodsDetailActivity.this, GoodsEstimateExActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.collection_txt:
                if (!isCollected) {
                    GoodsDAO.collectGoods(0, id, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            Toast.makeText(SystemUtil.getContext(), "加入成功", Toast.LENGTH_SHORT).show();
                            collectionTxt.setText(R.string.icon_collect);
                            collectionTxt.setTextColor(getResources().getColor(R.color.light_red));
                            collectionTxt.setTextSize(14);
                            isCollected = true;
                            new AlertDialog.Builder(GoodsDetailActivity.this)
                                    .setMessage("加入成功")
                                    .setPositiveButton("前往收藏页面", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent collection = new Intent(GoodsDetailActivity.this, CollectionActivity.class);
                                            startActivity(collection);
                                        }
                                    })
                                    .setNegativeButton("留在本页面", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(GoodsDetailActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    GoodsDAO.collectGoods(1, id, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            collectionTxt.setText(R.string.icon_collect2);
                            collectionTxt.setTextColor(getResources().getColor(R.color.black));

                            isCollected = false;
                            Toast.makeText(GoodsDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(GoodsDetailActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.cart:
                Intent toCart = new Intent(GoodsDetailActivity.this, NewMainActivity.class);
                SystemUtil.setSharedBoolean("toCart", true);
                startActivity(toCart);
                GoodsDetailActivity.this.finish();
                break;

            case R.id.icon_share:
                if (LoginUtils.isLogin()) {
                    setShareContent();
                    showShare(GoodsDetailActivity.this, null, false, shareTitle, shareTip, shareIcon);
                } else {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    GoodsDetailActivity.this.startActivity(intent);
                }
                break;
        }
    }

    private void setShareContent() {
        shareTitle = goodsName.getText().toString();
        shareTip = "我在" + "DING网" + "发现了一个不错的商品，赶快来看看吧";
        if (piclist != null && piclist.size() > 0) {
            shareIcon = HttpApi.getFullImageUrl(piclist.get(0).getImage_url());
        }
    }


    private String shareTitle = "";
    private String shareTip = "";
    private String shareIcon = "";

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit 是否显示编辑页
     */
    public static void showShare(Context context, String platformToShare, boolean showContentEdit,
                                 String shareTitle, String shareTip, String shareIcon) {
        if (shareTitle == null) {
            shareTitle = " ";
        }
        if (shareTip == null) {
            shareTip = " ";
        }
        if (shareIcon == null) {
            shareIcon = " ";
        }
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle(shareTitle);
        oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.yidu.sevensecondmall");
        oks.setText(shareTip);
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        oks.setImageUrl(shareIcon);
        oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.yidu.sevensecondmall"); //微信不绕过审核分享链接
//        oks.setFilePath("");  //filePath用于视频分享
//        oks.setComment(context.getString(R.string.app_share_comment)); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
//        oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.yidu.sevensecondmall");//QZone分享参数
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("分享商品!");
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        // oks.setCallback(new OneKeyShareCallback());
        // 去自定义不同平台的字段内容
        // oks.setShareContentCustomizeCallback(new
        // ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
//        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default);
//        String label = "ShareSDK";
//        View.OnClickListener listener = new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        };
//        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (popwindow.isShowing()) {
                popwindow.dismiss();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
