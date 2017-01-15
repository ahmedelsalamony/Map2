package com.aqaralatheer.Picker;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aqaralatheer.Edit.Edit;
import com.aqaralatheer.LoginRegister.LoginRegActivity;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.MapsActivity;
import com.aqaralatheer.R;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;




import cz.msebera.android.httpclient.Header;


public class Add_advert extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView v;
    boolean out = true;
    protected Context context;
    Typeface button;
    byte[] image;
    byte[] image1;
    byte[] image2;
    String editId;
    Spinner spinner, spinner2, markerSpinner,details0,details3;
    TextView head,ask,Images;
    Button b2;
    ImageView b1, b3, b4;
    EditText space, rooms, price, details, floors;
    String space2, rooms2, price2, details2, floors2,passImage,country,city;
    int rooms1, floors1, type = 0, stutes = 0, visable, find;
    Double space1, price1, L = 0.0, L1 = 0.0;
    String details1;
    Location l;
    String id;
    String Img[];
 //   Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);
        button = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
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
        main6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/AlatheerTech/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        TextView main7 = (TextView) navigationView.findViewById(R.id.com);
        main7.setTypeface(button);
        main.setTypeface(button);
        main1.setTypeface(button);
        main2.setTypeface(button);
        main3.setTypeface(button);
        main4.setTypeface(button);
        main5.setTypeface(button);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_advert.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_advert.this, LoginRegActivity.class);
                i.putExtra("Go","8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_advert.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_advert.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_advert.this, Add_advert.class);
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
                Intent i = new Intent(Add_advert.this, MapsActivity.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        id = pref.getString("UserId", "");
        if (id.equals("")) {
            View x=(View)findViewById(R.id.main3line);
            View x1=(View)findViewById(R.id.main5line);
            x.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);

            main3.setVisibility(View.GONE);
            main5.setVisibility(View.GONE);
        } else {

        }

///////////////////////////////////////////////////////////////////////////


        head = (TextView) findViewById(R.id.head);
        ask = (TextView) findViewById(R.id.ask);
        Images = (TextView) findViewById(R.id.upload_Images);
        b1 = (ImageView) findViewById(R.id.imageButton1);
        b3 = (ImageView) findViewById(R.id.imageButton2);
        b4 = (ImageView) findViewById(R.id.imageButton3);
        b2 = (Button) findViewById(R.id.btnsave);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        markerSpinner = (Spinner) findViewById(R.id.markershow);
         space = (EditText) findViewById(R.id.space);
        rooms = (EditText) findViewById(R.id.rooms);
        floors = (EditText) findViewById(R.id.floors);
        price = (EditText) findViewById(R.id.price);
        details = (EditText) findViewById(R.id.details);
        details0 = (Spinner) findViewById(R.id.details0);
        details3 = (Spinner) findViewById(R.id.details1);
        //////////////////////////////////////////////////////////////////////////////////////

//font type
        Images.setTypeface(button);
        space.setTypeface(button);
        rooms.setTypeface(button);
        floors.setTypeface(button);
        price.setTypeface(button);
        details.setTypeface(button);

        head.setTypeface(button);
         ask.setTypeface(button);
        b2.setTypeface(button);


/////////////////////////////////////////////////////////////////////////////////


        List<String> categories = new ArrayList<String>();//spinner1
        List<String> choise = new ArrayList<>();
        categories.add("شقة");
        categories.add("محل");
        categories.add("عقار");
        categories.add("اراضـــى");


        choise.add("نعــــم");
        choise.add("لا");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
/////////////////////////////////////////////////////////////////////////////
        //spinner2
        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();//spinner2

        categories2.add("ايجار ");
        categories2.add("تمليك");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);
        List<String> categories3 = new ArrayList<String>( Arrays.asList("القاهرة", "الإسكندرية", "الإسماعيلية", "أسوان",
                "أسيوط", "الأقصر", "البحر الأحمر", "البحيرة", "بني سويف", "بورسعيد", "جنوب سيناء", "الجيزة",
                "الدقهلية", "دمياط", "سوهاج", "السويس", "الشرقية", "شمال سيناء", "الغربية", "الفيوم",
                "القليوبية", "قنا", "كفر الشيخ", "مطروح", "المنوفية", "المنيا", "الوادي الجديد"));//spinner2

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories3);

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        details0.setAdapter(dataAdapter3);

        details0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//check who selected

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                 city = (String) parent.getItemAtPosition(position);
                System.out.println("soso"+view.getContext());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////

        List<String> categories4 = new ArrayList<String>( Arrays.asList("مصر"));//spinner2

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories4);

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        details3.setAdapter(dataAdapter4);

        details3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//check who selected

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                country = (String) parent.getItemAtPosition(position);
              //  System.out.println("soso"+item);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });


        /////////////////////////////////////////////////////////////////////////

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//check who selected

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                switch (position) {
                    case 0:
                        type = 1;
                        rooms.setVisibility(View.VISIBLE);
                        floors.setVisibility(View.VISIBLE);

                        break;
                    case 1:
                        type = 2;
                        rooms.setVisibility(View.GONE);
                        floors.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        type = 3;
                        floors.setVisibility(View.VISIBLE);
                        rooms.setVisibility(View.GONE);
                        break;
                    case 3:
                        type = 4;
                        rooms.setVisibility(View.GONE);
                        floors.setVisibility(View.GONE);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                type = 0;

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                switch (position) {
                    case 0:
                        stutes = 1;
                        break;
                    case 1:
                        stutes = 2;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                stutes = 0;
            }
        });


        // Creating adapter for spinner
        ArrayAdapter<String> choiseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choise);

        // Drop down layout style - list view with radio button
        choiseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        markerSpinner.setAdapter(choiseAdapter);
        markerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        find = 1;

                        LocationManager locationManager;
                        String svcName = Context.LOCATION_SERVICE;
                        locationManager = (LocationManager) getSystemService(svcName);

