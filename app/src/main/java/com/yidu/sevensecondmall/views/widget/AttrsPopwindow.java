package com.yidu.sevensecondmall.views.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Order.OrderSureActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.SevenSecondApplication;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.bean.Order.SkuBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.Sku;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.TextUtil;
import com.yidu.sevensecondmall.utils.UiData;
import com.yidu.sevensecondmall.views.adapter.TypeItemAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/5/6.
 */
public class AttrsPopwindow extends BasePopWindow implements View.OnClickListener {


    @BindView(R.id.small_banner)
    ImageView smallBanner;
    //    @BindView(R.id.small_banner_linear)
//    LinearLayout smallBannerLinear;
    @BindView(R.id.delete)
    IconFontTextView delete;
    @BindView(R.id.goods_content)
    TextView goodsContent;
    @BindView(R.id.priceleft)
    TextView priceleft;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.store_count)
    TextView storeCount;
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.line)
    LinearLayout line;
    @BindView(R.id.type_list)
    RecyclerView typeList;
    @BindView(R.id.type)
    LinearLayout type;
    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.howmuch)
    TextView howmuch;
    @BindView(R.id.plus)
    IconFontTextView plus;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.minus)
    IconFontTextView minus;
    @BindView(R.id.ll_edit)
    RelativeLayout llEdit;
    @BindView(R.id.c_count)
    RelativeLayout cCount;
    @BindView(R.id.counts)
    LinearLayout counts;
    //    @BindView(R.id.addcar)
