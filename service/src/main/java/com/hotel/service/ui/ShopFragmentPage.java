package com.hotel.service.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragment;

/**
 * i家
 *
 * @author aoxiang
 */
public class ShopFragmentPage extends BaseFragment implements OnGetGeoCoderResultListener {

    private String TAG = ShopFragmentPage.class.getCanonicalName();

    private View view;
    private GeoCoder mSearch = null;
    private LocationClient mLocClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ihome_fragment_view, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tabName = (TextView) view.findViewById(R.id.activity_name);
        tabName.setText("i家");

        view.findViewById(R.id.return_back).setVisibility(View.GONE);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(new MyLocationListenner());
        mLocClient.requestLocation();

        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    //#############################实现百度地图定位#######################################
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.i(TAG, "百度地图定位:");

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());

            Log.i(TAG, "Latitude:" + location.getLatitude() + " Longitude:" + location.getLongitude());

            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));


        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            toastDialog("抱歉，未能找到结果", -1);
        }
        String strInfo = String.format("纬度：%f 经度：%f",
                result.getLocation().latitude, result.getLocation().longitude);

        toastDialog(strInfo, -1);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            toastDialog("抱歉，未能找到结果", -1);
        }
        toastDialog(result.getAddress(), -1);

    }
}
