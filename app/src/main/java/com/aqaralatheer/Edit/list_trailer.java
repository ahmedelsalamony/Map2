package com.aqaralatheer.Edit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aqaralatheer.Details.DetailMainAct;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ahmed on 4/24/2016.
 */
public class list_trailer extends BaseAdapter {

    String[] data;
    String[] data1;
    String[] Image;
    String[] id;
    String Images[];
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
    TextView delet, fix;
    Context context;
    LayoutInflater layoutInflater;

    public list_trailer(String[] data, String[] data1, String Image[], String id[], String Images[]
            , String[] stutes, String[] lat, String[] lang, String[] rooms, String[] floors, String[] space, String[] addres,String[] type,String[] city, String[] country
            , Context context) {
        super();
        this.data1 = data1;
        this.data = data;
        this.Image = Image;
        this.id = id;
        this.Images = Images;
        this.stutes = stutes;
        this.lat = lat;
        this.lang = lang;
        this.rooms = rooms;
        this.floors = floors;
        this.space = space;
        this.addres = addres;

        this.type = type;
        this.city = city;
        this.country = country;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.trailer_row, null);

        TextView name = (TextView) convertView.findViewById(R.id.video_name);
        TextView name1 = (TextView) convertView.findViewById(R.id.video_name1);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        delet = (TextView) convertView.findViewById(R.id.delet);
        fix = (TextView) convertView.findViewById(R.id.fixed);

        name.setText(data[position]);
        name1.setText(data1[position] +" "+ "جنية");
       // Picasso.with(context).load("http://aqarak.esy.es/" + Image[position]).placeholder(R.drawable.process).into(img);
        ProgressBar progressBar = null;

        progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter( context.getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view


//load the image url with a callback to a callback method/class
        Picasso.with(context)
                .load("http://android.alatheertech.com/" + Image[position])
                .into(img, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {
                        if (this.progressBar != null) {
                            this.progressBar.setVisibility(View.GONE);
                        }
                    }
                });


        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Add_advert.class);
                i.putExtra("add", 2);
                i.putExtra("map",0);
                i.putExtra("type", type[position]);
                i.putExtra("price", data1[position]);
                i.putExtra("advertid", id[position]);
                i.putExtra("Images", Images[position]);
                i.putExtra("stutes", stutes[position]);
                i.putExtra("lat",Double.parseDouble( lat[position]));
                i.putExtra("lang",Double.parseDouble( lang[position]));
                i.putExtra("rooms", rooms[position]);
                i.putExtra("floors", floors[position]);
                i.putExtra("space", space[position]);
                i.putExtra("addres", addres[position]);
                i.putExtra("city",country[position]);
                i.putExtra("country",city[position]);
                context.startActivity(i);

            }
        });


        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    RequestParams params = new RequestParams();
                    params.put("Choose", "4");
                    params.put("Id", id[position]);
                    params.put("Images", Images[position]);
                    // params.put("image", new ByteArrayInputStream(image), getCurrentDateAndTime() + ".png");

                    Delet(params);

                    //      Toast.makeText(getApplicationContext(), searchlang + "" + searchlat, Toast.LENGTH_LONG).show();

                } catch (Exception ex) {
                    Toast.makeText(context.getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
                }
            }
        });
        // txt.setTextColor(position % 2 == 0 ? Color.rgb(226 ,77,140): Color.WHITE);//two color font
        convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.withee : R.drawable.withet);//two color
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, DetailMainAct.class);
                i.putExtra("ID",id[position]);
                i.putExtra("lat",Double.parseDouble( lat[position]));
                i.putExtra("lang", Double.parseDouble(lang[position]));
                context.startActivity(i);
            }
        });
        //change text family
        Typeface Font = Typeface.createFromAsset(context.getAssets(), "fonts/DroidKufi-Bold.ttf");

        name.setTypeface(Font);
        name1.setTypeface(Font);
        delet.setTypeface(Font);
        fix.setTypeface(Font);

        return convertView;
    }


    public void Delet(RequestParams params) throws JSONException {

        AsyncHttpClient.post("Edit.php", params, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getInt("code") == 1) {
                        Toast.makeText(context, "تم مسح الاعلان بنجاح", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, Edit.class);
                        context.startActivity(i);

                    } else {

                        Toast.makeText(context, "حاول مرة اخرى", Toast.LENGTH_LONG).show();

                    }
                    // String[] items = response.getString("message").split(",");

                } catch (Exception ex) {

                    Toast.makeText(context, "اشاره النت ضعيفه" + "" + ex, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show();
                Log.e("onFailure", "----------" + responseString);

            }


        });


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
