package com.xuanlocle.bandobenhvien;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xuanlocle.bandobenhvien.dangkykham.DangKyKhamActivity;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TrangChuFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TrangChuFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DangKyKhamHomeFragment extends Fragment implements View.OnClickListener {
    public DangKyKhamHomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_dangkykham, container, false);

        //tv = (TextView) inflatedView.findViewById(R.id.tvGet);

        Button btn1,btn2;
        btn1 = (Button)inflatedView.findViewById((R.id.btnDangKyKhamMacDinh));
        btn2 = (Button)inflatedView.findViewById((R.id.btnDangKyKhamDichVu));

        btn1.setOnClickListener(this);
//        if(tv == null)
//        {
//            Log.d("debugCheck", "HeadFrag: sendButton is null");
//            return inflatedView;
//        }

        return inflatedView;
    }

    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity)getActivity();
        Intent intent = new Intent(getContext(),DangKyKhamActivity.class);
        startActivity(intent);
    }
}
