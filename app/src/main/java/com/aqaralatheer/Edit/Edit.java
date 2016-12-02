package com.aqaralatheer.Edit;

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
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aqaralatheer.LoginRegister.LoginRegActivity;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.MapsActivity;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.Picker.SearchResult;
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
import java.util.Calendar;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class Edit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CircleImageView imageView_profile;
    boolean addImage = false;
    TextView name, error;
    ListView lv;
    String remove;
    ImageView ed;
    String name1, pass1, phone1, emanil1;
    Button out, advert, change;
    byte[] image;
    int img = 0;
    String id;
    Typeface Font;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);


        Typeface button = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
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
                Intent i = new Intent(Edit.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, LoginRegActivity.class);
                i.putExtra("Go", "8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, Add_advert.class);
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
                Intent i = new Intent(Edit.this, MapsActivity.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        id = pref.getString("UserId", "");
        if (id.equals("")) {
            View x = (View) findViewById(R.id.main3line);
            View x1 = (View) findViewById(R.id.main5line);
            x.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);

            main3.setVisibility(View.GONE);
            main5.setVisibility(View.GONE);
        } else {

        }

        try {

            RequestParams params = new RequestParams();
            params.put("Choose", "1");
            params.put("UserId", id);
            // params.put("image", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");

            EditData(params);

            //      Toast.makeText(getApplicationContext(), searchlang + "" + searchlat, Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
        }
        lv = (ListView) findViewById(R.id.profile_list);
        name = (TextView) findViewById(R.id.profile_name);

        out = (Button) findViewById(R.id.out);
        advert = (Button) findViewById(R.id.advert);
        change = (Button) findViewById(R.id.change);
        out.setTextSize(12);
        advert.setTextSize(12);
        change.setTextSize(12);
        error = (TextView) findViewById(R.id.Add);
        Font = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
        name.setTypeface(Font);

        error.setTypeface(Font);
        out.setTypeface(Font);
        advert.setTypeface(Font);
        change.setTypeface(Font);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
                //
                // SharedPreferences.Editor editor = sharedPref.edit();
                sharedPref.edit().remove("UserId").commit();
                // editor.commit();
                Intent i = new Intent(Edit.this, MapsActivity.class);
                startActivity(i);
            }
        });

        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, Add_advert.class);
                i.putExtra("add", 1);
                i.putExtra("map", 0);
                startActivity(i);
            }
        });
        imageView_profile = (CircleImageView) findViewById(R.id.profile_image);
        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseImage();
                img = 1;
            }
        });
        ed = (ImageView) findViewById(R.id.edit);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, LoginRegActivity.class);
                i.putExtra("name1", name1);
                i.putExtra("phone1", phone1);
                i.putExtra("email1", emanil1);
                i.putExtra("pass1", pass1);
                i.putExtra("Go", "1");
                startActivity(i);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
              /*  SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Go", "1");
                editor.commit();*/
                Intent i = new Intent(Edit.this, MapsActivity.class);
                // i.putExtra("Go","1");
                startActivity(i);
            }
        });
        advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, Add_advert.class);
                i.putExtra("add", 1);
                i.putExtra("map", 0);
                startActivity(i);
            }
        });


    }

    private void openChooseImage() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog);

        TextView imgtext = (TextView) dialog.findViewById(R.id.imgtext);
        // dialog.setTitle("اختر صورتك");
        imgtext.setTypeface(Font);

        // dialog.setTitle("اختر صورة");

        Button mChooseFromSDBtn = (Button) dialog.findViewById(R.id.button_Chooes);
        Button mCaptureBtn = (Button) dialog.findViewById(R.id.button_Capture);
        mChooseFromSDBtn.setTypeface(Font);
        mCaptureBtn.setTypeface(Font);
        mChooseFromSDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                intent_gallery.setType("image*//*");
                startActivityForResult(intent_gallery, 1);
                dialog.dismiss();*/

                if (Build.VERSION.SDK_INT >= 23){
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(Edit.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Edit.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(Edit.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1
                            );

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }else{
                        ActivityCompat.requestPermissions(Edit.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                }else {

                    Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                    intent_gallery.setType("image/*");
                    startActivityForResult(intent_gallery, 1);
                    dialog.dismiss();
                }


            }
        });

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);


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
                    imageView_profile.setImageBitmap(yourSelectedImage);
                    drawable = imageView_profile.getDrawable();
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    image = stream.toByteArray();
                    addImage = true;
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

                    imageView_profile.setImageBitmap(yourSelectedImage);
                    drawable = imageView_profile.getDrawable();
                    bitmap = ((BitmapDrawable) drawable).getBitmap();
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    image = stream.toByteArray();
                    // Picasso.with(this).load(filePath).into(imageView_profile);
                    addImage = true;
                }

        }
        if (image != null) {
            try {

                RequestParams params = new RequestParams();
                params.put("Choose", "3");
                params.put("UserId", id);
                params.put("image", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");
                params.put("remove", remove);
                Log.e("onActivityResult: ", id + remove);
                EditData(params);


            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void EditData(RequestParams params) throws JSONException {

        AsyncHttpClient.post("Edit.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(Edit.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى التحميل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getString("choose").equals("1") && response.getInt("code") == 1) {
                        String[] items = response.getString("message").split(" ");

                        name.setText(items[2]);
                        remove = items[3];
                        if (!(items[3].equals("not"))) {
                            //  Picasso.with(getBaseContext()).load("http://aqarak.esy.es/" + items[3]).into(imageView_profile);
                            ProgressBar progressBar = null;

                            progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.getIndeterminateDrawable().setColorFilter(getApplicationContext().getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view


//load the image url with a callback to a callback method/class
                            Picasso.with(getBaseContext())
                                    .load("http://android.alatheertech.com/" + items[3])
                                    .into(imageView_profile, new ImageLoadedCallback(progressBar) {
                                        @Override
                                        public void onSuccess() {
                                            if (this.progressBar != null) {
                                                this.progressBar.setVisibility(View.GONE);


                                            }
                                        }
                                    });
                        }
                        name1 = items[2];
                        phone1 = items[1];
                        emanil1 = items[0];
                        pass1 = items[4];
                        //phone.setText(items[2]);
                        //email.setText(items[1]);


                    } else if (response.getInt("code") == 1 && response.getString("choose").equals("3")) {
                        remove = response.getString("message");
                        Toast.makeText(getApplicationContext(), "تم تغير الصورة بنجاح", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "خطا فى تحميل البيانات ", Toast.LENGTH_LONG).show();

                    }


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
                        String[] kind = new String[items1.length];
                        String[] kind1 = new String[items1.length];//price
                        String[] kind3 = new String[items1.length];//First Image
                        String[] kind4 = new String[items1.length];//ID
                        String[] kind5 = new String[items1.length];//Images
                        String[] stutes = new String[items1.length];
                        String[] lat = new String[items1.length];
                        String[] lang = new String[items1.length];
                        String[] rooms = new String[items1.length];
                        String[] floors = new String[items1.length];
                        String[] space = new String[items1.length];
                        String[] addres = new String[items1.length];
                        String[] type = new String[items1.length];
                        String[] city = new String[items1.length];
                        String[] country = new String[items1.length];
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
                        lv.setAdapter(new list_trailer(kind, kind1, kind3, kind4, kind5, stutes, lat, lang, rooms, floors, space, addres, type
                                , city, country, Edit.this));
                        lv.setVisibility(View.VISIBLE);

                    } else if (response.getString("choose").equals("1")) {
                        error.setVisibility(View.VISIBLE);

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
             //   Log.e("onFailure", "----------" + responseString);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });


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

    @Override
    public void onBackPressed() {


        Intent i = new Intent(Edit.this, MapsActivity.class);
        startActivity(i);
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
