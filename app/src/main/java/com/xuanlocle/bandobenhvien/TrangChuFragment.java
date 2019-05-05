package com.xuanlocle.bandobenhvien;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrangChuFragment extends Fragment {

    public TrangChuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    View inflatedView = null;
    TextView tv = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.fragment_trangchu, container, false);

        tv = (TextView) inflatedView.findViewById(R.id.tvGet);

        if(tv == null)
        {
            Log.d("debugCheck", "HeadFrag: sendButton is null");
            return inflatedView;
        }

        return inflatedView;
    }

    public String getTextView(){
        return tv.getText().toString();
    }

    public void setTextView(String text) {
        tv.setText(text);
        return;
    }
}
