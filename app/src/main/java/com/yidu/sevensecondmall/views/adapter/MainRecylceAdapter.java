package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.BannerBean;
import com.yidu.sevensecondmall.bean.Main.FootBean;
import com.yidu.sevensecondmall.bean.Main.ShopListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/3/17.
 */
public class MainRecylceAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, BannerBean, ShopListBean.GoodsListBean, FootBean> {

    private int size;
    private Context context;
    private LayoutInflater inflater;
    private List<ShopListBean.GoodsListBean> list = new ArrayList<>();
    private List<BannerBean.BannerlistBean> bannerlist = new ArrayList<>();
    private final static int HEADITEM = 0;
    private final static int CONTENTITEM = 1;
    private int currentpage = 0;
    private TextView r_txt;
    private TextView h_txt;
    private TextView n_txt;
    private TextView p_txt;

    public MainRecylceAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(inflater.inflate(R.layout.layout_main_listitem, null));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyleview_head, null);
        return new BannerViewHolder(inflate);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        Log.e("mainRecycleAdapter","is run");
        BannerBean bean = getHeader();
        bannerViewHolder.bannerMainFlip.setData(bean.getBannerlist(), null);
        bannerViewHolder.banneradpter = new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(context)
                        .load(((BannerBean.BannerlistBean) model).getAd_code() + "")
                        .into((ImageView) view);
            }
        };
        bannerViewHolder.bannerMainFlip.setAdapter(bannerViewHolder.banneradpter);
    }



    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder) {
            MainViewHolder viewHolder = (MainViewHolder) holder;
            final ShopListBean.GoodsListBean bean = getItem(position);
            Glide.with(context)
                    .load(bean.getOriginal_img() + "")
                    .placeholder(R.drawable.default_loading_pic)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((MainViewHolder) holder).goodsPic);
            ((MainViewHolder) holder).inText.setText(bean.getGoods_name());
            ((MainViewHolder) holder).price.setText("￥" + bean.getShop_price());
            ((MainViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, GoodsDetailActivity.class);
                    i.putExtra("id", bean.getGoods_id());
                    context.startActivity(i);
                }
            });
        }
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.in_text)
        TextView inText;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.item)
        LinearLayout item;
        @BindView(R.id.goods_pic)
        ImageView goodsPic;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner_main_flip)
        BGABanner bannerMainFlip;
        //        @BindView(R.id.recommend)
//        TextView recommend;
//        @BindView(R.id.l_recommend)
//        RelativeLayout lRecommend;
//        @BindView(R.id.hot)
//        TextView hot;
//        @BindView(R.id.l_hot)
//        RelativeLayout lHot;
//        @BindView(R.id.news)
//        TextView news;
//        @BindView(R.id.l_news)
//        RelativeLayout lNews;
//        @BindView(R.id.price)
//        TextView price;
//        @BindView(R.id.l_price)
//        RelativeLayout lPrice;
        private BGABanner.Adapter banneradpter;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

//        @OnClick({R.id.l_recommend, R.id.l_hot, R.id.l_news, R.id.l_price})
//        public void onClick(View view) {
//            Intent i;
//            switch (view.getId()) {
//                case R.id.l_recommend:
////                EventBus.getDefault().post(new SelectEvent(IEventTag.RECOMMEND));
//                    currentpage = 0;
//                    break;
//                case R.id.l_hot:
////                EventBus.getDefault().post(new SelectEvent(IEventTag.HOT));
//                    currentpage = 1;
//                    break;
//                case R.id.l_news:
////                EventBus.getDefault().post(new SelectEvent(IEventTag.NEWS));
//                    currentpage = 2;
//                    break;
//                case R.id.l_price:
////                EventBus.getDefault().post(new SelectEvent(IEventTag.PRICE));
//                    currentpage = 3;
//                    break;
//            }
//            i = new Intent(context, SearchActivity.class);
//            context.startActivity(i);
//        }
    }

}
