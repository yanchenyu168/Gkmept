package com.nasserver.gkmept.FragmentShow;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nasserver.gkmept.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private String stringIp="192.168.0.103";
    private String stringUserName="nas";
    private String stringPassword="nas";
    private String stringPath="nas/yan/";

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //region 获取持久化数据
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        stringIp=sharedPreferences.getString("IP","192.168.0.103");
        stringUserName=sharedPreferences.getString("UserName","nas");
        stringPassword=sharedPreferences.getString("Password","nas");
        stringPath=sharedPreferences.getString("Path","/");
        //endregion
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

}
