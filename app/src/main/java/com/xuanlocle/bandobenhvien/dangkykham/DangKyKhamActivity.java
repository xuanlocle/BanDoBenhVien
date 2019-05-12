package com.xuanlocle.bandobenhvien.dangkykham;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.xuanlocle.bandobenhvien.CustomTimePickerDialog;
import com.xuanlocle.bandobenhvien.R;
import com.xuanlocle.bandobenhvien.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DangKyKhamActivity extends Activity {
    private ViewFlipper simpleViewFlipper;
    String TAG = "DangKyKhamActivity";
    Button btnNext;
    String stringAPIPhongKham = "http://210.245.111.217:480/api/values/4";
    String stringAPIBacSi = "http://210.245.111.217:480/api/values/5";
    String stringAPIPostLichKham = "http://210.245.111.217:480/api/values?";
    ArrayList<String> arrListPhongKham, arrListBacSi;
    ArrayAdapter<String> adapterPhongKham, adapterBacSi;
    HashMap<String,String> lstPhongKham, lstBacSi;
    Spinner spinnerPhongKham, spinnerBacSi;
    EditText edtNgayKham,edtGioKham,edtGhiChu;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    Date dateFinish,hourFinish;
    Button btnSendDemo;
    Animation in,out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_dangkykham_theoslide);
        AnhXa();
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

        VolleySingleton.getInstance(this).getRequestQueue().add(GetJSONPhongKhamFromServer(stringAPIPhongKham));
        VolleySingleton.getInstance(this).getRequestQueue().add(GetJSONBacSiFromServer(stringAPIBacSi));

        adapterPhongKham = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrListPhongKham);
        adapterPhongKham.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerPhongKham.setAdapter(adapterPhongKham);
        adapterPhongKham.notifyDataSetChanged();
        
        adapterBacSi = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrListBacSi);
        adapterBacSi.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerBacSi.setAdapter(adapterBacSi);
        adapterBacSi.notifyDataSetChanged();
        
        
        
        spinnerPhongKham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinnerPhongKham item
//                String item = arrListPhongKham.get(position);
//                String pkid = lstPhongKham.get(item);
                // Showing selected spinnerPhongKham item
//                Toast.makeText(getApplicationContext(),
//                        "phòng khám đã chọn : " + item + " - id : " +pkid, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerBacSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinnerPhongKham item
//                String item = arrListPhongKham.get(position);
//                String pkid = lstPhongKham.get(item);
                // Showing selected spinnerPhongKham item
//                Toast.makeText(getApplicationContext(),
//                        "phòng khám đã chọn : " + item + " - id : " +pkid, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                String temp = "ID=1&idthietbi=324561&token=ngỳasdf&MaHoSo=1986&NgayKham=20190101&GioKham=0220&IDPhongKham=1&MaBacSi=dfdf&GhiChu=fdfd";
                String idPhongKham = lstPhongKham.get(arrListPhongKham.get(spinnerPhongKham.getSelectedItemPosition()));
                String idBacSi = lstBacSi.get(arrListBacSi.get(spinnerBacSi.getSelectedItemPosition()));
                String maHoSo = "1986";
                String _ngayKham = edtNgayKham.getTag().toString();
                String _gioKham = edtGioKham.getTag().toString().replace(":","");
                String _ghiChu = edtGhiChu.getText().toString()+".";
//                Toast.makeText(getApplicationContext(), "idPhong: "+idPhongKham, Toast.LENGTH_SHORT).show();
//                stringAPIPostLichKham =

                String url = stringAPIPostLichKham+"ID=1&idthietbi=324561&token=ngỳasdf&MaHoSo="+maHoSo+"&NgayKham="+_ngayKham+"&GioKham="+_gioKham+"&IDPhongKham="+idPhongKham
                        +"&MaBacSi="+idBacSi+"&GhiChu="+_ghiChu;
                VolleySingleton.getInstance(DangKyKhamActivity.this).getRequestQueue().add(SendStrongRequest(url));
