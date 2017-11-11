package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.WebViewActivity;
import com.yidu.sevensecondmall.DAO.HttpUtils;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.GameBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/25.
 */

public class GameAdapter extends RecyclerAdapter<GameBean.ResultBean.GameListBean, GameAdapter.MyHolder> {

    public GameAdapter(Context context) {
        this.context = context;
    }

    Context context;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_game, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final GameBean.ResultBean.GameListBean bean = getItem(position);
        if (bean != null) {
            holder.ivGame.setImageURI(Uri.parse(bean.getSrc()));
            holder.gameName.setText(bean.getTitle());
            holder.messageTv.setText(bean.getDesc());
            holder.game_hot.setText(bean.getHot());
            holder.linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LoginUtils.isLogin()) {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("token", LoginUtils.getToken());
                        params.put("user_id", LoginUtils.getUserId());
                        params.put("gameCode", bean.getGameCode());
                        OkHttpUtil.postSubmitForm(HttpApi.loginGame, params, new OkHttpUtil.OnDownDataListener() {
                            @Override
                            public void onResponse(String url, String json) {
                                Log.e("登录游戏的url", url);
                                Log.e("登录游戏的JSON", json);
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String code = object.getString("code");
                                    if (code.equals("1")) {
                                        JSONObject result = object.getJSONObject("result");
                                        String gameUrl = result.getString("url");
                                        context.startActivity(new Intent(context, WebViewActivity.class).putExtra("url", gameUrl));
                                    } else {
                                        ToastUtil.showToast(context, object.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onFailure(String url, String error) {

                            }
                        });
                    } else {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            });
        }
    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.iv_game)
        SimpleDraweeView ivGame;
        @BindView(R.id.game_name)
        TextView gameName;
        @BindView(R.id.game_hot)
        TextView game_hot;
        @BindView(R.id.linearlayout)
        LinearLayout linearlayout;

        @BindView(R.id.message_tv)
        TextView messageTv;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
