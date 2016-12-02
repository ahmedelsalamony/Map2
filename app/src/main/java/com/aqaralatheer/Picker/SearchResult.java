package com.aqaralatheer.Picker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchResult extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    Typeface button;
    String id,city1;

    TextView notfound;
    Spinner spinner, spinner2,spinner3;
    ListView advert_list;
    int typed = 0, stutesd = 0;
    TextView textadvert;
    EditText salaryFrom, salaryTo, areaFrom, areaTo;
    Button call, advertd, home;
    Double a1 = 0.0, a2 = 0.0, p1 = 0.0, p2 = 0.0;
    String[] kind;
    String[] kind1;//price
    String[] kind3;//First Image
    String[] kind4;//ID
    String[] kind5;//Images
    String[] stutes;
    String[] lat;
    String[] lang;
    String[] rooms;
    String[] floors;
    String[] space;
    String[] addres;
    String[] type;
    String[] city;
    String[] country;

    String[] kindn;
    String[] kind1n;//price
    String[] kind3n;//First Image
    String[] kind4n;//ID
    String[] kind5n;//Images
    String[] stutesn;
    String[] latn;
    String[] langn;
    String[] roomsn;
    String[] floorsn;
    String[] spacen;
    String[] addresn;
    String[] typen;
    String[] cityn;
    String[] countryn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

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
                Intent i = new Intent(SearchResult.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResult.this, LoginRegActivity.class);
                i.putExtra("Go","8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResult.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResult.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResult.this, Add_advert.class);
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
                Intent i = new Intent(SearchResult.this, MapsActivity.class);
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

        advert_list = (ListView) findViewById(R.id.alladvert);
        call = (Button) findViewById(R.id.call);
        advertd = (Button) findViewById(R.id.advertd);
        home = (Button) findViewById(R.id.home);

         notfound=(TextView)findViewById(R.id.notfound);
        notfound.setTypeface(button);
        call.setTypeface(button);
        advertd.setTypeface(button);
        home.setTypeface(button);
        call.setTextSize(12);
        advertd.setTextSize(12);
        home.setTextSize(12);


        try {

            RequestParams params = new RequestParams();
            params.put("Choose", "1");

            // params.put("image", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");

            advert(params);

            //      Toast.makeText(getApplicationContext(), searchlang + "" + searchlat, Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
        }

        textadvert = (TextView) findViewById(R.id.textadvert);
        textadvert.setTypeface(button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sss);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                search();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchResult.this, MapsActivity.class);
                startActivity(i);
            }
        });

        advertd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.equals("")) {
                    Intent intent = new Intent(SearchResult.this, LoginRegActivity.class);
                    //  intent.putExtra("where",2);
                    intent.putExtra("Go", "0");
                    intent.putExtra("add", 1);
                    intent.putExtra("map", 0);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SearchResult.this, Add_advert.class);
                    //  intent.putExtra("where",2);
                    //intent.putExtra("Go","0");
                    intent.putExtra("add", 1);
                    intent.putExtra("map", 0);
                    startActivity(intent);
                }
            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01061512429"));
                startActivity(intent);
            }
        });
    }


    public void search() {
        final Dialog dialog = new Dialog(this);
       // dialog.setTitle("اختر طريقة البحث"
        // );
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_search);

        TextView imgtext = (TextView) dialog.findViewById(R.id.imgtext1);
        // dialog.setTitle("اختر صورتك");
        imgtext.setTypeface(button);
        salaryFrom = (EditText) dialog.findViewById(R.id.fromp);
        salaryTo = (EditText) dialog.findViewById(R.id.top);
        areaFrom = (EditText) dialog.findViewById(R.id.fromp1);
        areaTo = (EditText) dialog.findViewById(R.id.top1);

        Button ok = (Button) dialog.findViewById(R.id.dialogButtonOK);


//font type
        ok.setTypeface(button);


/////////////////////////////////////////////////////////////////////////////////

        spinner = (Spinner) dialog.findViewById(R.id.s_AdvSort);
        spinner2 = (Spinner) dialog.findViewById(R.id.s_Advtype);

        spinner3 = (Spinner) dialog.findViewById(R.id.cityspinner);
        List<String> categories = new ArrayList<String>();//spinner1
        List<String> choise = new ArrayList<>();
        categories.add("شقة");
        categories.add("محل");
        categories.add("عقار");
        categories.add("اراضـــى");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
/////////////////////////////////////////////////////////////////////////////
        //spinner2
        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();//spinner21

        categories2.add("ايجار ");
        categories2.add("تمليك");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

