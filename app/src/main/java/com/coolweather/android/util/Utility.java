package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {//解析JSON数据工具类

    //解析JSON格式的地区数据并组装成对应级别地区对象，通过映射存入对应级别地区数据表
    public static boolean handleProvinceResponse(String response){//解析省级地区数据并存入省级地区数据表
        if (!TextUtils.isEmpty(response)){
            try
            {
                JSONArray allProvinces=new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();//将省对象映射成省级数据表记录并存储
                }
                return true;//返回解析结果
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response,int provinceId){//解析市级地区数据并存入市级地区数据表
        if (!TextUtils.isEmpty(response)){
            try
            {
                JSONArray allCities=new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();//将市对象映射成市级数据表记录并存储
                }
                return true;//返回解析结果
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return  false;
    }

    public static boolean handleCountyResponse(String response,int cityId){//解析县级地区数据并存入县级地区数据表
        if (!TextUtils.isEmpty(response)){
            try
            {
                JSONArray allCounties=new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();//将县对象映射成县级数据表记录并存储
                }
                return true;//返回解析结果
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }
}
