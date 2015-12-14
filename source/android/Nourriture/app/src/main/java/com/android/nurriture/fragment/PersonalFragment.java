package com.android.nurriture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nourriture.nourriture.LoginActivity;
import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RegistActivity;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener{

    private TextView login_tv,regist_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View homeView = (View)inflater.inflate(R.layout.layout_personal, container,false);
        init(homeView);
        return homeView;
    }

    private void init(View view)
    {
        login_tv = (TextView)view.findViewById(R.id.login_tv);
        regist_tv = (TextView)view.findViewById(R.id.regist_tv);
        login_tv.setOnClickListener(this);
        regist_tv.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_tv:
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.regist_tv:
                Intent intent2 = new Intent(v.getContext(), RegistActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
