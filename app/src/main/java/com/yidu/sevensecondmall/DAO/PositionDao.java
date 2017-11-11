package com.yidu.sevensecondmall.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.DistrictModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.utils.AssetsDatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class PositionDao {
    private static PositionDao instance = null;
    private final AssetsDatabaseManager manager;
    public List<ProvinceModel> list=new ArrayList<>();
    private PositionDao() {
        manager = AssetsDatabaseManager.getManager();
    }
    public static PositionDao getInstance() {
        synchronized (PositionDao.class) {
            if (instance == null) {
                instance = new PositionDao();
            }
        }
        return instance;
    }



    public List<ProvinceModel> queryProvinceList() {

        SQLiteDatabase db = manager.getDatabase("area.db");
        List<ProvinceModel> provinceList = new ArrayList<>();
        try {
            String sql = "select * from mc_area where pid==";
            Cursor cursor = db.rawQuery(sql + 0, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ProvinceModel provinceModel = new ProvinceModel();
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    provinceModel.setId(id);
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    provinceModel.setName(name);
                    List<CityModel> cityList = new ArrayList<>();
                    Cursor cursorcity = db.rawQuery(sql + id, null);
                    if (cursorcity != null) {
                        while (cursorcity.moveToNext()) {
                            CityModel cityModel = new CityModel();
                            int id1 = cursorcity.getInt(cursorcity.getColumnIndex("id"));
                            cityModel.setId(id1);
                            String name1 = cursorcity.getString(cursorcity.getColumnIndex("name"));
                            cityModel.setName(name1);
                            Cursor cursordis = db.rawQuery(sql + id1, null);
                            List<DistrictModel> distList = new ArrayList<>();
                            if (cursordis != null) {
                                while (cursordis.moveToNext()) {
                                    DistrictModel districtModel = new DistrictModel();
                                    int id2 = cursordis.getInt(cursordis.getColumnIndex("id"));
                                    districtModel.setId(id2);
                                    String name2 = cursordis.getString(cursordis.getColumnIndex("name"));
                                    districtModel.setName(name2);
                                    distList.add(districtModel);
                                }
                                cityModel.setDistrictList(distList);
                                cursordis.close();
                            }
                            cityList.add(cityModel);
                        }
                        provinceModel.setCityList(cityList);
                        cursorcity.close();
                    }
                    provinceList.add(provinceModel);
                }
                cursor.close();
            }
            list = provinceList;
            manager.closeDatabase("area.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    public String getPreName(String id){
        String allname = "";
        List<ProvinceModel> provinceModels = PositionDao.getInstance().queryProvinceList();
        for (int i = 0; i < provinceModels.size(); i++) {
            ProvinceModel provinceModel = provinceModels.get(i);
            List<CityModel> cityList = provinceModel.getCityList();
            for (int j = 0; j < cityList.size(); j++) {
                CityModel cityModel = cityList.get(j);
                List<DistrictModel> districtModels = cityModel.getDistrictList();
                for(int k = 0;k<districtModels.size();k++){
                    if(districtModels.get(k).getId() == Integer.parseInt(id)){
                        DistrictModel districtModel = districtModels.get(k);
                        allname = provinceModel.getName() + " "+cityModel.getName()+" "+districtModel.getName();
                        break;
                    }
                }
            }
        }
        return allname;
    }




    public void closeDb()
    {
        manager.closeDatabase("area.db");
    }
}
