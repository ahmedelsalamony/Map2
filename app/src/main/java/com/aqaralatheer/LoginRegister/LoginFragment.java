package com.aqaralatheer.LoginRegister;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
public class LoginFragment extends Fragment {
    View view = null;
    Button go, log;
    EditText name, pass;
    Typeface button;
    TextView t1, or;
    String name1, pass1;
    Context context;


    boolean out = true;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        go = (Button) view.findViewById(R.id.reg);
        log = (Button) view.findViewById(R.id.reg1);
        name = (EditText) view.findViewById(R.id.name);
        pass = (EditText) view.findViewById(R.id.pass);
        t1 = (TextView) view.findViewById(R.id.t1);

        button = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DroidKufi-Bold.ttf");
        go.setTypeface(button);
        log.setTypeface(button);
        name.setTypeface(button);
        pass.setTypeface(button);
        t1.setTypeface(button);

        SharedPreferences pref = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
        String id = pref.getString("name", "");
        String tests = pref.getString("pass", "");
        name.setText(id);
        pass.setText(tests);
      //  Toast.makeText(getActivity().getApplicationContext(), tests + id, Toast.LENGTH_LONG).show();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Back", "1");
                editor.commit();
                RegisterFragment register = new RegisterFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, register).commit();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = name.getText().toString();
                pass1 = pass.getText().toString();
                if (cheak() != false) {

                    try {
                        RequestParams params = new RequestParams();
                        params.put("UserNme", name1);
                        params.put("PassWord", pass1);

                        AskLogin(params);
                    } catch (Exception ex) {
                        Toast.makeText(getActivity().getApplicationContext(), "Exception" + ex, Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
        return view;
    }

    public boolean cheak() {
        name1 = name.getText().toString();
        pass1 = pass.getText().toString();
        if (TextUtils.isEmpty(name1)) {
            name.setError("ادخل قيمة صحيحة");
            name.requestFocus();
            out = false;
        }
        if (TextUtils.isEmpty(pass1)) {
            pass.setError("ادخل قيمة صحيحة");
            pass.requestFocus();
            out = false;
        }

        return out;
    }


    public void AskLogin(RequestParams params) throws JSONException {

        AsyncHttpClient.post("login.php", params, new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("جارى البحث...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.e("onSuccess", response + "");
                try {
                    if (response.getInt("code") == 1) {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("Data",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("name", name1);
                        editor.putString("pass", pass1);
                        editor.putString("UserId",response.getString("message"));
                        editor.commit();
                        Intent intent = new Intent(getActivity().getApplication(), Add_advert.class);
                        intent.putExtra("id", response.getString("message"));
                        intent.putExtra("add", 1);
                        intent.putExtra("map",0);
                        startActivity(intent);
                        getActivity().finish();

                    }
                    else {

                        Toast.makeText(getActivity().getApplicationContext(), "الاسم او كلمة المرور خظأ", Toast.LENGTH_LONG).show();

                    }
                    // String[] items = response.getString("message").split(",");

                } catch (Exception ex) {

                    Toast.makeText(getActivity().getApplicationContext(), "اشاره النت ضغيفه", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity().getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
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