/////////////////////////////////////////////////////////////////////////

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//check who selected

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                switch (position) {
                    case -1:
                        typed = 0;

                        break;
                    case 0:
                        typed = 1;

                        break;
                    case 1:
                        typed = 2;
                        break;
                    case 2:
                        typed = 3;
                        break;
                    case 3:
                        typed = 4;
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                typed = 0;

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                switch (position) {
                    case -1:
                        stutesd = 0;
                        break;
                    case 0:
                        stutesd = 1;
                        break;
                    case 1:
                        stutesd = 2;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                stutesd = 0;
            }
        });

        List<String> categories3 = new ArrayList<String>( Arrays.asList("القاهرة", "الإسكندرية", "الإسماعيلية", "أسوان",
                "أسيوط", "الأقصر", "البحر الأحمر", "البحيرة", "بني سويف", "بورسعيد", "جنوب سيناء", "الجيزة",
                "الدقهلية", "دمياط", "سوهاج", "السويس", "الشرقية", "شمال سيناء", "الغربية", "الفيوم",
                "القليوبية", "قنا", "كفر الشيخ", "مطروح", "المنوفية", "المنيا", "الوادي الجديد"));//spinner2

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories3);

        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_list_item_1);

        // attaching data adapter to spinner
        spinner3.setAdapter(dataAdapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//check who selected

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                city1 = (String) parent.getItemAtPosition(position);
                System.out.println("soso"+city1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  int[] out1 = {};
                String pt1,pt2,at1,at2;
                pt1=salaryFrom.getText().toString();
                pt2=salaryTo.getText().toString();
                at1=areaFrom.getText().toString();
                at2=areaTo.getText().toString();
                if (!TextUtils.isEmpty(pt1) && !TextUtils.isEmpty(pt2)) {
                p1=Double.parseDouble(pt1);
                p2=Double.parseDouble(pt2);}
                if (!TextUtils.isEmpty(at1)&& !TextUtils.isEmpty(at2)) {
                a1=Double.parseDouble(at1);
                a2=Double.parseDouble(at2);}
                ArrayList<Integer> out = new ArrayList<Integer>();
                ArrayList<Integer> out1 = new ArrayList<Integer>();
                int Searchcity=0;
                int print = 0;
                int entertype = 0;
                int enterstutes = 0;
                int enterprice=0;
                int enterarea=0;
                //  Toast.makeText(getApplicationContext(), "من فضلك ادخل نوع الاعلان", Toast.LENGTH_LONG).show();

                for (int i = 0; i < type.length; i++) {
                    Log.e("xxx: ", type[i] + "");
                    if(!city1.equals("المحافظة")){
                       Searchcity=1;
                       if(city1.equals(country[i])){
                           out.add(i);
                       }
                    }

                    if (typed != 0) {
                        entertype = 1;
                        if (typed == Integer.parseInt(type[i])) {
                            out.add(i);
                            // input = 1;
                        }
                    }
                    if (stutesd != 0) {
                        enterstutes = 1;
                        if (stutesd == Integer.parseInt(stutes[i])) {
                            out.add(i);
                            //  input1 = 1;
                        }
                    }
                    if (p1 != 0.0&&p2!=0.0) {
                        enterprice = 1;
                        if (p1 <= Double.parseDouble(kind1[i])&&Double.parseDouble(kind1[i]) <= p2) {
                            out.add(i);
                            //  input1 = 1;
                        }
                    }
                    if (a1 != 0.0&&a2!=0.0) {
                        enterarea = 1;
                        if (a1 <= Double.parseDouble(space[i])&&Double.parseDouble(space[i]) <= a2) {
                            out.add(i);
                            //  input1 = 1;
                        }
                    }
                }
                System.out.println(out);

                int enter = enterstutes + entertype+enterarea+enterprice+Searchcity;

                if (enter > 1) {
                    for (int i = 0; i < out.size(); i++) {
                        for (int j = 0; j < out.size(); j++) {
                            if (out.get(i) == out.get(j)) {
                                print++;
                                // out.remove(out.get(j));
                            }
                        }

                        if (print == enter) {
                            out1.add(out.get(i));
                            //  System.out.println(out1);
                            out.remove(out.get(i));


                        }
                        print = 0;

                    }

                } else if (enter == 1) {
                    for (int i = 0; i < out.size(); i++) {
                        out1.add(out.get(i));
                    }
                }else{
                    for (int i = 0; i < type.length; i++) {
                        out1.add(i);
                    }
                }
                System.out.println(out1);
                Log.e("x: ", out1.indexOf(1) + "");
                if(!out1.isEmpty()){
                kindn = new String[out1.size()];
                kind1n = new String[out1.size()];//price
                kind3n = new String[out1.size()];//First Image
                kind4n = new String[out1.size()];//ID
                kind5n = new String[out1.size()];//Images
                stutesn = new String[out1.size()];
                latn = new String[out1.size()];
                langn = new String[out1.size()];
                roomsn = new String[out1.size()];
                floorsn = new String[out1.size()];
                spacen = new String[out1.size()];
                addresn = new String[out1.size()];
                typen = new String[out1.size()];
                cityn = new String[out1.size()];
                countryn = new String[out1.size()];

                for (int i = 0; i < out1.size(); i++) {
                    kindn[i] = kind[out1.get(i)];
                    kind1n[i] = kind1[out1.get(i)];//price
                    kind3n[i] = kind3[out1.get(i)];//First Image
                    kind4n[i] = kind4[out1.get(i)];//ID
                    kind5n[i] = kind5[out1.get(i)];//Images
                    stutesn[i] = stutes[out1.get(i)];
                    latn[i] = lat[out1.get(i)];
                    langn[i] = lang[out1.get(i)];
                    roomsn[i] = rooms[out1.get(i)];
                    floorsn[i] = floors[out1.get(i)];
                    spacen[i] = space[out1.get(i)];
                    addresn[i] = addres[out1.get(i)];
                    typen[i] = type[out1.get(i)];
                    cityn[i] = city[out1.get(i)];
                    countryn[i] = country[out1.get(i)];
                }
                    advert_list.setVisibility(View.VISIBLE);

                    notfound.setVisibility(View.GONE);
                    advert_list.setAdapter(new advert_list(kindn, kind1n, kind3n, kind4n, kind5n, stutesn, latn, langn, roomsn, floorsn, spacen, addresn, typen
                            , cityn, countryn, SearchResult.this));


                }else{
                    advert_list.setVisibility(View.GONE);

                    notfound.setVisibility(View.VISIBLE);
                }

                out.clear();
                out1.clear();
                stutesd=0;
                typed=0;
                Searchcity=0;
                a1 = 0.0; a2 = 0.0; p1 = 0.0; p2 = 0.0;
             /*   if (stutesd == 0) {
                    Toast.makeText(getApplicationContext(), "من فضلك ادخل نوع التعامل", Toast.LENGTH_LONG).show();

                }
                if (!TextUtils.isEmpty((CharSequence) salaryFrom)) {

                }
                if (!TextUtils.isEmpty((CharSequence) salaryTo)) {

                }
                if (!TextUtils.isEmpty((CharSequence) areaFrom)) {

                }
                if (!TextUtils.isEmpty((CharSequence) areaTo)) {

                }*/
                dialog.dismiss();
            }
        });

        dialog.show();
        Display display = ((WindowManager) getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        Log.v("width", width + "");
        //dialog.getWindow().setLayout((6*width)/6,(3*height)/6);
        dialog.getWindow().setLayout((6 * width) / 6, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    public void advert(RequestParams params) throws JSONException {

        AsyncHttpClient.post("alladvert.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(SearchResult.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التحميل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {

                    if (response.getInt("code1") == 1) {
                        String[] items1 = response.getString("message1").split("#");
                        System.out.println(items1[0] + "???????");
                        String[] items2 = new String[10];
                        String[][] out = new String[items1.length][13];
                        for (int i = 0; i < items1.length; i++) {
                            items2 = items1[i].split("    ");
                            for (int j = 0; j < 13; j++) {
                                out[i][j] = items2[j];
                            }
                        }
                        kind = new String[items1.length];
                        kind1 = new String[items1.length];//price
                        kind3 = new String[items1.length];//First Image
                        kind4 = new String[items1.length];//ID
                        kind5 = new String[items1.length];//Images
                        stutes = new String[items1.length];
                        lat = new String[items1.length];
                        lang = new String[items1.length];
                        rooms = new String[items1.length];
                        floors = new String[items1.length];
                        space = new String[items1.length];
                        addres = new String[items1.length];
                        type = new String[items1.length];
                        city = new String[items1.length];
                        country = new String[items1.length];
                        for (int i = 0; i < items1.length; i++) {
                            if (out[i][0].equals("1")) {
                                kind[i] = "شقة";
                            } else if (out[i][0].equals("2")) {
                                kind[i] = "محل";
                            } else if (out[i][0].equals("3")) {
                                kind[i] = "عقار";
                            } else if (out[i][0].equals("4")) {
                                kind[i] = "اراضى";
                            }
                            type[i] = out[i][0];
                            city[i] = out[i][11];
                            country[i] = out[i][12];
                            stutes[i] = out[i][4];
                            lat[i] = out[i][8];
                            lang[i] = out[i][9];
                            rooms[i] = out[i][2];
                            floors[i] = out[i][3];
                            space[i] = out[i][1];
                            kind4[i] = out[i][10];
                            kind1[i] = out[i][6];
                            kind5[i] = out[i][7];
                            addres[i] = out[i][5];
                            String oneImage[] = out[i][7].split(",");
                            kind3[i] = oneImage[0];

                            System.out.println(kind3[i] + "???????");
                        }
                        advert_list.setAdapter(new advert_list(kind, kind1, kind3, kind4, kind5, stutes, lat, lang, rooms, floors, space, addres, type
                                , city, country, SearchResult.this));
                        //  advert_list.setVisibility(View.VISIBLE);

                    } else if (response.getString("choose").equals("1")) {
                        //  error.setVisibility(View.VISIBLE);

                    }
                    ///////////////////////////////////////////////////////////////////////////////////


                    // Toast.makeText(getApplicationContext(),items[0],Toast.LENGTH_LONG).show();


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
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}


