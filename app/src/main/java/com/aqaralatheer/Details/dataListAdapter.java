package com.aqaralatheer.Details;

/**
 * Created by elnemr on 8/8/16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aqaralatheer.R;

/**
 * Created by VGA! on 04/08/2016.
 */
class dataListAdapter extends BaseAdapter {
    String[] data;
    String[] data1;
    Context context;
    LayoutInflater layoutInflater;
    TextView txt, info;
    Typeface typetxt, typeinfo;


    public dataListAdapter(String[] data, String[] data1, Context context) {
        super();
        this.data1 = data1;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item, null);
        txt = (TextView) convertView.findViewById(R.id.list_item_text);
        info = (TextView) convertView.findViewById(R.id.list_item_text1);
       /* int img[] = {R.drawable.home, R.drawable.home, R.drawable.home, R.drawable.home, R.drawable.phone,
                R.drawable.home,R.drawable.home,R.drawable.home, R.drawable.home};
        txt.setCompoundDrawablesWithIntrinsicBounds(0,0,img[position], 0); //icon with text .*/
        txt.setText(data[position]);
        info.setText(data1[position]);
        // txt.setTextColor(position % 2 == 0 ? Color.rgb(226 ,77,140): Color.WHITE);//two color font
        convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.withee : R.drawable.withet);//two color
        //change text family
        typetxt = Typeface.createFromAsset(context.getAssets(), "fonts/DroidKufi-Bold.ttf");
        typeinfo = Typeface.createFromAsset(context.getAssets(), "fonts/DroidKufi-Bold.ttf");
        txt.setTypeface(typetxt);
        info.setTypeface(typeinfo);


        return convertView;
    }


}