package resolution.ex6.vr.aps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.google.android.gms.analytics.internal.zzy.t;

public class AEDActivity extends NMapActivity
        implements OnMapStateChangeListener, OnCalloutOverlayListener{

    // API-KEY
    public static final String API_KEY = "mo8MMpoxRZggZYZ9JdKy";
    // 네이버 맵 객체
    NMapView mMapView = null;
    // 맵 컨트롤러
    NMapController mMapController = null;
    // 맵을 추가할 레이아웃
    LinearLayout MapContainer;

    // 오버레이의 리소스를 제공하기 위한 객체
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    // 오버레이 관리자
    NMapOverlayManager mOverlayManager;
    LocationManager manager;
    //현재 위치 값
    double longitude = -1;
    double latitude = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aed);
        startLocationService();
        /******************* 지도 초기화 시작 ********************/
        // 네이버 지도를 넣기 위한 LinearLayout 컴포넌트
        MapContainer = (LinearLayout) findViewById(R.id.MapContainer);
        // 네이버 지도 객체 생성
        mMapView = new NMapView(this);


        // 네이버 지도 객체에 APIKEY 지정
        mMapView.setApiKey(API_KEY);

        // 생성된 네이버 지도 객체를 LinearLayout에 추가시킨다.
        MapContainer.addView(mMapView);

        // 지도를 터치할 수 있도록 옵션 활성화
        mMapView.setClickable(true);

        // 확대/축소를 위한 줌 컨트롤러 표시 옵션 활성화
        mMapView.setBuiltInZoomControls(true, null);




        /******************* 지도 초기화 끝 ********************/




        /**
        /******************* 오버레이 관련 코드 시작 ********************/
        // 오버레이 리소스 관리객체 할당
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // 오버레이 관리자 추가
        mOverlayManager = new NMapOverlayManager(this, mMapView,
                mMapViewerResourceProvider);

    }

    /**
     * 지도가 초기화된 후 호출된다.
     * 정상적으로 초기화되면 errorInfo 객체는 null이 전달되며,
     * 초기화 실패 시 errorInfo객체에 에러 원인이 전달된다
     */
    @Override
    public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
        if (errorInfo == null) { // success
            //mMapController.setMapCenter(
            //	new NGeoPoint(126.978371, 37.5666091), 11);
        } else { // fail
            android.util.Log.e("NMAP", "onMapInitHandler: error="
                    + errorInfo.toString());
        }
    }

    /**
     * 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다.
     */
    @Override
    public void onZoomLevelChange(NMapView mapview, int level) {}

    /**
     * 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다.
     */
    @Override
    public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}

    /**
     * 지도 애니메이션 상태 변경 시 호출된다.
     * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
     * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
     */
    @Override
    public void onAnimationStateChange(NMapView arg0, int animType,
                                       int animState) {}

    @Override
    public void onMapCenterChangeFine(NMapView arg0) {}

    /** 오버레이가 클릭되었을 때의 이벤트 */
    @Override
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0,
                                                     NMapOverlayItem arg1, Rect arg2) {
        Toast.makeText(this, arg1.getTitle(),
                Toast.LENGTH_SHORT).show();
        return null;
    }




    //현재 위치를 찾는것.
    private void startLocationService() {

        // get manager instance
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 1000;
        float minDistance = 0;
        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "체크 퍼미션", Toast.LENGTH_SHORT).show();
            return  ;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);

    }
    private void stopLocationService(){
        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "체크 퍼미션", Toast.LENGTH_SHORT).show();
            return  ;
        }
        manager.removeUpdates(mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            showMyLocation(latitude, longitude);
            stopLocationService();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private void showMyLocation(double latitude, double longitude){
        NMapViewerResourceProvider nMapViewerResourceProvider = null;
        NMapOverlayManager nMapOverlayManager;

        nMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        nMapOverlayManager = new NMapOverlayManager(this, mMapView, nMapViewerResourceProvider);

        NGeoPoint myPoint = new NGeoPoint(longitude, latitude);

        // 오버레이들을 관리하기 위한 id값 생성
        int markerId = NMapPOIflagType.PIN;

        // 표시할 위치 데이터를 지정한다. 마지막 인자가 오버레이를 인식하기 위한 id값
        NMapPOIdata poiData = new NMapPOIdata(1, nMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(myPoint, "현재 위치", markerId, 0);
        poiData.endPOIdata();

        // 위치 데이터를 사용하여 오버레이 생성
        NMapPOIdataOverlay poiDataOverlay
                = nMapOverlayManager.createPOIdataOverlay(poiData, null);

        // id값이 0으로 지정된 모든 오버레이가 표시되고 있는 위치로
        // 지도의 중심과 ZOOM을 재설정
        poiDataOverlay.showAllPOIdata(0);

        NMapController controller = mMapView.getMapController();
        controller.animateTo(myPoint);
    }
}