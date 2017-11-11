package com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter;

import android.view.View;

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

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public interface TypeFactory {
    int type(BanListBean bean);
    int type(HomeGoodBean bean);
    int type(HomeGoodListBean bean);
    int type(PayTypeBean one);
    int type(PasswordBean bean);
    int type(OrderListBean bean);
    int type(RebateListBean bean);
    int type(MemberListBean bean);
    int type(ExtensionTeamBean bean);
    int type(GiftGoodsArrBean bean);
    int type(MyTeamMemberBean bean);
    int type(MyJurisdictionBean bean);
    int type(VideoListBean bean);
    int type(BankCardBean bean);
    int type(MessageBean bean);
    int type(FeeShoppingBean bean);
    int type(ServerBean bean);
    int type(CommentBean bean);
    int type(VipMealBean bean);
    int type(AssignmentBean bean);
    int type(DingCoinRecordBean bean);
    int type(PushListBean bean);
    int type(ShipDataBean bean);
    int type(StoreClassificationBean bean);
    int type(StoreBrandBean bean);
    int type(ImageListBean bean);
    int type(TitleBeanList bean);
    BaseViewHolder createViewHolder(int type, View itemView);
}
