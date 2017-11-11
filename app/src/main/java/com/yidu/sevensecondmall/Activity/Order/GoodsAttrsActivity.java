package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.bean.Order.SkuBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IAttrsEvent;
import com.yidu.sevensecondmall.utils.Sku;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.TextUtil;
import com.yidu.sevensecondmall.utils.UiData;
import com.yidu.sevensecondmall.views.adapter.GridAdapter;
import com.yidu.sevensecondmall.views.adapter.TypeItemAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/3/20.
 */
public class GoodsAttrsActivity extends BaseActivity {
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.big_pic)
    ImageView bigPic;
    @BindView(R.id.small_banner)
    BGABanner smallBanner;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.goods_content)
    TextView goodsContent;
    @BindView(R.id.priceleft)
    TextView priceleft;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.all_content)
    RelativeLayout allContent;
    @BindView(R.id.type_list)
    RecyclerView typeList;
    @BindView(R.id.type)
    LinearLayout type;
    @BindView(R.id.howmuch)
    TextView howmuch;
    @BindView(R.id.minus)
    LinearLayout minus;
    @BindView(R.id.plus)
    LinearLayout plus;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl_count)
    LinearLayout rlCount;
    @BindView(R.id.c_count)
    RelativeLayout cCount;
    @BindView(R.id.counts)
    LinearLayout counts;
    @BindView(R.id.addcar)
    LinearLayout addcar;
    @BindView(R.id.Imbuy)
    LinearLayout Imbuy;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.getsale)
    TextView getsale;
    @BindView(R.id.store_count)
    TextView storeCount;
    private String id = "";
    private AttrsBean bean;
    private GoodInfoBean goodbean;
    private GridAdapter gridAdapter;
    private BGABanner.Adapter banneradapter;
    private List<AttrsBean.GoodsSpecListBean> speclist = new ArrayList<>();
    private List<String> sort = new ArrayList<>();//种类的list
    private HashMap<String, List<AttrsBean.GoodsSpecListBean>> allsort = new HashMap<>();//全部用二维数组存储起来
    private TypeItemAdapter typeItemAdapter;
    private LinearLayoutManager manager;
    private List<AttrsBean.MulBean> multipleAttrs = new ArrayList<>();
    private HashMap<String,SkuBean> map = new HashMap<>();
    private UiData result = new UiData();
    private SpecHelper helper = SpecHelper.getInstance();

    public GoodsAttrsActivity(){
//        EventBus.getDefault().register(this);
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_goodsattrs;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        Intent i = getIntent();
        if (i.hasExtra("id")) {
            id = i.getStringExtra("id");
        }
        helper.getMap().clear();
        titleName.setText("商品属性");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerGrid(IAttrsEvent event){
        typeItemAdapter.setKey(event.founctionTag);
        typeItemAdapter.notifyDataSetChanged();
    }



    @Override
    protected void loadData() {
        if (!id.equals("")) {
            GoodsDAO.GetGoodAttrs(id, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (AttrsBean) data;
                        Log.e("list1", bean.getMullist() + "");
                        multipleAttrs = bean.getMullist();


                        if (bean.getGoods_spec_list() != null && bean.getGoods_spec_list().size() > 0) {
                            speclist = bean.getGoods_spec_list();

                            for(int i = 0;i < multipleAttrs.size();i++){
                                SkuBean sku = new SkuBean(Double.parseDouble(multipleAttrs.get(i).getPrice()),multipleAttrs.get(i).getCount());
                                map.put(multipleAttrs.get(i).getKey(),sku);
                            }

                            //计算所有子组合,存放到uidata类
                            result.setResult(Sku.skuCollection(map));

                            //筛选，选出多少类型
                            for (int i = 0; i < speclist.size(); i++) {
                                if (!sort.contains(speclist.get(i).getSpec_name())) {
                                    sort.add(speclist.get(i).getSpec_name());
                                }
                            }

                            //筛选的每种种类具体有多少种类型
                            for (int i = 0; i < sort.size(); i++) {
                                List<AttrsBean.GoodsSpecListBean> itemlist = new ArrayList<AttrsBean.GoodsSpecListBean>();
                                for (int j = 0; j < speclist.size(); j++) {
                                    if (sort.get(i).equals(speclist.get(j).getSpec_name())) {
                                        //同时设置对应的数量
                                        for(Map.Entry<String,SkuBean> item : result.getResult().entrySet()){
                                            String s = speclist.get(j).getItem_id()+"";
                                            String s2 = item.getKey();
                                            Log.e("iid",speclist.get(j).getItem_id()+"");
                                            Log.e("key",item.getKey());
                                            if(item.getKey().equals(String.valueOf(speclist.get(j).getItem_id()))){
                                                speclist.get(j).setCount(item.getValue().getStock());
                                            }
                                        }
                                        itemlist.add(speclist.get(j));
                                    }
                                }
                                allsort.put(sort.get(i), itemlist);
                            }


                            //初始化那个不可点击
                            for(Map.Entry<String,SkuBean> item : result.getResult().entrySet()){
                                for(Map.Entry<String,List<AttrsBean.GoodsSpecListBean>> item2 : allsort.entrySet()){
                                    for(int i = 0;i < item2.getValue().size();i++){
                                        if( item2.getValue().get(i).getCount() <= 0){
                                            item2.getValue().get(i).setStatus(2);
                                        }
                                    }
                                }
                                Log.e("resultmap","key:"+item.getKey()+"price:"+item.getValue().getPrice()+"stock:"+item.getValue().getStock());
                            }


                            //创建相应数量的listitem,加入赛选后的名字
                            manager = new LinearLayoutManager(GoodsAttrsActivity.this);
                            manager.setOrientation(LinearLayoutManager.VERTICAL);
                            typeList.setLayoutManager(manager);
                            typeItemAdapter = new TypeItemAdapter(GoodsAttrsActivity.this, sort, allsort,result);
                            typeList.setAdapter(typeItemAdapter);
                        }

                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                }
            });

            GoodsDAO.GetGoodsInfo(id, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        goodbean = (GoodInfoBean) data;
                        if (goodbean.getGallery() != null && goodbean.getGallery().size() > 0) {
                            smallBanner.setData(goodbean.getGallery(), null);
                            banneradapter = new BGABanner.Adapter() {
                                @Override
                                public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                                    Glide.with(GoodsAttrsActivity.this)
                                            .load(((GoodInfoBean.GalleryBean) model).getImage_url() + "")
                                            .placeholder(R.drawable.default_loading_pic)
                                            .into((ImageView) view);
                                }
                            };
                            smallBanner.setAdapter(banneradapter);
                        }

                        if (goodbean.getGoods() != null) {
                            goodsContent.setText(goodbean.getGoods().getGoods_name() + "");
                            price.setText(goodbean.getGoods().getShop_price() + "");
                            score.setText("购物即送"+goodbean.getGoods().getGive_integral()+"积分");
                            if(goodbean.getGoods().getProminfo() != null){
                                getsale.setText(goodbean.getGoods().getProminfo().getName()+"");
                            }else {
                                getsale.setText("暂无优惠");
                            }
                            storeCount.setText(goodbean.getGoods().getStore_count()+"套");
                            Glide.with(GoodsAttrsActivity.this)
                                    .load(goodbean.getGoods().getOriginal_img()+"")
                                    .placeholder(R.drawable.default_loading_pic)
                                    .into(bigPic);
                        }

                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    Toast.makeText(SystemUtil.getContext(),""+data,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onDestroy(){
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public boolean checkSpec(){
        if(multipleAttrs.size()>0){
            if(helper.getMap().size() <= 0 || helper.getMap().size() < sort.size()){
                Toast.makeText(GoodsAttrsActivity.this,"请选择规格或者选择完整规格",Toast.LENGTH_SHORT).show();
                return false;
            }
            for(Map.Entry<String,SkuBean> item : map.entrySet()){
                if(item.getKey().equals(TextUtil.tranBack(helper.getMap(),sort)) && item.getValue().getStock() < 0){
                    Toast.makeText(GoodsAttrsActivity.this,"库存不足",Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }


    @OnClick({R.id.back, R.id.addcar, R.id.Imbuy, R.id.minus, R.id.plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addcar:
                if(checkSpec()){
                    Intent trolley = new Intent(GoodsAttrsActivity.this, TrolleyActivity.class);
                    startActivity(trolley);
                }

                break;
            case R.id.Imbuy:
                if(checkSpec()){
                    Intent Imbuy = new Intent(GoodsAttrsActivity.this, OrderSuccessActivity.class);
                    startActivity(Imbuy);
                }
                break;
            case R.id.minus:
                int count = Integer.parseInt(tvCount.getText().toString());
                if (count > 0) {
                    count--;
                    tvCount.setText(count + "");
                }
                break;
            case R.id.plus:
                int count2 = Integer.parseInt(tvCount.getText().toString());
                if (count2 > 0) {
                    count2++;
                    tvCount.setText(count2 + "");
                }
                break;
        }
    }
}
