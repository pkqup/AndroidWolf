package com.chunlangjiu.app.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.chunlangjiu.app.user.bean.LocalAreaBean;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;
import com.zaaach.citypicker.model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreatedbBy: liucun on 2018/8/12.
 * @Describe: 省市区的工具类
 */
public class AreaUtils {

    public static List<LocalAreaBean.ProvinceData> getJson(Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        List<LocalAreaBean.ProvinceData> lists = new ArrayList<>();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("area")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            LocalAreaBean localAreaBean = new Gson().fromJson(stringBuilder.toString(), LocalAreaBean.class);

            lists = localAreaBean.getRegion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    public static List<City> getCityList(Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        List<City> lists = new ArrayList<>();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("area")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            LocalAreaBean localAreaBean = new Gson().fromJson(stringBuilder.toString(), LocalAreaBean.class);
            List<LocalAreaBean.ProvinceData> provinceDataList = localAreaBean.getRegion();
            for (int i = 0; i < provinceDataList.size(); i++) {
                List<LocalAreaBean.ProvinceData.City> cityList = provinceDataList.get(i).getChildren();
                for (int j = 0; j < cityList.size(); j++) {
                    lists.add(new City(cityList.get(j).getValue(), "",
                            Pinyin.toPinyin(cityList.get(j).getValue(), "").toLowerCase(), cityList.get(j).getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }
}
