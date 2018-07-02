package com.chunlangjiu.app.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.pkqup.commonlibrary.util.AppUtils;

/**
 * @CreatedbBy: liucun on 2018/6/20
 * @Describe: 定位工具类
 */
public class LocationUtils {

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    public interface LocationCallBack {
        void locationSuccess(AMapLocation aMapLocation);

        void locationFail();
    }

    public void startLocation(final LocationCallBack locationCallBack) {
        //初始化定位
        mLocationClient = new AMapLocationClient(AppUtils.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                //异步获取定位结果
                if (null == aMapLocation || aMapLocation.getErrorCode() != 0) {
                    //定位失败
                    locationCallBack.locationFail();
                } else {
                    //定位成功
                    locationCallBack.locationSuccess(aMapLocation);
                }

                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }


}
