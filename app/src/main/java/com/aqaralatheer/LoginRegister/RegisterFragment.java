package com.aqaralatheer.LoginRegister;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aqaralatheer.Edit.Edit;
import com.aqaralatheer.LoginRegister.Uitilt.AsyncHttpClient;
import com.aqaralatheer.Picker.Add_advert;
import com.aqaralatheer.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    boolean out=true;
    View view=null;
    TextView t1,t2;
    Button inter,inter1;
    Drawable drawable;
    Typeface button;
    EditText name,pass,phone,email;
    String name1,pass1,phone1,email1;
    public RegisterFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);
        t1=(TextView)view.findViewById(R.id.t1);
        t2=(TextView)view.findViewById(R.id.t11);
        inter=(Button)view.findViewById(R.id.inter);
        inter1=(Button)view.findViewById(R.id.inter1);
        name=(EditText)view.findViewById(R.id.name);
        pass=(EditText)view.findViewById(R.id.pass);
        phone=(EditText)view.findViewById(R.id.phone);
        email =(EditText)view.findViewById(R.id.email);
        button = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DroidKufi-Bold.ttf");
        inter.setTypeface(button);
        inter1.setTypeface(button);
        t2.setTypeface(button);
        phone.setTypeface(button);
        name.setTypeface(button);
        pass.setTypeface(button);
        t1.setTypeface(button);
        email.setTypeface(button);
        if( getActivity().getIntent().getExtras().getString("Go").equals("1")){
            name.setText(getActivity().getIntent().getExtras().getString("name1"));
            email.setText(getActivity().getIntent().getExtras().getString("email1"));
            phone.setText(getActivity().getIntent().getExtras().getString("phone1"));
            pass.setText(getActivity().getIntent().getExtras().getString("pass1"));

            inter1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);

        }else{
            inter.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
        }
        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1=name.getText().toString();
                pass1=pass.getText().toString();
                phone1=phone.getText().toString();
                email1=email.getText().toString();
                if(cheak()!=false){


                    try
                    {
                        RequestParams params = new RequestParams();
                        params.put("choose","2");
                        params.put("UserNme",name1 );
                        params.put("PassWord",pass1);
                        params.put("email",email1 );
                        params.put("phone",phone1);

                        Register(params);
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Exception"+ex,Toast.LENGTH_LONG).show();
                    }}







            }
        });
        inter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name1=name.getText().toString();
                pass1=pass.getText().toString();
                phone1=phone.getText().toString();
                email1=email.getText().toString();
                SharedPreferences pref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
                String id = pref.getString("UserId", "");
                if(cheak()!=false){


                    try
                    {
                        RequestParams params = new RequestParams();
                        params.put("choose","1");

                        params.put("UserNme",name1 );
                        params.put("PassWord",pass1);
                        params.put("email",email1 );
                        params.put("phone",phone1);
                        params.put("UserId",id );

                        Register(params);
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Exception"+ex,Toast.LENGTH_LONG).show();
                    }}







            }
        });
        return view;
    }
    public boolean cheak(){
        name1=name.getText().toString();
        email1=email.getText().toString();
        phone1=phone.getText().toString();
        pass1=pass.getText().toString();
        if(TextUtils.isEmpty(name1)){
            name.setError("ادخل قيمة صحيحة");
            name.requestFocus();
            out= false;
        }
        if(TextUtils.isEmpty(pass1)){
        /* drawable= getResources().getDrawable(R.drawable.error);
            drawable.setBounds(0,0, drawable.getIntrinsicHeight(),
                    drawable.getIntrinsicWidth());*/
            pass.setError("ادخل قيمة صحيحة");

            pass.requestFocus();
            out= false;
        }
        if(TextUtils.isEmpty(email1)||!email1.contains("@")){
            email.setError("ادخل قيمة صحيحة");
            email.requestFocus();
            out= false;
        }
        if(TextUtils.isEmpty(phone1)){
            phone.setError("ادخل قيمة صحيحة");
            phone.requestFocus();
            out= false;
        }


        return out;
    }






    public void Register(RequestParams params) throws JSONException {

        AsyncHttpClient.post("register.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("يتم التسجيل...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getString("choose").equals("2") && response.getInt("code") == 1 )
                    {      Toast.makeText(getActivity().getApplicationContext(), "تم التسجيل نجاح", Toast.LENGTH_LONG).show();

                        /////////////////////////Data/////////////
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("name", name1);
                        editor.putString("pass", pass1);
                        editor.putString("UserId",response.getString("message"));

                        editor.commit();
                        Intent intent = new Intent(getActivity().getApplication(), Add_advert.class);
                        intent.putExtra("id",response.getString("message"));
                        intent.putExtra("add", 1);
                        intent.putExtra("map",0);
                        startActivity(intent);

                    }
                    else if(response.getInt("code") == 0 && response.getString("choose").equals("0"))
                    {

                        Toast.makeText(getActivity().getApplicationContext(), "هذا الايميل موجود بالفعل استخدم اخر.", Toast.LENGTH_LONG).show();

                    }
                    else if (response.getInt("code") == 1 && response.getString("choose").equals("1")){
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("name", name1);
                        editor.putString("pass", pass1);
                        editor.putString("UserId",response.getString("message"));
                        editor.commit();
                        Intent intent = new Intent(getActivity().getApplication(), Edit.class);
                        startActivity(intent);
                        Toast.makeText(getActivity().getApplicationContext(),"تم التعديل بنجاح", Toast.LENGTH_LONG).show();

                    } else if(response.getInt("code") == 0 && response.getString("choose").equals("2"))
                    {

                        Toast.makeText(getActivity().getApplicationContext(), "اعد المحاولة فيما بعد", Toast.LENGTH_LONG).show();

                    }
                    // String[] items = response.getString("message").split(",");

                } catch (Exception ex) {

                    Toast.makeText(getActivity().getApplicationContext(), "اشاره النت ضغيفه" , Toast.LENGTH_LONG).show();

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
}
