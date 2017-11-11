package com.yidu.sevensecondmall.utils;

import android.text.TextUtils;

import com.yidu.sevensecondmall.bean.Order.SkuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/28.
 */
public class Sku {
    public static Map<String, SkuBean> skuCollection(Map<String, SkuBean> initData) {
        //用户返回数据
        HashMap<String, SkuBean> result = new HashMap<>();
        // 遍历所有库存
        for (String subKey : initData.keySet()) {
            SkuBean skuModel = initData.get(subKey);
            //根据；拆分key的组合
            String[] skuKeyAttrs = subKey.split("_");

            //获取所有的组合
            ArrayList<ArrayList<String>> combArr = combInArray(skuKeyAttrs);

            // 对应所有组合添加到结果集里面
            for (int i = 0; i < combArr.size(); i++) {
                add2SKUResult(result, combArr.get(i), skuModel);
            }

            // 将原始的库存组合也添加进入结果集里面
            String key = TextUtils.join("_", skuKeyAttrs);
            result.put(key, skuModel);
        }
        return result;
    }

    /**
     * 获取所有的组合放到ArrayList里面
     *
     * @param skuKeyAttrs 单个key被； 拆分的数组
     * @return ArrayList
     */
    private static ArrayList<ArrayList<String>> combInArray(String[] skuKeyAttrs) {
        if (skuKeyAttrs == null || skuKeyAttrs.length <= 0)
            return null;
        int len = skuKeyAttrs.length;
        ArrayList<ArrayList<String>> aResult = new ArrayList<>();
        for (int n = 1; n < len; n++) {
            ArrayList<Integer[]> aaFlags = getCombFlags(len, n);
            for (int i = 0; i < aaFlags.size(); i++) {
                Integer[] aFlag = aaFlags.get(i);
                ArrayList<String> aComb = new ArrayList<>();
                for (int j = 0; j < aFlag.length; j++) {
                    if (aFlag[j] == 1) {
                        aComb.add(skuKeyAttrs[j]);
                    }
                }
                aResult.add(aComb);
            }
        }
        return aResult;
    }

    /**
     * 算法拆分组合 用1和0 的移位去做控制
     * （这块需要你打印才能看的出来）
     *
     * @param len
     * @param n
     * @return
     */
    private static ArrayList<Integer[]> getCombFlags(int len, int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        ArrayList<Integer[]> aResult = new ArrayList<>();
        Integer[] aFlag = new Integer[len];
        boolean bNext = true;
        int iCnt1 = 0;
        //初始化
        for (int i = 0; i < len; i++) {
            aFlag[i] = i < n ? 1 : 0;
        }
        aResult.add(aFlag.clone());
        while (bNext) {
            iCnt1 = 0;
            for (int i = 0; i < len - 1; i++) {
                if (aFlag[i] == 1 && aFlag[i + 1] == 0) {
                    for (int j = 0; j < i; j++) {
                        aFlag[j] = j < iCnt1 ? 1 : 0;
                    }
                    aFlag[i] = 0;
                    aFlag[i + 1] = 1;
                    Integer[] aTmp = aFlag.clone();
                    aResult.add(aTmp);
                    if (!TextUtils.join("", aTmp).substring(len - n).contains("0")) {
                        bNext = false;
                    }
                    break;
                }
                if (aFlag[i] == 1) {
                    iCnt1++;
                }
            }
        }
        return aResult;
    }

    /**
     * 添加到数据集合
     *
     * @param result
     * @param newKeyList
     * @param skuModel
     */
    private static void add2SKUResult(HashMap<String, SkuBean> result, ArrayList<String> newKeyList, SkuBean skuModel) {
        String key = TextUtils.join("_", newKeyList);
        if (result.keySet().contains(key)) {
            result.get(key).setStock(result.get(key).getStock() + skuModel.getStock());
            result.get(key).setPrice(skuModel.getPrice());
        } else {
            result.put(key, new SkuBean(skuModel.getPrice(), skuModel.getStock()));
        }
    }
}
