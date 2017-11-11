package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.CommentBean;
import com.yidu.sevensecondmall.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by Administrator on 2017/5/15.
 */
public class EstimateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<CommentBean> list;
    private int position;

    public EstimateAdapter(Context context, List<CommentBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentViewHolder holder = new CommentViewHolder(inflater.inflate(R.layout.item_user_comments_noimg, null),list.get(position).getImg());
        position++;
        return holder ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommentViewHolder){
            switch (list.get(position).getGoods_rank()){
                case 0:
                    ((CommentViewHolder) holder).ratingBar.setRating(0);
                    break;
                case 1:
                    ((CommentViewHolder) holder).ratingBar.setRating(1);
                    break;
                case 2:
                    ((CommentViewHolder) holder).ratingBar.setRating(2);
                    break;
                case 3:
                    ((CommentViewHolder) holder).ratingBar.setRating(3);
                    break;
                case 4:
                    ((CommentViewHolder) holder).ratingBar.setRating(4);
                    break;
                case 5:
                    ((CommentViewHolder) holder).ratingBar.setRating(5);
                    break;
                default:
                    ((CommentViewHolder) holder).ratingBar.setRating(0);
                    break;
            }
            ((CommentViewHolder) holder).tvIme.setText(TimeUtils.timedate(String.valueOf(list.get(position).getAdd_time())));
            ((CommentViewHolder) holder).tvUsername.setText(list.get(position).getUsername());
            if(list.get(position).getImg() == null||list.get(position).getImg().size() ==0){
                ((CommentViewHolder) holder).imgList.setVisibility(View.INVISIBLE);
            }else {
                ((CommentViewHolder) holder).imgList.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.rating_bar)
        ProperRatingBar ratingBar;
        @BindView(R.id.tv_userlinear)
        LinearLayout tvUserlinear;
        @BindView(R.id.tv_ime)
        TextView tvIme;
        @BindView(R.id.tv_goodscomments)
        TextView tvGoodscomments;
        @BindView(R.id.img_list)
        RecyclerView imgList;

        private GridLayoutManager manager;
        private imgAdapter adapter;
        private int position;
        private List<String> imglist;


        public CommentViewHolder(View itemView,List<String>imglist) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.imglist = imglist;
            manager = new GridLayoutManager(context,3);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            adapter = new imgAdapter(context,imglist,true);
            imgList.setLayoutManager(manager);
            imgList.setAdapter(adapter);

        }
    }


}
