package com.xuanlocle.bandobenhvien.account;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.xuanlocle.bandobenhvien.MainActivity;
import com.xuanlocle.bandobenhvien.R;

public class LoginActivity extends AppCompatActivity {

    Button btnDangNhap,btnDangNhapFacebook, btnDangNhapBangTheBaoHiem;
    EditText edtSoDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Activity activity = this;
        btnDangNhap = (Button)findViewById(R.id.btnDangNhap);
        btnDangNhapFacebook = (Button)findViewById(R.id.btnDangNhapFacebook);
        btnDangNhapBangTheBaoHiem = (Button)findViewById(R.id.btnDangNhapBangTheBaoHiem) ;
        edtSoDienThoai = (EditText)findViewById(R.id.edtSoDienThoai);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = edtSoDienThoai.getText().toString().length()>7;
                if(!flag){
                    edtSoDienThoai.setError("Vui lòng nhập số điện thoại chính xác.");
                }
                else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                }
            }
        });

        btnDangNhapFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Chức năng này đang cập nhật!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDangNhapBangTheBaoHiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Quét mã QR trên thẻ bảo hiểm của bạn.");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null)
                Toast.makeText(this, "Không quét được mã!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
