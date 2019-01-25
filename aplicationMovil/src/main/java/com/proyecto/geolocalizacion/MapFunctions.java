package com.proyecto.geolocalizacion;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.proyect.movil.R;

import java.text.DecimalFormat;

import static android.content.Context.LOCATION_SERVICE;

public class MapFunctions {

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    public static Location getCurrentLocation(Activity activity){
        try{
            LocationManager mLocationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0, mLocationListener);
            if (!checkGPSIsEnabled(activity)) {
                showInfoAlert(activity);
            } else {
                Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                return location;
            }

		/*if (location == null) {
			location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} */
        }catch (Exception e){
            Toast.makeText(activity, "getCurrentLocation() > " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

        return null;
    }

    private static final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //createOrUpdateMarkerByLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static boolean checkGPSIsEnabled(Activity activity) {
        try {
            int locationMode = 0;
            String locationProviders;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                try {
                    locationMode = Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE);

                } catch (Settings.SettingNotFoundException e) {
                    Toast.makeText(activity, "checkGPSIsEnabled() SDKV>19 > " + e.getMessage() ,
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                return locationMode != Settings.Secure.LOCATION_MODE_OFF;

            }else{
                locationProviders = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                return !TextUtils.isEmpty(locationProviders);
            }


        } catch (Exception e) {
            Toast.makeText(activity, "checkGPSIsEnabled() > " + e.getMessage() , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void showInfoAlert(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.GEO_NOSIGNAL_TITLE))
                .setMessage(activity.getResources().getString(R.string.GEO_NOSIGNAL_MESSAGE))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //El GPS no está activado
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

}