//                SendJSONPhongKhamToServer(stringAPIPostLichKham,getApplicationContext(),maHoSo,idPhongKham,_ngayKham,_gioKham,_ghiChu);
            }
        });

    }

    private void AnhXa() {

        btnNext = (Button) findViewById(R.id.buttonNext);
        btnSendDemo = (Button)findViewById(R.id.btnSendDemo);
        edtNgayKham= (EditText) findViewById(R.id.edtNgayKham);
        edtGioKham = (EditText) findViewById(R.id.edtGioKham);
        edtGhiChu = (EditText)findViewById(R.id.edtGhiChu);
        simpleViewFlipper = (ViewFlipper) findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        // Declare in and out animations and load them using AnimationUtils class
        in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        spinnerPhongKham = (Spinner)findViewById(R.id.spinnerPhongKham);
        spinnerBacSi = (Spinner)findViewById(R.id.spinnerBacSi);
        // set the animation type to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        getDefaultInfor();
        lstPhongKham = new HashMap<>();
        lstBacSi = new HashMap<>();
        lstPhongKham.put("---Chọn phòng khám---","-1");
        lstBacSi.put("---Chọn bác sĩ---","-1");
        arrListPhongKham = new ArrayList<String>();
        arrListBacSi = new ArrayList<String>();
        arrListPhongKham.add("---Chọn phòng khám---");
        arrListBacSi.add("---Chọn bác sĩ---");


    }


    public StringRequest SendStrongRequest(final String url){
//        String url = "http://example.com/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                Toast.makeText(DangKyKhamActivity.this, response.substring(response.indexOf(':')+2,response.indexOf(',')-1), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "url: " + url);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("para1", "value1");
                params.put("para1", "value2");
                return params;
            }
        };
        return stringRequest;
    }
//    public void SendJSONPhongKhamToServer(String URL, Context context,String maBenhNhan, String idPhongKham, String NgayKham,String GioKham,String GhiChu){
//        RequestQueue queue = Volley.newRequestQueue(this);
////        showProgressDialog();
////        Map<String, String> postParam= new HashMap<String, String>();
////        postParam.put("MaBenhNhan", maBenhNhan);
////        postParam.put("IDPhongKham", idPhongKham);
////        postParam.put("NgayKham",NgayKham);
////        postParam.put("GioKham",GioKham);
////        postParam.put("GhiChu",GhiChu);
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                URL, new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//                        //msgResponse.setText(response.toString());
//                        //hideProgressDialog();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//               // hideProgressDialog();
//            }
//        }) {
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//
//
//
//        };
//        jsonObjReq.setTag(TAG);
//        // Adding request to request queue
//        queue.add(jsonObjReq);
//
//
//
//
//        }

    public  JsonArrayRequest GetJSONPhongKhamFromServer(String URL){
        JsonArrayRequest jarr = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject job = response.getJSONObject(i);
                        String name = job.getString("TenPhongKham");
                        String id = (job.getString("IDPhongKham"));
                        lstPhongKham.put(name,id);
                        arrListPhongKham.add(name);
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
        return jarr;
    }

    public  JsonArrayRequest GetJSONBacSiFromServer(String URL){
        JsonArrayRequest jarr = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject job = response.getJSONObject(i);
                        String name = job.getString("TenBacSi");
                        String id = (job.getString("MaBacSi"));
//                        String nameShort = (job.getString("TenTat"));
                        lstBacSi.put(name,id);
                        arrListBacSi.add(name);
//                      trangChuFragment.setTextView(trangChuFragment.getTextView()+"\n"+name);
//                      Toast.makeText(MainActivity.this, name , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Log.d("Lấy list bác sĩ","Error"+e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        return jarr;
    }

    /**
     * Hàm lấy các thông số mặc định khi lần đầu tiền chạy ứng dụng
     */
    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        calendar=Calendar.getInstance();
        SimpleDateFormat dft=null;
        SimpleDateFormat dftTag=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        dftTag =new SimpleDateFormat("yyyyMMdd",Locale.getDefault());
        String strDate=dft.format(calendar.getTime());
        String strDateTag=dftTag.format(calendar.getTime());
        //hiển thị lên giao diện
        edtNgayKham.setText(strDate);
        edtNgayKham.setTag(strDateTag);
        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
//        dftTag=new SimpleDateFormat("hhmm",Locale.getDefault());
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
                String s = (hourOfDay<10?"0"+hourOfDay:hourOfDay) + ":" + (minute<10?"0"+minute:minute);
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
                int m = monthOfYear+1;
                int d = dayOfMonth;
                edtNgayKham.setTag(year+""+(m<10?"0"+m:m)+""+(d<10?"0"+d:d));
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