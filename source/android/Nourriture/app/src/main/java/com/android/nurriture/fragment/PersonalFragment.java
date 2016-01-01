package com.android.nurriture.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nourriture.nourriture.LoginActivity;
import com.android.nourriture.nourriture.MainActivity;
import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RegistActivity;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener{

    private TextView login_tv,regist_tv,tou_username;
    private ImageView touxiang;
    private LinearLayout mylinear;
    private SharedPreferences sp;
    private Button logout;

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

        tou_username = (TextView)view.findViewById(R.id.tou_username);
        touxiang = (ImageView)view.findViewById(R.id.touxiang);
        mylinear = (LinearLayout)view.findViewById(R.id.personalLinear);
        logout = (Button)view.findViewById(R.id.logout);


        sp = getActivity().getSharedPreferences("userinfo",0);
        String usernamestring = sp.getString("username", null);
        if(usernamestring !=null)
        {
            mylinear.setOrientation(LinearLayout.VERTICAL);
            login_tv.setVisibility(View.GONE);
            regist_tv.setVisibility(View.GONE);
            tou_username.setVisibility(View.VISIBLE);
            touxiang.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);

        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    protected  void logout() {
        HttpUtil connectNet = new HttpUtil(
                "/logout",
                HttpMethod.GET){
            @Override
            protected void getResult(String result) {
                //Toast.makeText(getActivity().getApplicationContext(), "Connect Server API success!=" + result,
                //        Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    String value = jsonObject.getString("value");
                    Log.v("get value", value);

                    if (value.equals("OK") || value == "OK") {
                        sp.edit().clear().commit();
                        //sp.edit().commit();


                        String username = sp.getString("username", null);
                        if(username!=null){
                            Log.v("username",username);
                        }
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        intent.putExtra("currentIndex",3);
                        startActivity(intent);
                        getActivity().finish();
//                        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
//                        for(Fragment fragment: fragments ){
//                            if(fragment instanceof PersonalFragment){
//                                PersonalFragment dfragment = (PersonalFragment)fragment;
//                                //ˢ��
//                                Log.v("fragment", "fragment1");
//                                dfragment.onDestroy();
//                                Log.v("fragment", "fragment2");
//                                dfragment.onAttach(getActivity());
//                                Log.v("fragment", "fragment3");
//
//                            }
//                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        connectNet.execute();
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
