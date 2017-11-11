package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.UserCenter.setAddressActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/10.
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AddressBean> list;


    public AddressAdapter(Context context) {
        this.context = context;
        list =  new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public AddressAdapter(Context context, ArrayList<AddressBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new addressHolder(inflater.inflate(R.layout.address_item_layout, null));
    }
    public void  setData(List<AddressBean> bean){
        list.addAll(bean);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof addressHolder){
            if(list.size()>0){
                final AddressBean bean = list.get(position);
                ((addressHolder) holder).name.setText(bean.getConsignee());
                ((addressHolder) holder).mobile.setText(bean.getMobile());

                ((addressHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context)
                                .setTitle("删除地址")
                                .setMessage("确认删除地址")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        OrderDAO.delAddress(list.get(position).getAddress_id(), new BaseCallBack() {
                                            @Override
                                            public void success(Object data) {
                                                list.remove(position);
                                                notifyDataSetChanged();
                                                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void failed(int errorCode, Object data) {
                                                Toast.makeText(context, data + "", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();

                    }
                });

                ((addressHolder) holder).add.setText(PositionDao.getInstance().getPreName(bean.getDistrict())+bean.getAddress());
//                List<ProvinceModel> provinceModels = PositionDao.getInstance().queryProvinceList();
//                for (int i = 0; i < provinceModels.size(); i++) {
//                    ProvinceModel provinceModel = provinceModels.get(i);
//                    List<CityModel> cityList = provinceModel.getCityList();
//                    for (int j = 0; j < cityList.size(); j++) {
//                        CityModel cityModel = cityList.get(j);
//                        if (cityModel.getId() == Integer.parseInt(bean.getCity())) {
//                            ((addressHolder) holder).add.setText(provinceModel.getName() + cityModel.getName() + bean.getAddress());
//                        }
//                    }
//                }

                ((addressHolder) holder).edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(context,setAddressActivity.class);
                        i.putExtra("type",1);
                        i.putExtra("addressBean", bean);
                        context.startActivity(i);
                    }
                });

                ((addressHolder) holder).selectAdd.setTextColor(
                        "1".equals(bean.getIs_default())
                                ?ContextCompat.getColor(context, R.color.app_theme)
                                :ContextCompat.getColor(context, R.color.colorBottomBlack));
                ((addressHolder) holder).selectAdd.setText(
                        "1".equals(bean.getIs_default())
                                ?R.string.icon_select
                                :R.string.icon_un_select);
                AddressSelectListener listener = new AddressSelectListener(position);
                ((addressHolder) holder).selectAdd.setOnClickListener(listener);
                ((addressHolder) holder).tv_select.setOnClickListener(listener);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class addressHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.mobile)
        TextView mobile;
        @BindView(R.id.add)
        TextView add;
        @BindView(R.id.select_add)
        IconFontTextView selectAdd;
        @BindView(R.id.edit)
        TextView edit;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.tv_select)
        TextView tv_select;

        public addressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class AddressSelectListener implements View.OnClickListener{

        int position;

        public AddressSelectListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.select_add:
                case R.id.tv_select:
                    final AddressBean bean = list.get(position);
                    OrderDAO.setDefaultAddress(bean.getAddress_id(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            for (int i = 0; i < list.size(); i++) {
                                AddressBean addressBean = list.get(i);
                                addressBean.setIs_default("0");
                            }
                            bean.setIs_default("1");
                            notifyDataSetChanged();
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            Toast.makeText(context, data + "", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
            }
        }
    }

}
