package com.aqaralatheer.LoginRegister;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aqaralatheer.Edit.Edit;
import com.aqaralatheer.MapsActivity;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.Picker.SearchResult;
import com.aqaralatheer.R;

public class LoginRegActivity
        extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String msg = "Android : ";
    LoginFragment login;
    RegisterFragment register;
    Button go;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    String Go="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);
      Typeface  button = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Bold.ttf");
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
                Intent i = new Intent(LoginRegActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });
        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginRegActivity.this, LoginRegActivity.class);
                i.putExtra("Go","8");
                startActivity(i);
            }
        });
        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginRegActivity.this, SearchResult.class);
                startActivity(i);
            }
        });
        main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginRegActivity.this, Edit.class);
                startActivity(i);
            }
        });
        main4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginRegActivity.this, Add_advert.class);
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
                Intent i = new Intent(LoginRegActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("UserId", "");
        if (id.equals("")) {
            View x=(View)findViewById(R.id.main3line);
            View x1=(View)findViewById(R.id.main5line);
            x.setVisibility(View.GONE);
            x1.setVisibility(View.GONE);

            main3.setVisibility(View.GONE);
            main5.setVisibility(View.GONE);
        } else {

        }


        //////////////////////////////////
        login = new LoginFragment();
        register = new RegisterFragment();

        Go= getIntent().getExtras().getString("Go");
        if(Go.equals("1")||Go.equals("8")){

            fragmentTransaction.add(R.id.fragment_container, register).commit();

        }else {
        fragmentTransaction.add(R.id.fragment_container, login).commit();
    }
        //////////////////////////////////////////////////

    }

    @Override
    public void onBackPressed() {
        SharedPreferences pref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        String id = pref.getString("Back", "");
        if(id.equals("1")){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, login)
                .addToBackStack(null).commit();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Back", "0");
            editor.commit();}
        else if(Go.equals("1")){
            Intent i = new Intent(LoginRegActivity.this, Edit.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(LoginRegActivity.this, MapsActivity.class);
            startActivity(i);
        }

        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}


