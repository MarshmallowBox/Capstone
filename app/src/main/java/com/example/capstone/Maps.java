package com.example.capstone;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.MarkerIcons;
import com.naver.maps.map.widget.LocationButtonView;

public class Maps extends Fragment implements OnMapReadyCallback// Fragment 클래스를 상속받아야한다
{
    private MapView mapView;
    private LocationButtonView locationButtonView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_maps, container, false);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
//내위치 버튼


        locationButtonView = view.findViewById(R.id.locationbuttonview);
        // 내위치 찾기 위한 source
        locationButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "aaaa", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        /**
         이런식으로 마커관리할까?
         List<Marker> Markers = ...

         for (Marker marker : Markers) {
         ...
         marker.setMap(naverMap);
         }**/

        //인포뷰생성
        final InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindowAdapter(getActivity()));//클래스 커스텀한거 가져오기
        infoWindow.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("상호명");
                builder.setMessage("주소\n카테고리\n전화번호");
                builder.show();
                return true;
            }
        });
//        infoWindow.setOnClickListener(overlay -> {
//            infoWindow.close();
//            return true;
//        });


        //마커생성
        final Marker marker = new Marker();
        marker.setPosition(new LatLng(37.56436, 126.97499));//위경도
//        marker.setIcon(OverlayImage.fromResource(R.drawable.appicon64)); //아이콘 직접 지정
        marker.setIcon(MarkerIcons.YELLOW);//기본제공 마커
        //특정 줌 레벨에서만 캡션이 나타나도록 지정
        marker.setCaptionMinZoom(12);
//        marker.setCaptionMaxZoom(16);
        //마커 크기지정 아마 3:4비율인듯
        marker.setWidth(90);
        marker.setHeight(120);
        marker.setCaptionText("상호명"); //메인캡션
        marker.setTag("야호!!!");//인포뷰에 전달할 태그값
        marker.setSubCaptionText("카테고리"); //서브캡션
        marker.setSubCaptionColor(Color.BLUE); //서브캡션 색상
        marker.setSubCaptionTextSize(10); //서브캡션 크기
        marker.setHideCollidedCaptions(true);//마커곂칠때 캡션숨기기

        marker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                infoWindow.open(marker);
                return true;
            }
        });

        marker.setMap(naverMap); //지도에 추가, null이면 안보임

        //https://navermaps.github.io/android-map-sdk/reference/com/naver/maps/map/NaverMapOptions.html
        //컨텐츠 패딩
        naverMap.setContentPadding(0, 35 * 3, 0, 0);

        //지도클릭시 인포뷰 닫기
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                infoWindow.close();
            }
        });
    }


    private static class InfoWindowAdapter extends InfoWindow.ViewAdapter {
        @NonNull
        private final Context context;
        private View rootView;
        private ImageView icon;
        private TextView name;
        private TextView address;


        private InfoWindowAdapter(@NonNull Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(@NonNull InfoWindow infoWindow) {
            if (rootView == null) {
                rootView = View.inflate(context, R.layout.view_custom_info_window, null);
                icon = rootView.findViewById(R.id.view_custom_info_window_image);
                name = rootView.findViewById(R.id.view_custom_info_window_title);
                address = rootView.findViewById(R.id.view_custom_info_window_desc);
            }

            if (infoWindow.getMarker() != null) {
                icon.setImageResource(R.drawable.appicon64);
                name.setText("tag:" + (String) infoWindow.getMarker().getTag());
                address.setText("tag:" + (String) infoWindow.getMarker().getTag());
            }

            return rootView;
        }
    }
}


