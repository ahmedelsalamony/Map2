package com.aqaralatheer.Details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.aqaralatheer.Edit.Edit;
import com.aqaralatheer.LoginRegister.LoginRegActivity;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.Maps.TestClass;
import com.aqaralatheer.MapsActivity;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.Picker.SearchResult;
import com.aqaralatheer.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class DetailMainAct extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,NavigationView.OnNavigationItemSelectedListener {
    String call;
    private SliderLayout mDemoSlider;
    Typeface typemeassage, tyepphone, typemap;
    Button message, phone, map;
    ListView l;
    double mLatitude, mLongitude;
   // String[] Slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
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
        SharedPreferences pref = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("UserId", "");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        tyepphone = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
        TextView main = (TextView) navigationView.findViewById(R.id.main);
        TextView main1 = (TextView) navigationView.findViewById(R.id.main1);
        TextView main2 = (TextView) navigationView.findViewById(R.id.main2);
        TextView main3 = (TextView) navigationView.findViewById(R.id.main3);
        TextView main4 = (TextView) navigationView.findViewById(R.id.main4);
        TextView main5 = (TextView) navigationView.findViewById(R.id.main5);
        TextView main6 = (TextView) navigationView.findViewById(R.id.com1);
        main6.setTypeface(tyepphone);
        main6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/AlatheerTech/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        TextView main7 = (TextView) navigationView.findViewById(R.id.com);
        main7.setTypeface(tyepphone);
        main.setTypeface(tyepphone);
        main1.setTypeface(tyepphone);
        main2.setTypeface(tyepphone);
        main3.setTypeface(tyepphone);
        main4.setTypeface(tyepphone);
        main5.setTypeface(tyepphone);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMainAct.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMainAct.this, LoginRegActivity.class);
                i.putExtra("Go","8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMainAct.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMainAct.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailMainAct.this, Add_advert.class);
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
                Intent i = new Intent(DetailMainAct.this, MapsActivity.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

        if (id.equals("")) {
            View x=(View)findViewById(R.id.main3line);
            View x1=(View)findViewById(R.id.main5line);
            x.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);

            main3.setVisibility(View.GONE);
            main5.setVisibility(View.GONE);
        } else {

        }



        mDemoSlider = (SliderLayout) findViewById(R.id.slider);


        l = (ListView) findViewById(R.id.transformers);


        try {
            RequestParams params = new RequestParams();
            params.put("ID", Integer.parseInt(getIntent().getExtras().getString("ID")));
            // params.put("ID",20);
            Details(params);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
        }

        TypefaceButton();

    }
    /*
    private void setupActionBar() {
        ActionBar mActionBar = getActionBar();
//        mActionBar.setDisplayShowHomeEnabled(false);
       // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.customaction, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("My Own Title");

        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Refresh Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
*/

    //change font style
    private void TypefaceButton() {


        message = (Button) findViewById(R.id.message);
        phone = (Button) findViewById(R.id.phone);
        map = (Button) findViewById(R.id.map);

        typemeassage = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
        message.setTypeface(typemeassage);
        message.setTextSize(12);


        phone.setTypeface(tyepphone);
        phone.setTextSize(12);
        typemap = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
        map.setTypeface(typemap);
        map.setTextSize(12);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                mLatitude = i.getDoubleExtra("lat", 0);
                mLongitude = i.getDoubleExtra("lang", 0);

                Intent intent = new Intent(DetailMainAct.this, TestClass.class);
                intent.putExtra("lat", mLatitude);
                intent.putExtra("lang", mLongitude);

                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + call));
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"abdouelnemr91@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "عنوان الرسالة ");
                i.putExtra(Intent.EXTRA_TEXT, "محتوى الرسالة");
                try {
                    startActivity(Intent.createChooser(i, "ارسل رسالة ...."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailMainAct.this, "لا يوجد ايميل ع جوجل", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public void Details(RequestParams params) throws JSONException {

        AsyncHttpClient.post("details.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(DetailMainAct.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التحميل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getInt("code") == 1) {
                        String[] items = response.getString("message").split("    ");
                        String[]Slider = items[7].split(",");
                        /////////////////////////////////////////////////////////////////////////////////
                        HashMap<String, String> url_maps = new HashMap<String, String>();
                        url_maps.put("xxx", "http://android.alatheertech.com/" + Slider[0]);
                        url_maps.put("yyy" , "http://android.alatheertech.com/" + Slider[1]);
                        url_maps.put("ddd", "http://android.alatheertech.com/" + Slider[2]);
                      //  System.out.println(Slider[0].substring(Slider[0].length()-4)+">>>>");

                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(DetailMainAct.this);
                            // initialize a SliderLayout
                            textSliderView
                                    .image(url_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                            ;

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", name);

                            mDemoSlider.addSlider(textSliderView);
                        }
                        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                        mDemoSlider.setDuration(4000);
                        mDemoSlider.addOnPageChangeListener(DetailMainAct.this);
                        mDemoSlider.setPresetTransformer("ZoomOut");

                        ///////////////////////////////////////////////////////////////////////////////////
                        String kind[] = {"المساحة ", "عدد الغرف", "عدد الطوابق", "المتاح", "العنوان ", "السعر", "اسم المالك ", "رقم الهاتف"};
                        String kind1[] = {"المساحة ", "عدد الطوابق", "المتاح", "العنوان ", "السعر", "اسم المالك ",  "رقم الهاتف"};
                        String kind2[] = {"المساحة ", "المتاح", "العنوان ", "السعر", "اسم المالك ",  "رقم الهاتف"};
                        String s = "";
                        call = items[9];
                        if (items[4].equals("1")) {
                            s = "ايجار";
                        } else {
                            s = "تمليك";
                        }
                        if (items[0].equals("1")) {
                            String out[] = {items[1], items[2], items[3], s, items[5], items[6], items[8], items[9]};
                            l.setAdapter(new dataListAdapter(kind, out, DetailMainAct.this));
                        } else if (items[0].equals("4")) {
                            String out[] = {items[1], s, items[5], items[6], items[8], items[9]};
                            l.setAdapter(new dataListAdapter(kind2, out, DetailMainAct.this));

                        } else {
                            String out[] = {items[1], items[3], s, items[5], items[6], items[8], items[9]};
                            l.setAdapter(new dataListAdapter(kind1, out, DetailMainAct.this));
                        }


                        // Toast.makeText(getApplicationContext(),items[0],Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "خطا فى تحميل البيانات ", Toast.LENGTH_LONG).show();

                    }

                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), "اشاره النت ضعيفه", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //Toast.makeText(getActivity().getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
                Log.e("onFailure", "----------" + responseString);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });


    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}