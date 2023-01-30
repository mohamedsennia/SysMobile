package com.example.projetsystme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Show extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    ImageView imageView;
    SensorManager sensorManager;
    Sensor accelrationSensor,magnetSensor;
    float[] lastAccelero=new float[3];
    float[] lastMagnito=new float[3];
    float[] rotationMatrix=new float[9];
    float[] oreontation=new float[3];
    boolean islastAccelero=false;
    boolean isLastMagnito=false;
    long lastUpdatedTime=0;
    float currentDegree=0f;
    float b;
    FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private static final int REQUEST_L_PERMISSION = 101;
    int choice1,choice2;
    private  Point[][] Points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int Choice1,Choice2;
        Points= new Point[][]{{new Point(36.7105391, 3.1803594), new Point(36.7115161, 3.1809035)},
                {new Point(36.7105391, 3.1803594), new Point(36.7115161, 3.1809035)},
                {new Point(36.7104335, 3.1806607), new Point(36.7109261, 3.1812107)},
                {new Point(36.7104335, 3.1806607), new Point(36.7109261, 3.1812107)}};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        choice1=0;
        choice2=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            String value2 = extras.getString("key2");
            //The key argument here must match that used in the other activity
            choice1=Integer.valueOf(value);
            choice2=Integer.valueOf(value2);

            choice1=choice1-1;

            choice2=choice2-1;
        }else {


        }
        imageView=findViewById(R.id.imageView);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        accelrationSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor==accelrationSensor){
            System.arraycopy(sensorEvent.values,0,lastAccelero ,0,sensorEvent.values.length);
            islastAccelero=true;
        }else{
            if(sensorEvent.sensor==magnetSensor){
                System.arraycopy(sensorEvent.values,0,lastMagnito ,0,sensorEvent.values.length);
                isLastMagnito=true;
            }
        }
        if(islastAccelero &&isLastMagnito && System.currentTimeMillis()-lastUpdatedTime>250){
            SensorManager.getRotationMatrix(rotationMatrix,null,lastAccelero,lastMagnito);
            SensorManager.getOrientation(rotationMatrix,oreontation);
            float azimuthInRadian=oreontation[0];
            float azimuth=(float) Math.toDegrees(azimuthInRadian);
            Location destination = new Location("destination");
            double destinationLatitude = Points[choice1][choice2].getLatitdue();
            double destinationLongitude = Points[choice1][choice2].getLongitude();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_L_PERMISSION);
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object


                                destination.setLatitude(destinationLatitude);
                                destination.setLongitude(destinationLongitude);

                                float distance = location.distanceTo(destination);
                                float bearing = location.bearingTo(destination);
                                b=bearing;

                            }else{
                                Toast.makeText(Show.this,"Couldn't find your location please make sure to turn on gps location",Toast.LENGTH_LONG).show();
                            }
                        }
                    });



            imageView.setRotation((-azimuth+b));
            Log.d("azimuth",String.valueOf(azimuth));
            Log.d("b",String.valueOf(b));
            Log.d("angle",String.valueOf(b-azimuth));
            lastUpdatedTime=System.currentTimeMillis();
            int x=(int)azimuth;

            isLastMagnito=false;
            islastAccelero=false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelrationSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magnetSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,accelrationSensor);
        sensorManager.unregisterListener(this,magnetSensor);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, proceed with your functionality
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == REQUEST_L_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission for camera was granted
            } else {
                // Permission for camera denied
            }
        }
    }
}