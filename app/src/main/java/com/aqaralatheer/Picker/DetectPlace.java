package com.aqaralatheer.Picker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.aqaralatheer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetectPlace extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    LatLng pos2;
    Marker draggedMarker;
    int advType1;
    int advType2;
    int advType12;
    String advType3;
    String advType4;
    String advType5;
    String advType6;
    String advType7;
    int advType8;
    String advType9;
    String advType10;
    String advType11,advType13,advType14;
    String Images;
    String id;
    Double lat, lang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_place);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle(Html.fromHtml("<strong>عقـــــاري </strong>"));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.currentmap);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();

        advType1 = intent.getIntExtra("advType1", 0);
        advType2 = intent.getIntExtra("advType2", 0);
        advType3 = intent.getStringExtra("advType3");
        advType4 = intent.getStringExtra("advType4");
        advType5 = intent.getStringExtra("advType5");
        advType6 = intent.getStringExtra("advType6");
        advType7 = intent.getStringExtra("advType7");
        advType8 = intent.getIntExtra("advType8", 0);
        advType9 = intent.getStringExtra("advType9");
        advType10 = intent.getStringExtra("advType10");
        advType11 = intent.getStringExtra("advType11");
        advType13 = intent.getStringExtra("advType13");
        advType14 = intent.getStringExtra("advType14");
        advType12 = intent.getIntExtra("add", 0);
        if (advType12 == 2) {
            Images = intent.getStringExtra("Img");
            id = intent.getStringExtra("advertid");
            lat = intent.getDoubleExtra("lat",0.0);
            lang = intent.getDoubleExtra("lang",0.0);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        Toast.makeText(this, "اضغط علي المؤشر واسحبه الي مكان المبني واضغط عليه ", Toast.LENGTH_LONG).show();

        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);

       /* if (lat != null && lang !=null) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(30.091921, 31.246332))
                    .title("country")
                    .snippet("4 E. 28TH Street From $15 /per night")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker4)).draggable(true));

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(lat, lang))
                    .zoom(8)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }
*/
        if (lat != null && lang !=null) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lang))
                    .title("country")
                    .snippet("4 E. 28TH Street From $15 /per night")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker4)).draggable(true));

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(lat, lang))
                    .zoom(8)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }else{
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(30.091921, 31.246332))
                    .title("country")
                    .snippet("4 E. 28TH Street From $15 /per night")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker4)).draggable(true));

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(30.091921, 31.246332))
                    .zoom(8)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(DetectPlace.this, Add_advert.class);

                intent.putExtra("lat", pos2.latitude);
                intent.putExtra("lang", pos2.longitude);

                intent.putExtra("advType1", advType1);
                intent.putExtra("advType2", advType2);
                intent.putExtra("advType3", advType3);
                intent.putExtra("advType4", advType4);
                intent.putExtra("advType5", advType5);
                intent.putExtra("advType6", advType6);
                intent.putExtra("advType7", advType7);
                intent.putExtra("advType8", advType8);
                intent.putExtra("advType9", advType9);
                intent.putExtra("advType10", advType10);
                intent.putExtra("advType11", advType11);
                intent.putExtra("advType13", advType13);
                intent.putExtra("advType14", advType14);
                intent.putExtra("add", advType12);
                intent.putExtra("map", 1);
                if (advType12 == 2) {
                    intent.putExtra("Img", Images);
                    intent.putExtra("advertid", id);
                }
                startActivity(intent);

                return true;
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker M2) {
            }

            @Override
            public void onMarkerDragEnd(Marker M2) {

                pos2 = M2.getPosition();


              /*  Toast.makeText(DetectPlace.this,
                        "M2     " + pos2.latitude + "," + "-----" + pos2.longitude, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onMarkerDrag(Marker M2) {

            }

        });


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}