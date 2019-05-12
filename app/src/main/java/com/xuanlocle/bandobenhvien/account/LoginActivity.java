package com.xuanlocle.bandobenhvien.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuanlocle.bandobenhvien.MainActivity;
import com.xuanlocle.bandobenhvien.R;

public class LoginActivity extends AppCompatActivity {

    Button btnDangNhap,btnDangNhapFacebook, btnDangNhapBangTheBaoHiem;
    EditText edtSoDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            }
        });


    }
}
