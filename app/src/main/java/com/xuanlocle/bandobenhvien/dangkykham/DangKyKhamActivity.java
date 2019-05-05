package com.xuanlocle.bandobenhvien.dangkykham;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.xuanlocle.bandobenhvien.CustomTimePickerDialog;
import com.xuanlocle.bandobenhvien.R;
import com.xuanlocle.bandobenhvien.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DangKyKhamActivity extends Activity {
    private ViewFlipper simpleViewFlipper;
    String TAG = "DangKyKhamActivity";
    Button btnNext;
    String stringAPIPhongKham = "http://210.245.111.217:480/api/values/4";
    ArrayList<String> arrList;
    ArrayAdapter<String> arrayAdapter;
    HashMap<String,String> lstPhongKham;
    Spinner spinner;
    EditText edtNgayKham,edtGioKham;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    Date dateFinish,hourFinish;
    Button btnSendDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_dangkykham_theoslide);
        // get The references of Button and ViewFlipper
        btnNext = (Button) findViewById(R.id.buttonNext);
        btnSendDemo = (Button)findViewById(R.id.btnSendDemo);
        edtNgayKham= (EditText) findViewById(R.id.edtNgayKham);
        edtGioKham = (EditText) findViewById(R.id.edtGioKham);
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        spinner = (Spinner)findViewById(R.id.spinnerPhongKham);
        // set the animation type to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);

        // ClickListener for NEXT button
        // When clicked on Button ViewFlipper will switch between views
        // The current view will go out and next view will come in with specified animation
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                // show the next view of ViewFlipper
                simpleViewFlipper.showNext();
            }
        });
        getDefaultInfor();
        lstPhongKham = new HashMap<>();
        lstPhongKham.put("---Chọn phòng khám---","-1");
        arrList = new ArrayList<String>();
        arrList.add("---Chọn phòng khám---");
        GetJSONPhongKhamFromServer(stringAPIPhongKham, getApplicationContext());

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrList);
        arrayAdapter .setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                String item = arrList.get(position);
                String pkid = lstPhongKham.get(item);
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(),
                        "phòng khám đã chọn : " + item + " - id : " +pkid, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        final int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);

        edtNgayKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDatePickerDialog();
            }
        });
        edtGioKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        btnSendDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SendJSONPhongKhamToServer();
            }
        });

    }

    public void SendJSONPhongKhamToServer(String URL, Context context,String maBenhNhan, String idPhongKham, String NgayKham,String GioKham){
        RequestQueue queue = Volley.newRequestQueue(this);
//        showProgressDialog();
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("MaBenhNhan", maBenhNhan);
        postParam.put("IDPhongKham", idPhongKham);
        postParam.put("NgayKham",NgayKham);
        postParam.put("GioKham",GioKham);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        //msgResponse.setText(response.toString());
                        //hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               // hideProgressDialog();
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };
        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);
        }

    public  void GetJSONPhongKhamFromServer(String URL, Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
//        final HashMap<Byte,String> lstPhongKham = new HashMap<Byte,String>();

//        final ArrayList<String> lstPhongBan = new ArrayList<String>();
        JsonArrayRequest jarr = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{

                    for(int i=0;i<response.length();i++){
                        JSONObject job = response.getJSONObject(i);
                        String name = job.getString("TenPhongKham");
                        String id = (job.getString("IDPhongKham"));
                        lstPhongKham.put(name,id);
                        arrList.add(name);
//                      trangChuFragment.setTextView(trangChuFragment.getTextView()+"\n"+name);
//                      Toast.makeText(MainActivity.this, name , Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e){
                    Log.d("Lấy list phòng khám","Error"+e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        queue.add(jarr);
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        try {
//            JSONObject response = future.get();
//            return lstPhongKham;
//            // do something with response
//        } catch (InterruptedException e) {
//            // handle the error
//        } catch (ExecutionException e) {
//            // handle the error
//        }

        ;
    }
    /**
     * Hàm lấy các thông số mặc định khi lần đầu tiền chạy ứng dụng
     */
    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        calendar=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        String strDate=dft.format(calendar.getTime());
        //hiển thị lên giao diện
        edtNgayKham.setText(strDate);
        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(calendar.getTime());
        //đưa lên giao diện
        edtGioKham.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        edtGioKham.setTag(dft.format(calendar.getTime()));

        //editCv.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=calendar.getTime();
        hourFinish=calendar.getTime();
    }

    /**
     * Hàm hiển thị TimePickerDialog
     */
    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                String hourHienThi = hourTam<10?("0"+hourTam):hourTam+"";
                String minutesHienThi = minute<10?("0"+minute):minute+"";
                edtGioKham.setText
                        (hourHienThi + ":" + minutesHienThi + (hourOfDay > 12 ? " PM" : " AM"));
                //lưu giờ thực vào tag
                edtGioKham.setTag(s);
                //lưu vết lại giờ vào hourFinish
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                hourFinish = calendar.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = edtGioKham.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        CustomTimePickerDialog time = new CustomTimePickerDialog(DangKyKhamActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }/**
     * Hàm hiển thị DatePicker dialog
     */
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                edtNgayKham.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                calendar.set(year, monthOfYear, dayOfMonth);
                dateFinish=calendar.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=edtNgayKham.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                DangKyKhamActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }
}