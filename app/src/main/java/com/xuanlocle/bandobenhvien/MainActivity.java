package com.xuanlocle.bandobenhvien;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ScrollView mainScrollView;
    ArrayList<String> lstPhongBan;
    TextView tvGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        //anh xa
        mainScrollView = (ScrollView)findViewById(R.id.scrollvmain);
        tvGet = (TextView)findViewById(R.id.tvGet);
//        mainScrollView.fullScroll(ScrollView.FOCUSABLES_ALL);
        mainScrollView.setFocusableInTouchMode(true);
        mainScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        String URL = "http://210.245.111.217:480/api/values?id=1";
        lstPhongBan = new ArrayList<String>();
        JsonArrayRequest jarr = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i=0;i<response.length();i++){
                        JSONObject job = response.getJSONObject(i);
                        String name = job.getString("TenPhongBan");
                        lstPhongBan.add(name);
                        tvGet.setText(tvGet.getText()+"\n"+name);
                        Toast.makeText(MainActivity.this, name , Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e){


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        queue.add(jarr);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
