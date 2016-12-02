package com.aqaralatheer.Picker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqaralatheer.Details.DetailMainAct;
import com.aqaralatheer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by ahmed on 4/24/2016.
 */
public class advert_list extends BaseAdapter {

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

    public advert_list(String[] data, String[] data1, String Image[], String id[], String Images[]
            , String[] stutes, String[] lat, String[] lang, String[] rooms, String[] floors, String[] space, String[] addres, String[] type, String[] city, String[] country
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

        convertView = layoutInflater.inflate(R.layout.searchadvertrow, null);

        TextView name = (TextView) convertView.findViewById(R.id.kindd);
        TextView name1 = (TextView) convertView.findViewById(R.id.priced);
        //  ImageView img = (ImageView) convertView.findViewById(R.id.img);
        delet = (TextView) convertView.findViewById(R.id.aread);
        fix = (TextView) convertView.findViewById(R.id.govd);

        name.setText(data[position]);
        name1.setText(data1[position] + " " + "جنية");
        delet.setText(space[position] + " " + "متر");
        fix.setText(city[position] + " - " + country[position]);
        // Picasso.with(context).load("http://aqarak.esy.es/" + Image[position]).into(img);
        ProgressBar progressBar = null;

        progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter( context.getResources().getColor(R.color.highlight), android.graphics.PorterDuff.Mode.MULTIPLY);

//get your image view
        ImageView myImage = (ImageView) convertView.findViewById(R.id.myImageView);

//load the image url with a callback to a callback method/class
        Picasso.with(context)
                .load("http://android.alatheertech.com/" + Image[position])
                .into(myImage, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {
                        if (this.progressBar != null) {
                            this.progressBar.setVisibility(View.GONE);
                        }
                    }
                });


        convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.withee : R.drawable.withet);//two color
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailMainAct.class);
                i.putExtra("ID", id[position]);
                i.putExtra("lat", Double.parseDouble(lat[position]));
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