//    LinearLayout addcar;
    @BindView(R.id.Imbuy)
    LinearLayout Imbuy;
    @BindView(R.id.sv_type)
    ScrollView svType;
    private Context context;
    private String id;
    private AttrsBean bean;
    private GoodInfoBean goodbean;
    private BGABanner.Adapter banneradapter;
    private List<AttrsBean.GoodsSpecListBean> speclist = new ArrayList<>();
    private List<String> sort = new ArrayList<>();//种类的list
    private HashMap<String, List<AttrsBean.GoodsSpecListBean>> allsort = new HashMap<>();//全部用二维数组存储起来
    private TypeItemAdapter typeItemAdapter;
    private LinearLayoutManager manager;
    private List<AttrsBean.MulBean> multipleAttrs = new ArrayList<>();
    private HashMap<String, SkuBean> map = new HashMap<>();
    private UiData result = new UiData();
    private SpecHelper helper = SpecHelper.getInstance();
    private String good_sn = "";
    private List<GoodInfoBean.GoodsBean.GoodsSpecListBean> specNameList = new ArrayList<>();
    private boolean buynow = false;
    private JSONArray array;
    private int fw = 0;
    private int fm = 0;
    private int addw = 0;
    private int addm = 0;
    private int weight = 0;
    private String defalutspec = "";
    //计算邮费 round((weight - fw) / addw) * fm
    private String postprice = "";
    private int ideal;
    String KeyName = "";
    private boolean hasSpec;
    private String url = null;

    public AttrsPopwindow(Context context, String id) {
        super(context);
        this.context = context;
        this.id = id;
        View v;
        if (hasSpec) {
            v = LayoutInflater.from(context).inflate(R.layout.attrs_pop_layout, contentContainer);
            Log.e("AttrsPopwindow", "AttrsPopwindow==attrs_pop_layout");
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.attrs_pop_layout2, contentContainer);
            Log.e("AttrsPopwindow", "AttrsPopwindow==attrs_pop_layout2");
        }
        ButterKnife.bind(this, v);
        loadData();
    }

    public void setTip(int ideal) {
        this.ideal = ideal;
    }

    public void clearData() {
        helper.clearData();
    }

    private List<GoodInfoBean.GalleryBean> gallery;

    public void setBanner(List<GoodInfoBean.GalleryBean> gallery) {
        this.gallery = gallery;
    }

    public void loadData() {
        if (!id.equals("")) {
            GoodsDAO.GetGoodAttrs(id, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (AttrsBean) data;
                        multipleAttrs = bean.getMullist();

                        if (bean.getGoods_spec_list() != null && bean.getGoods_spec_list().size() > 0) {
                            hasSpec = true;
                            speclist = bean.getGoods_spec_list();

                            for (int i = 0; i < multipleAttrs.size(); i++) {
                                SkuBean sku = new SkuBean(Double.parseDouble(multipleAttrs.get(i).getPrice()), multipleAttrs.get(i).getCount());
                                map.put(multipleAttrs.get(i).getKey(), sku);
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
                                        for (Map.Entry<String, SkuBean> item : result.getResult().entrySet()) {
                                            String s = speclist.get(j).getItem_id() + "";
                                            String s2 = item.getKey();
//                                            Log.e("iid", speclist.get(j).getItem_id() + "");
//                                            Log.e("key", item.getKey());
                                            if (item.getKey().equals(String.valueOf(speclist.get(j).getItem_id()))) {
                                                speclist.get(j).setCount(item.getValue().getStock());
                                            }
                                        }
                                        itemlist.add(speclist.get(j));
                                    }
                                }
                                allsort.put(sort.get(i), itemlist);
                            }


                            //初始化那个不可点击
                            for (Map.Entry<String, SkuBean> item : result.getResult().entrySet()) {
                                for (Map.Entry<String, List<AttrsBean.GoodsSpecListBean>> item2 : allsort.entrySet()) {
                                    for (int i = 0; i < item2.getValue().size(); i++) {
                                        if (item2.getValue().get(i).getCount() <= 0) {
                                            item2.getValue().get(i).setStatus(2);
                                        }
                                    }
                                }
                                Log.e("resultmap", "key:" + item.getKey() + "price:" + item.getValue().getPrice() + "stock:" + item.getValue().getStock());
                            }

                            //创建相应数量的listitem,加入赛选后的名字
                            manager = new LinearLayoutManager(context);
                            manager.setOrientation(LinearLayoutManager.VERTICAL);
                            typeList.setLayoutManager(manager);
                            typeItemAdapter = new TypeItemAdapter(context, sort, allsort, result);
                            typeItemAdapter.setOnRefreshListener(new TypeItemAdapter.Refresh() {
                                @Override
                                public void refresh() {
                                    //刷新界面
                                    storeCount.setText(result.getResult().get(helper.getStringbuffer().substring(0, helper.getStringbuffer().length() - 1).toString()).getStock() + "套");
                                    price.setText(result.getResult().get(helper.getStringbuffer().substring(0, helper.getStringbuffer().length() - 1).toString()).getPrice() + "");
                                    String substring = helper.getStringbuffer().substring(0, helper.getStringbuffer().length() - 1);
                                    for (int i = 0; i < speclist.size(); i++) {
                                        if (String.valueOf(speclist.get(i).getItem_id()).equals(substring)) {


                                            if (speclist.get(i).getSrc() != null && !speclist.get(i).getSrc().equals("")) {
                                                url = speclist.get(i).getSrc();
                                            } else if(gallery.size() > 0 &&  gallery != null && gallery.get(0).getImage_url()!= null) {
                                                url = gallery.get(0).getImage_url();
                                            }
                                                Log.e("规则图片URL", url);
                                                Glide.with(SevenSecondApplication.getInstance())
                                                        .load(HttpApi.getFullImageUrl(url))
                                                        .asBitmap()
                                                        .centerCrop()
                                                        .transform(new GlideSquareTransform(context))
                                                        .placeholder(R.drawable.default_loading_pic)
                                                        .into(smallBanner);
                                            }
                                    }

                                }
                            });
                            typeList.setAdapter(typeItemAdapter);

                        } else {
                            hasSpec = false;
                        }


                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
//                    Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                }
            });

            GoodsDAO.GetGoodsInfo(id, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        goodbean = (GoodInfoBean) data;
                        good_sn = goodbean.getGoods().getGoods_sn();
                        if (goodbean.getGallery() != null && goodbean.getGallery().size() > 0) {
                            Glide.with(SevenSecondApplication.getInstance())
                                    .load(HttpApi.getFullImageUrl(goodbean.getGoods().getOriginal_img()))
                                    .transform(new GlideSquareTransform(context))
                                    .placeholder(R.drawable.default_loading_pic)
                                    .into(smallBanner);
                        }

                        if (goodbean.getGoods() != null) {
                            goodsContent.setText(goodbean.getGoods().getGoods_name() + "");
                            price.setText(goodbean.getGoods().getShop_price() + "");
                            if (weight <= fw) {
                                postprice = goodbean.getGoods().getAddress().getConfig().getMoney();
                            } else {
                                int size = Math.round((float) (weight - fw) / (float) addw);
                                int cost = size * addm + fm;
                                postprice = String.valueOf(cost);
                            }

                            storeCount.setText(goodbean.getGoods().getStore_count() + "套");
                        }

                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
//                    Toast.makeText(SystemUtil.getContext(), "" + data, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean checkSpec() {
        if (multipleAttrs.size() > 0) {
            if (helper.getMap().size() <= 0 || helper.getMap().size() < sort.size()) {
                Toast.makeText(context, "请选择规格或者选择完整规格", Toast.LENGTH_SHORT).show();
                return false;
            }
            for (Map.Entry<String, SkuBean> item : map.entrySet()) {
                if (item.getKey().equals(TextUtil.tranBack(helper.getMap(), sort)) && item.getValue().getStock() < 0) {
                    Log.e("stock:", item.getValue().getStock() + "key:" + item.getKey());
                    Toast.makeText(context, "库存不足", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    //创建立即购买json数组
    public void createArray() {
        //获取点击样式，获取价格和数量
        try {

            String key = helper.getStringbuffer().toString();
            String Key = helper.getStringbuffer().delete(helper.getStringbuffer().length() - 1, helper.getStringbuffer().length()).toString();
            String keyPrice = "";
            //查找组成新的keyname
            String[] keyid = key.split("_");
            for (int i = 0; i < keyid.length; i++) {
                for (int j = 0; j < speclist.size(); j++) {
                    if (String.valueOf(speclist.get(j).getItem_id()).equals(keyid[i])) {
                        KeyName = KeyName + speclist.get(j).getSpec_name() + ":" + speclist.get(j).getItem() + " ";
                    }
                }
            }
            for (int i = 0; i < multipleAttrs.size(); i++) {
                if (multipleAttrs.get(i).getKey().equals(Key)) {
                    keyPrice = multipleAttrs.get(i).getPrice();
                }
            }


            array = new JSONArray();
            JSONObject obj = new JSONObject();
//            obj.put("user_id",LoginUtils.getUserId());
//            Log.e("userid",LoginUtils.getUserId());
            obj.put("goods_id", id);
//            obj.put("goods_sn",good_sn);
//            obj.put("goods_name",goodbean.getGoods().getGoods_name());
//            obj.put("shipping_price",postprice);
//            obj.put("market_price",goodbean.getGoods().getMarket_price());
            obj.put("goods_price", keyPrice);
            obj.put("goods_num", tvCount.getText().toString());
            if (hasSpec) {
                obj.put("spec_key", Key);
                obj.put("spec_key_name", KeyName);
            } else {
                obj.put("spec_key", "");
                obj.put("spec_key_name", "");
            }
            array.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createarray2() {
        try {
            array = new JSONArray();
            JSONObject obj = new JSONObject();
//            obj.put("user_id",LoginUtils.getUserId());
//            Log.e("userid",LoginUtils.getUserId());
            obj.put("goods_id", id);
//            obj.put("goods_sn",good_sn);
//            obj.put("goods_name",goodbean.getGoods().getGoods_name());
//            obj.put("shipping_price",postprice);
//            obj.put("market_price",goodbean.getGoods().getMarket_price());
            obj.put("goods_num", tvCount.getText().toString());
            obj.put("goods_price", goodbean.getGoods().getShop_price());
            obj.put("spec_key", "");
            obj.put("spec_key_name", "");
            array.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        helper.clear();
        helper.clearData();
    }

    public String getKetName() {
        return KeyName;
    }

    public void clearKeyName() {
        KeyName = "";
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @OnClick({R.id.delete, R.id.Imbuy, R.id.minus, R.id.plus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
                dismiss();
                break;
//            case R.id.addcar:
//                if (checkSpec()) {
//                    Log.e("okbuffer",helper.getStringbuffer()+"");
//                    GoodsDAO.AddCart(id, "1", TextUtil.tranToSpec(helper.getStringbuffer()), new BaseCallBack() {
//                        @Override
//                        public void success(Object data) {
////                        Toast.makeText(SystemUtil.getContext(), "加入成功", Toast.LENGTH_SHORT).show();
////                        Intent toAttrs = new Intent(GoodsDetailActivity.this,GoodsAttrsActivity.class);
////                        startActivity(toAttrs);
//                            new AlertDialog.Builder(context)
//                                    .setMessage("加入成功")
//                                    .setPositiveButton("前往购物车", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            Intent addcar = new Intent(context, TrolleyActivity.class);
//                                            context.startActivity(addcar);
//                                        }
//                                    })
//                                    .setNegativeButton("留在本页面", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//
//                        }
//
//                        @Override
//                        public void failed(int errorCode, Object data) {
//                            Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                break;
            case R.id.Imbuy:
                String arrayspec = "";
                if (hasSpec && !checkSpec()) {
                    return;
                } else if (hasSpec && checkSpec() || !hasSpec) {
                    if (hasSpec) {
                        arrayspec = TextUtil.tranToSpec(helper.getStringbuffer());
                    } else {
                        arrayspec = "";
                    }
                    if (ideal == 1) {
                        if (!LoginUtils.isLogin()) {
                            GoodsDAO.AddCart(id, tvCount.getText().toString(), arrayspec, new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    Toast.makeText(SystemUtil.getContext(), "加入成功", Toast.LENGTH_SHORT).show();
                                    AttrsPopwindow.this.dismiss();
                                    EventBus.getDefault().post(new RefreshEvent(IEventTag.UPNUM));
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            GoodsDAO.AddCartWithToken(id, tvCount.getText().toString(), arrayspec, new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    Toast.makeText(SystemUtil.getContext(), "加入成功", Toast.LENGTH_SHORT).show();
                                    AttrsPopwindow.this.dismiss();
                                    EventBus.getDefault().post(new RefreshEvent(IEventTag.UPNUM));
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else if (ideal == 0) {
                        String str = "";
                        if (helper.getStringbuffer() != null && helper.getStringbuffer().length() > 0) {
                            createArray();
                            str = array.toString();
                        } else {
                            createarray2();
                            str = array.toString();
                        }

                        OrderDAO.BuyNow(str, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                helper.clear();
                                helper.clearData();
                                if (hasSpec) {
                                    typeItemAdapter.clearSelect();
                                }
                                KeyName = "";
                                Intent i = new Intent(context, OrderSureActivity.class);
                                EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
                                i.putExtra("postprice", postprice);
                                i.putExtra("array", array.toString());
                                i.putExtra("frombuy", 1);
                                context.startActivity(i);
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(context, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (ideal == 2) {
                        if (checkSpec()) {
                            createArray();
                            EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRESHTEXT));
                            this.dismiss();
                        }

                    }
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
                if (count2 >= 0) {
                    count2++;
                    tvCount.setText(count2 + "");
                }
                break;
        }
    }
}