//permations /////////////////////////////
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                      //  Toast.makeText(getApplicationContext(), l+"", Toast.LENGTH_LONG).show();
                        if(l != null){
                            L = l.getLatitude();
                            L1 = l.getLongitude();


                        }else{
                            if (getIntent().getIntExtra("map",0) != 1) {
                            SHOWDIALOGE();





                            }
                        }
                        break;
                    case 1:
                        find = 2;
                        if (getIntent().getIntExtra("map",0) != 1) {
                            SHOWDIALOGE();

                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                find = 0;
            }

        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////




        if (getIntent().getIntExtra("add", 0) == 2) {
            head.setText("عدل اعلانك");
            b2.setText("حفظ التعديل");
            details0.setVisibility(View.GONE);
            details3.setVisibility(View.GONE);
            if(getIntent().getIntExtra("map",0)==0){
                L = getIntent().getDoubleExtra("lat", 0.0);
                L1 = getIntent().getDoubleExtra("lang", 0.0);
            type = Integer.parseInt(getIntent().getExtras().getString("type"));
            spinner.setSelection(type);
            price2 = getIntent().getExtras().getString("price");
            editId = getIntent().getExtras().getString("advertid");
            spinner2.setSelection(Integer.parseInt(getIntent().getExtras().getString("stutes")));
            rooms2 = getIntent().getExtras().getString("rooms");
            floors2 = getIntent().getExtras().getString("floors");
            space2 = getIntent().getExtras().getString("space");
            details2 = getIntent().getExtras().getString("addres");
            passImage= getIntent().getExtras().getString("Images");
            city=  getIntent().getExtras().getString("city");
                country=  getIntent().getExtras().getString("country");
            Img = getIntent().getExtras().getString("Images").split(",");
                ProgressBar progressBar = null;

                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.getIndeterminateDrawable().setColorFilter( getApplicationContext().getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view


//load the image url with a callback to a callback method/class
                Picasso.with(getApplicationContext())
                        .load("http://android.alatheertech.com/" +Img[0])
                        .into(b1, new ImageLoadedCallback(progressBar) {
                            @Override
                            public void onSuccess() {
                                if (this.progressBar != null) {
                                    this.progressBar.setVisibility(View.GONE);


                                }
                            }
                        });
                ProgressBar progressBar1 = null;

                progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
                progressBar1.setVisibility(View.VISIBLE);
                progressBar1.getIndeterminateDrawable().setColorFilter( getApplicationContext().getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view


//load the image url with a callback to a callback method/class
                Picasso.with(getApplicationContext())
                        .load("http://android.alatheertech.com/" +Img[1])
                        .into(b3, new ImageLoadedCallback(progressBar1) {
                            @Override
                            public void onSuccess() {
                                if (this.progressBar!= null) {
                                    this.progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                ProgressBar progressBar3 = null;

                progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar3.setVisibility(View.VISIBLE);
                progressBar3.getIndeterminateDrawable().setColorFilter( getApplicationContext().getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view


//load the image url with a callback to a callback method/class
                Picasso.with(getApplicationContext())
                        .load("http://android.alatheertech.com/" +Img[2])
                        .into(b4, new ImageLoadedCallback(progressBar3) {
                            @Override
                            public void onSuccess() {
                                if (this.progressBar != null) {
                                    this.progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

           /* Picasso.with(getBaseContext()).load("http://aqarak.esy.es/" + Img[1]).placeholder(R.drawable.process).into(b3);
            Picasso.with(getBaseContext()).load("http://aqarak.esy.es/" + Img[2]).placeholder(R.drawable.process).into(b4);*/
            }else{

                L = getIntent().getDoubleExtra("lat", 0.0);
                L1 = getIntent().getDoubleExtra("lang", 0.0);
                type=getIntent().getIntExtra("advType1", 0);
                spinner.setSelection(type);
                spinner2.setSelection(getIntent().getIntExtra("advType2", 0));
                markerSpinner.setSelection(getIntent().getIntExtra("advType8", 0));
                space2=getIntent().getStringExtra("advType3");
                editId = getIntent().getExtras().getString("advertid");
                rooms2=getIntent().getStringExtra("advType5");
                floors2=getIntent().getStringExtra("advType4");
                price2=getIntent().getStringExtra("advType6");

                details2=getIntent().getStringExtra("advType7");
                city=getIntent().getStringExtra("advType13");
               country =getIntent().getStringExtra("advType14");
                passImage= getIntent().getExtras().getString("Img");
                Img = getIntent().getExtras().getString("Img").split(",");
                Picasso.with(getBaseContext()).load("http://android.alatheertech.com/" + Img[0]).into(b1);
                Picasso.with(getBaseContext()).load("http://android.alatheertech.com/" + Img[1]).into(b3);
                Picasso.with(getBaseContext()).load("http://android.alatheertech.com/" + Img[2]).into(b4);

            }
            switch (type) {
                case 1:

                    rooms.setVisibility(View.VISIBLE);
                    floors.setVisibility(View.VISIBLE);
                    rooms.setText(rooms2);
                    floors.setText(floors2);

                    break;
                case 2:
                    floors.setText(floors2);
                    rooms.setVisibility(View.GONE);
                    floors.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    floors.setText(floors2);
                    floors.setVisibility(View.VISIBLE);
                    rooms.setVisibility(View.GONE);
                    break;
                case 4:

                    rooms.setVisibility(View.GONE);
                    floors.setVisibility(View.GONE);
                    break;
            }
            price.setText(price2);
            space.setText(space2);
            details.setText(details2);
           /* details3.setText(country);
            details0.setText(city);*/
            b3.setVisibility(View.VISIBLE);
            b4.setVisibility(View.VISIBLE);


        } else {
            head.setText("اضف اعلانك");
            b2.setText("تســـجيل");
            if(getIntent().getIntExtra("map",0)==1) {
                L = getIntent().getDoubleExtra("lat", 0.0);
                L1 = getIntent().getDoubleExtra("lang", 0.0);

                type = getIntent().getIntExtra("advType1", 0);
                spinner.setSelection(type);
                spinner2.setSelection(getIntent().getIntExtra("advType2", 0));
                markerSpinner.setSelection(getIntent().getIntExtra("advType8", 0));
                space2 = getIntent().getStringExtra("advType3");

                rooms2 = getIntent().getStringExtra("advType5");
                floors2 = getIntent().getStringExtra("advType4");
                price2 = getIntent().getStringExtra("advType6");

                details2 = getIntent().getStringExtra("advType7");
                city=getIntent().getStringExtra("advType14");
                country =getIntent().getStringExtra("advType13");

                switch (type) {
                    case 1:

                        rooms.setVisibility(View.VISIBLE);
                        floors.setVisibility(View.VISIBLE);
                        rooms.setText(rooms2);
                        floors.setText(floors2);

                        break;
                    case 2:
                        floors.setText(floors2);
                        rooms.setVisibility(View.GONE);
                        floors.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        floors.setText(floors2);
                        floors.setVisibility(View.VISIBLE);
                        rooms.setVisibility(View.GONE);
                        break;
                    case 4:

                        rooms.setVisibility(View.GONE);
                        floors.setVisibility(View.GONE);
                        break;
                }
                price.setText(price2);
                space.setText(space2);
                details.setText(details2);
               /* details3.setText(country);
                details0.setText(city);*/
              //  b3.setVisibility(View.VISIBLE);
              //  b4.setVisibility(View.VISIBLE);

            }






        }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out = true;
             /*
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                List<android.location.Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(L,  L1, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0)
                    System.out.println("soso"+addresses.get(0).getAddressLine(1));*/


                //validation for input
                if (cheak() != false) {
//get latidute and loongtude


////////////////////////////////////////////////////////////////////////////////////////////////////////////
//get ever one want what

                    if (find == 2||(find==1 && l== null )) {

                        //  Intent intent = getIntent();


                        L = getIntent().getDoubleExtra("lat", 0);
                        L1 = getIntent().getDoubleExtra("lang", 0);

                    }

//                    Toast.makeText(Add_advert.this, "Lat    :  " + L
//                    + "Lon     :  " + L1, Toast.LENGTH_LONG).show();
                    space1 = Double.parseDouble(space.getText().toString());

                    price1 = Double.parseDouble(price.getText().toString());
                    details1 = details.getText().toString();
                   /* country=details3.getText().toString();
                    city=details0.getText().toString();*/

                    if (type == 2 || type == 3 || type == 1) {
                        floors1 = Integer.parseInt(floors.getText().toString());
                    }
                    if (type == 1) {
                        rooms1 = Integer.parseInt(rooms.getText().toString());
                    }

//Log.e("Out",type+" "+details1+" "+L+" "+L1+" "+floors1+" "+rooms1+" "+space1+" "+getIntent().getExtras().getString("id")+ " "+image1+image+image2);
                    //Log.e("Out1",getIntent().getExtras().getString("id")+"");
///Send paramter to web server /////////////////////
                    try {

                        RequestParams params = new RequestParams();

                        params.put("type", type);
                        Log.e("onClick: " + "type", type + "");
                        params.put("address", details1);
                        Log.e("onClick: " + "address", details1 + "");
                      /* params.put("latituted", 30.058056);
                        params.put("longt", 31.228889);*/
                        params.put("latituted", L);
                        params.put("longt", L1);
                        params.put("price", price1);
                        params.put("city",city);
                        params.put("country",country);
                        Log.e("onClick: " + "price", price1 + "");
                        if (type == 4) {
                            params.put("floors", 0);

                        } else {
                            params.put("floors", floors1);
                            Log.e("onClick: " + "floor", floors1 + "");
                        }
                        if (type == 1) {
                            params.put("rooms", rooms1);
                            Log.e("onClick: " + "rooms", rooms1 + "");
                        } else {
                            params.put("rooms", 0);

                        }

                        params.put("space", space1);
                        Log.e("onClick: " + "space", space1 + "");

                        params.put("status", stutes);
                        Log.e("onClick: " + "statues", stutes + "");

                        if (getIntent().getIntExtra("add", 0) == 2) {

                            params.put("choose", 2);
                            params.put("advertId", editId);
                            Log.e("onClick: " + "addId", editId + "");
                            Log.e("onClick: ", "TEST");
                            Log.e("onClick: " + "add", getIntent().getIntExtra("add", 0) + "");
                            String numimg = "not";
                          //  Log.e("onClick1: ", getIntent().getExtras().getString("Images"));
                            if (image != null) {
                                params.put("image1", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");
                                Log.e("onClick: ", "????" + image.toString());
                                if (numimg.equals("not")) {

                                    numimg = "1";

                                } else {
                                    numimg += "," + "1";

                                }

                            }
                            if (image1 != null) {
                                Log.e("onClick: ", "????" + image1.toString());
                                params.put("image2", new ByteArrayInputStream(image1), getCurrentDateAndTime() + ".png");
                                if (numimg.equals("not")) {
                                    numimg = "2";

                                } else {
                                    numimg += "," + "2";
                                }

                            }


                            if (image2 != null) {
                                Log.e("onClick: ", "????" + image2.toString());
                                params.put("image3", new ByteArrayInputStream(image2), getCurrentDateAndTime() + ".png");
                                if (numimg.equals("not")) {
                                    numimg = "3";

                                } else {
                                    numimg += "," + "3";

                                }

                            }

                            Log.e("onClick: ", numimg);


                            params.put("update", numimg);

                            params.put("delet", passImage);


                        } else if (getIntent().getIntExtra("add", 0) == 1) {
                            params.put("choose", 1);
                            params.put("image", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");
                            params.put("image1", new ByteArrayInputStream(image1), getCurrentDateAndTime() + ".png");
                            params.put("image2", new ByteArrayInputStream(image2), getCurrentDateAndTime() + ".png");
                            params.put("cust_id", id);
                        }


                        Add(params);
                    } catch (Exception ex) {
                        // Toast.makeText(getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
                        Log.e("xxx", "Exception" + ex);
                        Toast.makeText(getApplicationContext(), "اعد المحاولة فى وقت اخر ", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }

    public void SHOWDIALOGE() {
       /* LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        builder.show();*/

        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.addADVERT,detectPlace).addToBackStack(null).commit();*/

        Intent intent = new Intent(Add_advert.this, DetectPlace.class);

        intent.putExtra("advType1", type);
        intent.putExtra("advType2", stutes);
        intent.putExtra("advType3", space.getText().toString());
        intent.putExtra("advType4", floors.getText().toString());
        intent.putExtra("advType5", rooms.getText().toString());
        intent.putExtra("advType6", price.getText().toString());
        intent.putExtra("advType7", details.getText().toString());
        intent.putExtra("advType8", find);
       /* intent.putExtra("advType13", details3.getText().toString());
        intent.putExtra("advType14", details0.getText().toString());*/
        intent.putExtra("add", getIntent().getIntExtra("add", 0));
        if(getIntent().getIntExtra("add", 0)==2){
            intent.putExtra("Img", getIntent().getExtras().getString("Images"));
            intent.putExtra("advertid",getIntent().getExtras().getString("advertid"));
            intent.putExtra("lat",getIntent().getExtras().getDouble("lat"));
            intent.putExtra("lang",getIntent().getExtras().getDouble("lang"));

        }

       /* intent.putExtra("advType9", b1.getBackground().toString());
        intent.putExtra("advType10", b3.getBackground().toString());
        intent.putExtra("advType11", b4.getBackground().toString());*/

        startActivity(intent);
    }

    public void loadImagefromGallery(View view) {
        int id = view.getId();
        if (id == R.id.imageButton1) {
            v = b1;
            visable = 1;
             // b3.setVisibility(View.VISIBLE);
        } else if (id == R.id.imageButton2) {
            v = b3;
            visable = 2;

            //  b4.setVisibility(View.VISIBLE);
        } else if (id == R.id.imageButton3) {
            v = b4;
            visable = 3;
         }
        open();
    }

    /* public void loadImagefromGallery1(View view) {
         open();
     }
     public void loadImagefromGallery2(View view) {
         open();
     }*/
//validate
    public boolean cheak() {
        space2 = space.getText().toString();
        floors2 = floors.getText().toString();
        rooms2 = rooms.getText().toString();
        price2 = price.getText().toString();
        details2 = details.getText().toString();

        if(L==0.0){
            Toast.makeText(getApplicationContext(),"من فضلك ادخل الموقع", Toast.LENGTH_LONG).show();
            out = false;
        }
        if (type == 0) {
            Toast.makeText(getApplicationContext(), "من فضلك ادخل نوع الاعلان", Toast.LENGTH_LONG).show();
            out = false;
        }
        if(getIntent().getIntExtra("add", 0) != 2){
            if (country.equals("الدولة")) {
                Toast.makeText(getApplicationContext(), "من فضلك ادخل الدولة", Toast.LENGTH_LONG).show();
                out = false;
            }
            if (city.equals("المحافظة")) {
                Toast.makeText(getApplicationContext(), "من فضلك ادخل اسم المحافظة", Toast.LENGTH_LONG).show();
                out = false;
            }
        }


        if (stutes == 0) {
            Toast.makeText(getApplicationContext(), "من فضلك ادخل نوع التعامل", Toast.LENGTH_LONG).show();
            out = false;
        }
        if ((image == null || image1 == null || image2 == null) && getIntent().getIntExtra("add", 0) == 1) {
              Toast.makeText(getApplicationContext(), "من فضلك ادخل ثلاثه صور ", Toast.LENGTH_LONG).show();
            out = false;
        }
        if (TextUtils.isEmpty(space2)) {
            space.setError("ادخل قيمة صحيحة");
            space.requestFocus();
            out = false;
        }
        if ((TextUtils.isEmpty(floors2) && (type == 1 || type == 2 || type == 3))||(TextUtils.isEmpty(floors2)&&type==0)) {
        /* drawable= getResources().getDrawable(R.drawable.error);
            drawable.setBounds(0,0, drawable.getIntrinsicHeight(),
                    drawable.getIntrinsicWidth());*/
            floors.setError("ادخل قيمة صحيحة");

            floors.requestFocus();
            out = false;
        }
        if (TextUtils.isEmpty(details2)) {
            details.setError("ادخل قيمة صحيحة");
            details.requestFocus();
            out = false;
        }
      /*  if (TextUtils.isEmpty(country)) {
            details3.setError("ادخل اسم الدولة");
            details3.requestFocus();
            out = false;
        }
        if (TextUtils.isEmpty(city)) {
            details0.setError("ادخل قيمة صحيحة");
            details0.requestFocus();
            out = false;
        }*/
        if (TextUtils.isEmpty(price2)) {
            price.setError("ادخل قيمة صحيحة");
            price.requestFocus();
            out = false;
        }

            if ((TextUtils.isEmpty(rooms2)&&type == 1)  ||(TextUtils.isEmpty(rooms2)&&type==0)) {
                rooms.setError("ادخل قيمة صحيحة");
                rooms.requestFocus();
                out = false;
            }



        return out;
    }


    //web server function
    public void Add(RequestParams params) throws JSONException {

        AsyncHttpClient.post("add.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(Add_advert.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التسجيل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {

                    if (response.getInt("code") == 1) {
                        Toast.makeText(getApplicationContext(), "تم التسجيل نجاح", Toast.LENGTH_LONG).show();

                        /////////////////////////Data/////////////

                        Intent intent = new Intent(getApplication(), MapsActivity.class);
                        intent.putExtra("so",1);
                        startActivity(intent);

                    } else {

                        Toast.makeText(getApplicationContext(), "اعد المحاولة", Toast.LENGTH_LONG).show();

                    }
                    // String[] items = response.getString("message").split(",");

                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(),  "اشاره النت ضغيفه", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "اشاره النت ضغيفه", Toast.LENGTH_LONG).show();
                Log.e("onFailure", "----------" + responseString);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });


    }


//Image Selector

    private void open() {

        final Dialog dialog = new Dialog(this);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog);
        TextView imgtext = (TextView) dialog.findViewById(R.id.imgtext);
        // dialog.setTitle("اختر صورتك");
        imgtext.setTypeface(button);
        Button mChooseFromSDBtn = (Button) dialog.findViewById(R.id.button_Chooes);
        Button mCaptureBtn = (Button) dialog.findViewById(R.id.button_Capture);
         mChooseFromSDBtn.setTypeface(button);
        mCaptureBtn.setTypeface(button);


            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.


            mChooseFromSDBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if (ActivityCompat.shouldShowRequestPermissionRationale(Add_advert.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                        intent_gallery.setType("image*//*");
                        startActivityForResult(intent_gallery, 1);
                        dialog.dismiss();
                    } else {
                        Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                        intent_gallery.setType("image*//*");
                        startActivityForResult(intent_gallery, 1);
                        dialog.dismiss();
                    }*/


                  if (Build.VERSION.SDK_INT >= 23){
                        // Here, thisActivity is the current activity
                        if (ContextCompat.checkSelfPermission(Add_advert.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Add_advert.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                                // Show an expanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.

                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(Add_advert.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1
                                        );

                                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        }else{
                            ActivityCompat.requestPermissions(Add_advert.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        }

                    }else {

                      Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                      intent_gallery.setType("image/*");
                      startActivityForResult(intent_gallery, 1);
                      dialog.dismiss();
                    }

                } });

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);


                dialog.dismiss();
            }
        });


        dialog.show();
        Display display =((WindowManager)getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();

        Log.v("width", width+"");
        //dialog.getWindow().setLayout((6*width)/6,(3*height)/6);
        dialog.getWindow().setLayout((6*width)/6, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //Image name Must b random So get from Current time
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = df.format(c.getTime());

        int min = 0;
        int max = 100000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        formattedDate = formattedDate + i1;

        return formattedDate;
    }

    //Recive Image after select
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Drawable drawable;
        Bitmap bitmap;
        ByteArrayOutputStream stream;
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap yourSelectedImage = (Bitmap) data.getExtras().get("data");
                    v.setImageBitmap(yourSelectedImage);

                    drawable = v.getDrawable();
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    if (visable == 1) {
                        image = stream.toByteArray();
                        b3.setVisibility(View.VISIBLE);
                    } else if (visable == 2) {
                        image1 = stream.toByteArray();
                        b4.setVisibility(View.VISIBLE);
                    } else if (visable == 3) {
                        image2 = stream.toByteArray();
                    }
                    //  Toast.makeText(getApplicationContext(),image+"",Toast.LENGTH_LONG).show();
                }
                break;

            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = this.getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

                    v.setImageBitmap(yourSelectedImage);
                    // Picasso.with(this).load(filePath).into(imageView_profile);
                    drawable = v.getDrawable();
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    if (visable == 1) {
                        image = stream.toByteArray();
                        b3.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), image + "", Toast.LENGTH_LONG).show();
                    } else if (visable == 2) {
                        image1 = stream.toByteArray();
                        b4.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), image1 + "", Toast.LENGTH_LONG).show();
                    } else if (visable == 3) {
                        image2 = stream.toByteArray();
//                        Toast.makeText(getApplicationContext(), image2 + "", Toast.LENGTH_LONG).show();
                    }

                }


        }


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}


