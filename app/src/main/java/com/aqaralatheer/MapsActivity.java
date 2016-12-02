package com.aqaralatheer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.aqaralatheer.Details.DetailMainAct;
import com.aqaralatheer.Edit.Edit;
import com.aqaralatheer.LoginRegister.LoginRegActivity;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.Maps.TestClass;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.Picker.SearchResult;
import com.aqaralatheer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    String searchid = "";
    String id;
    Double searchlat, searchlang;
    Button search, callus, addAdvert;
    PopupMenu popup;
    Button btn_profile;
    TextView mk1, mk2, mk3, mk4;
    boolean flag = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_maps);
       /* getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle(Html.fromHtml("<strong>عقـــــاري </strong>"));
*/

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            flag = true;
        } else {
            Toast.makeText(MapsActivity.this, "من فضلك تحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            finish();
        }

        Typeface button = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
//font type
        mk1 = (TextView) findViewById(R.id.mk1);
        mk2 = (TextView) findViewById(R.id.mk2);
        mk3 = (TextView) findViewById(R.id.mk3);
        mk4 = (TextView) findViewById(R.id.mk4);

        mk1.setTypeface(button);
        mk2.setTypeface(button);
        mk3.setTypeface(button);
        mk4.setTypeface(button);
        mk1.setTextSize(13);
        mk2.setTextSize(13);
        mk3.setTextSize(13);
        mk4.setTextSize(13);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Back", "0");
        editor.commit();


        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

        }

        if (!gps_enabled && !network_enabled) {

            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS Not enabled");
            dialog.setPositiveButton("Open GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });


            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences pref = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        id = pref.getString("UserId", "");
        // String tests = pref.getString("pass", "");
        // Toast.makeText(getApplicationContext(),id+tests,Toast.LENGTH_LONG).show();

        //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        search = (Button) findViewById(R.id.search);
        callus = (Button) findViewById(R.id.callUs);
        addAdvert = (Button) findViewById(R.id.advert);
        search.setTypeface(button);
        search.setTextSize(12);
        callus.setTypeface(button);
        callus.setTextSize(12);
        addAdvert.setTypeface(button);
        addAdvert.setTextSize(12);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                //Inflating the Popup using xml file

                //registering popup with OnMenuItemClickListener
            /*    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                      *//*  Toast.makeText(
                                MapsActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();*//*
                        searchid = item.getTitle().toString();
                        try {

                            RequestParams params = new RequestParams();

                            params.put("ID", searchid);

                            Search(params);

                            //      Toast.makeText(getApplicationContext(), searchlang + "" + searchlat, Toast.LENGTH_LONG).show();

                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "اشاره النت ضغيفه", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }
                });

                popup.show(); //showing popup menu
            */
                Intent i = new Intent(MapsActivity.this, SearchResult.class);
                startActivity(i);
            }
        });

        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01061512429"));
                startActivity(intent);
            }
        });

        addAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.equals("")) {
                    Intent intent = new Intent(MapsActivity.this, LoginRegActivity.class);
                    //  intent.putExtra("where",2);
                    intent.putExtra("Go", "0");
                    intent.putExtra("add", 1);
                    intent.putExtra("map", 0);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MapsActivity.this, Add_advert.class);
                    //  intent.putExtra("where",2);
                    //intent.putExtra("Go","0");
                    intent.putExtra("add", 1);
                    intent.putExtra("map", 0);
                    startActivity(intent);
                }

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView t = (TextView) toolbar.findViewById(R.id.toolbar_title);
        // t.setTypeface(button);
        t.setText(Html.fromHtml("<strong>عقـــــاري </strong>"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView main = (TextView) navigationView.findViewById(R.id.main);
        TextView main1 = (TextView) navigationView.findViewById(R.id.main1);
        TextView main2 = (TextView) navigationView.findViewById(R.id.main2);
        TextView main3 = (TextView) navigationView.findViewById(R.id.main3);
        TextView main4 = (TextView) navigationView.findViewById(R.id.main4);
        TextView main5 = (TextView) navigationView.findViewById(R.id.main5);
        TextView main6 = (TextView) navigationView.findViewById(R.id.com1);
        main6.setTypeface(button);
        TextView main7 = (TextView) navigationView.findViewById(R.id.com);
        main7.setTypeface(button);
        main6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/AlatheerTech/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        main.setTypeface(button);
        main1.setTypeface(button);
        main2.setTypeface(button);
        main3.setTypeface(button);
        main4.setTypeface(button);
        main5.setTypeface(button);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, LoginRegActivity.class);
                i.putExtra("Go","8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, Add_advert.class);
                startActivity(i);
            }
        });
        main5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
                //
                // SharedPreferences.Editor editor = sharedPref.edit();
                sharedPref.edit().remove("UserId").commit();
                // editor.commit();e
                Intent i = new Intent(MapsActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        btn_profile = (Button) findViewById(R.id.btn_profile);

        btn_profile.setTypeface(button);
        btn_profile.setTextSize(12);
        if (id.equals("")) {
            View x=(View)findViewById(R.id.main3line);
            View x1=(View)findViewById(R.id.main5line);
            x.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);
            btn_profile.setVisibility(View.GONE);
            main3.setVisibility(View.GONE);
            main5.setVisibility(View.GONE);
        } else {
            btn_profile.setVisibility(View.VISIBLE);
        }
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, Edit.class);
                //  i.putExtra("where",1);
                startActivity(i);

                //   Toast.makeText(getApplicationContext(),"btn profile",Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //  mMap.setMyLocationEnabled(true);

            // Show rationale and request permission.

            mMap = googleMap;

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(30.045206, 31.236495))
                    .zoom(10)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));


            try {
                RequestParams params = new RequestParams();
                params.put("UserNme", "");
                DrawMap(params);
                //cairo = DrawCircls(30.8197, 30.8242, 7000);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "اشاره النت ضغيفه", Toast.LENGTH_LONG).show();
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Intent intent = new Intent(MapsActivity.this, DetailMainAct.class);

                    intent.putExtra("ID", marker.getTitle());

                    intent.putExtra("lat", marker.getPosition().latitude);
                    intent.putExtra("lang", marker.getPosition().longitude);

                    //  Toast.makeText(getBaseContext(),marker.getTitle(),Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    return true;
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MapsActivity.this, TestClass.class);
                    intent.putExtra("ID", marker.getTitle());
                    startActivity(intent);
                }
            });

