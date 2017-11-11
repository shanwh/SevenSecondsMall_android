package com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter;

import android.content.Context;
import android.view.View;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.AssignmentBean;
import com.yidu.sevensecondmall.bean.Distribution.DingCoinRecordBean;
import com.yidu.sevensecondmall.bean.Distribution.ExtensionTeamBean;
import com.yidu.sevensecondmall.bean.Distribution.GiftGoodsArrBean;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.Distribution.MyJurisdictionBean;
import com.yidu.sevensecondmall.bean.Distribution.MyTeamMemberBean;
import com.yidu.sevensecondmall.bean.Distribution.RebateListBean;
import com.yidu.sevensecondmall.bean.Distribution.VipMealBean;
import com.yidu.sevensecondmall.bean.Main.BanListBean;
import com.yidu.sevensecondmall.bean.Main.HomeGoodBean;
import com.yidu.sevensecondmall.bean.Main.HomeGoodListBean;
import com.yidu.sevensecondmall.bean.Main.ImageListBean;
import com.yidu.sevensecondmall.bean.Main.TitleBeanList;
import com.yidu.sevensecondmall.bean.Order.FeeShoppingBean;
import com.yidu.sevensecondmall.bean.Order.PayTypeBean;
import com.yidu.sevensecondmall.bean.Order.ShipDataBean;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.bean.Others.CommentBean;
import com.yidu.sevensecondmall.bean.Others.StoreBrandBean;
import com.yidu.sevensecondmall.bean.Others.StoreClassificationBean;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.bean.User.MessageBean;
import com.yidu.sevensecondmall.bean.User.ServerBean;
import com.yidu.sevensecondmall.bean.Video.PushListBean;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;
import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.holder.TitleBeanListHolder;
import com.yidu.sevensecondmall.views.holder.AssignmentListHolder;
import com.yidu.sevensecondmall.views.holder.BankCardListHolder;
import com.yidu.sevensecondmall.views.holder.CommendListHolder;
import com.yidu.sevensecondmall.views.holder.DingCoinRecordHolder;
import com.yidu.sevensecondmall.views.holder.ExtensionTeamListHolder;
import com.yidu.sevensecondmall.views.holder.FeeShoppingHolder;
import com.yidu.sevensecondmall.views.holder.GifHolder;
import com.yidu.sevensecondmall.views.holder.GoodsListHolder;
import com.yidu.sevensecondmall.views.holder.HomeHeaderHolder;
import com.yidu.sevensecondmall.views.holder.HomeListHolder;
import com.yidu.sevensecondmall.views.holder.ImageListHolder;
import com.yidu.sevensecondmall.views.holder.LogisticsStatusListHolder;
import com.yidu.sevensecondmall.views.holder.MemberListHolder;
import com.yidu.sevensecondmall.views.holder.MyJurisdictionHolder;
import com.yidu.sevensecondmall.views.holder.MyTeamMemberHolder;
import com.yidu.sevensecondmall.views.holder.OrderAllHolder;
import com.yidu.sevensecondmall.views.holder.PasswordHolder;
import com.yidu.sevensecondmall.views.holder.PayTypeHolder;
import com.yidu.sevensecondmall.views.holder.RecordHolder;
import com.yidu.sevensecondmall.views.holder.ServerHolder;
import com.yidu.sevensecondmall.views.holder.StoreBrandHolder;
import com.yidu.sevensecondmall.views.holder.StoreClassHolder;
import com.yidu.sevensecondmall.views.holder.VideoListHolder;
import com.yidu.sevensecondmall.views.holder.VipMealListHolder;
import com.yidu.sevensecondmall.views.holder.logisticsHolder;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class TypeFactoryForList implements TypeFactory{


    private final int TITLEBEANLIST = R.layout.item_titlebean_list;
    private final int TYPE_HOME_HEADER = R.layout.item_home_header;
    private final int TYPE_HOME_LIST = R.layout.item_home_list;
    private final int TYPE_HOME_GOODS_LIST = R.layout.item_home_goods_list;
    private final int TYPE_PAY_TYPE_POP = R.layout.item_pay_type2;
    private final int TYPE_PASSWORD = R.layout.item_password;
    private final int TYPE_ORDER_ITEM = R.layout.item_order;
    private final int TYPE_RECORD = R.layout.item_day_li_record;
    private final int TYPE_MEMBER_ITEM = R.layout.item_new_my_people;
    private final int TYPE_EXTENSION_ITEM = R.layout.item_extension_list;
    private final int TYPE_GIFT = R.layout.item_gift;
    private final int TYPE_MY_MEMBER_ITEM = R.layout.item_new_my_team;
    private final int TYPE_MY_JURISDICTION = R.layout.item_my_jurisdiction;
    private final int TYPE_VIDEO_LIST = R.layout.item_video_list;
    private final int TYPE_CARD_NUMBER = R.layout.item_bank_card;
    private final int TYPE_MESSAGE_SHIP_ITEM = R.layout.logistics_item;
    private final int TYPE_FEE_SHOPPING_ITEM = R.layout.item_fee_shopping_new;
    private final int TYPE_SERVER_ITEM = R.layout.item_sever;
    private final int TYPE_COMMEND = R.layout.item_user_comments_noimg;
    private final int TYPE_VIP_MEAL = R.layout.item_vip_meal;
    private final int TYPE_ASSIGNMENT = R.layout.item_assignment;
    private final int TYPE_DING_COIN_RECORD = R.layout.layout_monryitem;
    private final int TYPE_LOGISTICS_ALL_STATUS =R.layout.item_ship_status;
    private final int TYPE_STORE_CLASSIFICATION =R.layout.item_store_classification;
    private final int TYPE_STORE_BRAND=R.layout.item_store_brand;
    private final int TYPE_PUSH_LIST = R.layout.item_video_list_new;
    private final int IMAGE_LIST = R.layout.item_image;
    private Context context;

    public TypeFactoryForList(){
    }

    public TypeFactoryForList(Context context){
        this.context = context;
    }

    @Override
    public int type(BanListBean bean) {
        return TYPE_HOME_HEADER;
    }

    @Override
    public int type(HomeGoodBean bean) {
        return TYPE_HOME_LIST;
    }

    @Override
    public int type(HomeGoodListBean bean) {
        return TYPE_HOME_GOODS_LIST;
    }

    @Override
    public int type(PayTypeBean one) {
        return TYPE_PAY_TYPE_POP;
    }

    @Override
    public int type(PasswordBean bean) {
        return TYPE_PASSWORD;
    }

    @Override
    public int type(OrderListBean bean) {
        return TYPE_ORDER_ITEM;
    }

    @Override
    public int type(RebateListBean bean) {
        return TYPE_RECORD;
    }

    @Override
    public int type(MemberListBean bean) {
        return TYPE_MEMBER_ITEM;
    }

    @Override
    public int type(ExtensionTeamBean bean) {
        return TYPE_EXTENSION_ITEM;
    }

    @Override
    public int type(GiftGoodsArrBean bean) {
        return TYPE_GIFT;
    }

    @Override
    public int type(MyTeamMemberBean bean) {
        return TYPE_MY_MEMBER_ITEM;
    }

    @Override
    public int type(MyJurisdictionBean bean) {
        return TYPE_MY_JURISDICTION;
    }

    @Override
    public int type(VideoListBean bean) {
        return TYPE_VIDEO_LIST;
    }

    @Override
    public int type(BankCardBean bean) {
        return TYPE_CARD_NUMBER;
    }

    @Override
    public int type(MessageBean bean) {
        return TYPE_MESSAGE_SHIP_ITEM;
    }

    @Override
    public int type(FeeShoppingBean bean) {
        return TYPE_FEE_SHOPPING_ITEM;
    }

    @Override
    public int type(ServerBean bean) {
        return TYPE_SERVER_ITEM;
    }

    @Override
    public int type(CommentBean bean) {
        return TYPE_COMMEND;
    }

    @Override
    public int type(VipMealBean bean) {
        return TYPE_VIP_MEAL;
    }

    @Override
    public int type(AssignmentBean bean) {
        return TYPE_ASSIGNMENT;
    }

    @Override
    public int type(DingCoinRecordBean bean) {
        return TYPE_DING_COIN_RECORD;
    }

    @Override
    public int type(PushListBean bean) {
        return TYPE_PUSH_LIST;
    }

    @Override
    public int type(ShipDataBean bean) {return TYPE_LOGISTICS_ALL_STATUS;}


    @Override
    public int type(StoreClassificationBean bean) {return TYPE_STORE_CLASSIFICATION;}

    @Override
    public int type(StoreBrandBean bean) {return TYPE_STORE_BRAND;}

    @Override
    public int type(ImageListBean bean) {
        return IMAGE_LIST;
    }

    @Override
    public int type(TitleBeanList bean) {
        return TITLEBEANLIST;
    }

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {

        if (TYPE_PAY_TYPE_POP == type){
            return new PayTypeHolder(itemView, context);
        }else if (TYPE_PASSWORD == type){
            return new PasswordHolder(itemView);
        } else if (TYPE_ORDER_ITEM == type){
            return new OrderAllHolder(itemView, context);
        }else if (TYPE_RECORD == type){
            return new RecordHolder(itemView);
        }else if (TYPE_MEMBER_ITEM == type){
            return new MemberListHolder(itemView, context);
        }else if (TYPE_EXTENSION_ITEM == type){
            return new ExtensionTeamListHolder(itemView, context);
        }else if (TYPE_GIFT == type){
            return new GifHolder(itemView, context);
        }else if (TYPE_MY_MEMBER_ITEM == type){
            return new MyTeamMemberHolder(itemView, context);
        }else if (TYPE_MY_JURISDICTION == type){
            return new MyJurisdictionHolder(itemView);
        }else if (TYPE_VIDEO_LIST == type){
            return new VideoListHolder(itemView, context);
        }else if (TYPE_CARD_NUMBER == type){
            return new BankCardListHolder(itemView, context);
        }else if (TYPE_MESSAGE_SHIP_ITEM == type){
            return new logisticsHolder(itemView, context);
        }else if (TYPE_FEE_SHOPPING_ITEM == type){
            return new FeeShoppingHolder(itemView, context);
        }else if (TYPE_HOME_HEADER == type){
            return new HomeHeaderHolder(itemView, context);
        }else if (TYPE_HOME_LIST == type){
            return new HomeListHolder(itemView, context);
        }else if (TYPE_HOME_GOODS_LIST == type){
            return new GoodsListHolder(itemView, context);
        }else if (TYPE_SERVER_ITEM == type){
            return new ServerHolder(itemView, context);
        }else if (TYPE_COMMEND == type){
            return new CommendListHolder(itemView, context);
        }else if (TYPE_VIP_MEAL == type){
            return new VipMealListHolder(itemView, context);
        }else if (TYPE_ASSIGNMENT == type){
            return new AssignmentListHolder(itemView, context);
        }else if (TYPE_DING_COIN_RECORD == type){
            return new DingCoinRecordHolder(itemView, context);
        } else if (TYPE_LOGISTICS_ALL_STATUS == type) {
            return new LogisticsStatusListHolder(itemView, context);
        }  else if (TYPE_STORE_BRAND == type) {
            return new StoreBrandHolder(itemView, context);
        } else if (TYPE_STORE_CLASSIFICATION == type) {
            return new StoreClassHolder(itemView, context);
        }else if(TYPE_PUSH_LIST == type){
            return new VideoListHolder(itemView,context);
        }else if(IMAGE_LIST==type){
            return  new ImageListHolder(itemView,context);
        }else if(TITLEBEANLIST==type){
            return  new TitleBeanListHolder(itemView,context);
        }
        return null;
    }
}