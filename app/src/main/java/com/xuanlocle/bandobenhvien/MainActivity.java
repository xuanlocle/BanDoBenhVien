package com.xuanlocle.bandobenhvien;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

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

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TrangChuFragment trangChuFragment;
    private CaNhanFragment caNhanFragment;
    private DangKyKhamHomeFragment dangKyKhamHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        trangChuFragment = new TrangChuFragment();
        //anh xa
//        mainScrollView = (ScrollView)findViewById(R.id.scrollvmain);
        dangKyKhamHomeFragment = new DangKyKhamHomeFragment();
        caNhanFragment = new CaNhanFragment();


////        mainScrollView.fullScroll(ScrollView.FOCUSABLES_ALL);
//        mainScrollView.setFocusableInTouchMode(true);
//        mainScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
//        String URL = "http://210.245.111.217:480/api/values?id=1";
//        GetJSONFromServer(URL);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Trang chủ");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_home_white_18, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Đăng ký khám");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_assignment_white_18, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Bản đồ");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_place_white_18, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Cá nhân");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_face_white_18, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(trangChuFragment, "Trang chủ");
        adapter.addFragment(dangKyKhamHomeFragment, "Đăng ký khám");
        adapter.addFragment(new TrangChuFragment(), "Bản đồ");
        adapter.addFragment(caNhanFragment, "Cá nhân");
        viewPager.setAdapter(adapter);
    }


}