/************************* End Of Markers ****************************/
            final LatLng l = new LatLng(30.045206, 31.236495);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(l)
                    .zoom(6)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public Circle DrawCircls(double Latitude, double Longitude, int radius) {
        final Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(Latitude, Longitude))
                .radius(radius)
                .strokeColor(Color.BLACK)
                .fillColor(0x402f0000));

        return circle;
    }

    ////////////////////////////////web server //////////////////////////
    public void DrawMap(RequestParams params) throws JSONException {

        AsyncHttpClient.post("drawmap.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(MapsActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التحميل...");
                if (flag == true)

                    progressDialog.show();

                else {

                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getInt("code") == 1) {
                        String[] items = response.getString("message").split("  ");


                        String[] items2 = new String[8];
                        String[][] out = new String[items.length][8];
                        for (int i = 0; i < items.length; i++) {
                            items2 = items[i].split(" ");
                            for (int j = 0; j < 8; j++) {
                                out[i][j] = items2[j];
                            }
                        }


                        int mark = R.drawable.marker4;


                        for (int i = 0; i < out.length; i++) {
                            LatLng l = new LatLng(Double.parseDouble(out[i][2]), Double.parseDouble(out[i][3]));

                            System.out.println("outttttt" + out[i][1]);

                            if (out[i][1].equals("1")) {
                                mark = R.drawable.marker1;
                            } else if (out[i][1].equals("2")) {
                                mark = R.drawable.marker2;
                            } else if (out[i][1].equals("3")) {
                                mark = R.drawable.marker3;
                            } else if (out[i][1].equals("4")) {
                                mark = R.drawable.marker4;
                            }

                            mMap.addMarker(new MarkerOptions()
                                    .position(l)
                                    .title(out[i][0])
                                    .icon(BitmapDescriptorFactory.fromResource(mark))
                                    .snippet(out[i][4])
                                    .visible(true)
                            );
                        }


                    } else {


                    }


                } catch (Exception ex) {

                    //    Toast.makeText(getApplicationContext(), "response\n" + "" + ex, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.e("onFailure", "----------" + responseString);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }

        });


    }


    public void Search(RequestParams params) throws JSONException {
        //  Toast.makeText(getApplicationContext(), searchid, Toast.LENGTH_LONG).show();

        AsyncHttpClient.post("search.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(MapsActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التحميل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getInt("code") == 1) {
                        String[] items = response.getString("message").split(" ");
                        searchlat = Double.parseDouble(items[0]);
                        searchlang = Double.parseDouble(items[1]);

                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(searchlat, searchlang))
                                .zoom(13)
                                .bearing(0)
                                .tilt(45)
                                .build();

                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));


                    } else {


                    }
                    // String[] items = response.getString("message").split(",");

                } catch (Exception ex) {

                    //  Toast.makeText(getApplicationContext(), "response\n" + "" + ex, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //Toast.makeText(getActivity().getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      /*  if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}