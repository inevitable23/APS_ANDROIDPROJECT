package resolution.ex6.vr.aps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager manager;
    Intent aedintent;
    //현재 위치 값
    static double  longitude = -1;
    static double latitude = -1;
    //
    /***************레이아웃관련 변수 ***********/
    LinearLayout find_patient_Button;
    LinearLayout find_AED_Button;
    LinearLayout emergnecy_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        layoutinit();
        find_AED_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aedintent = new Intent(getApplicationContext(),AEDActivity.class);
                if(longitude != -1 && latitude != -1) {
                    aedintent.putExtra("longitude", longitude);
                    aedintent.putExtra("lat", latitude);
                    startActivity(aedintent);
                }else{
                    startLocationService();
                    Toast.makeText(getApplicationContext(), "현재 위치를 찾는중...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        find_patient_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PatientActivity.class);
                startActivity(i);
            }
        });
        emergnecy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmergencylectureActivity.class);
                startActivity(i);
            }
        });

    }
    /***********layout inflater **********/
    public void layoutinit(){
        find_patient_Button = (LinearLayout)findViewById(R.id.find_patient_Button);
        find_AED_Button = (LinearLayout)findViewById(R.id.find_aed_button);
        emergnecy_button = (LinearLayout) findViewById(R.id.emergency_button);
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
            stopLocationService();
            if(longitude != -1 && latitude != -1) {
                aedintent.putExtra("longitude", longitude);
                aedintent.putExtra("lat", latitude);
                startActivity(aedintent);
            }
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
}