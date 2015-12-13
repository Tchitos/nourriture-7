package com.android.nurriture.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.MutualInfo;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nuttriture.adapter.MutualAdapter;
import com.android.nuttriture.adapter.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MutualSuitListFragment extends Fragment {

    private ListView listView;
    private List<MutualInfo> mutualInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mutualsuit = (View)inflater.inflate(R.layout.mutulesuit_list, container,false);
        init(mutualsuit,inflater);
        return mutualsuit;
    }

    private void init(View view,LayoutInflater inflater)
    {
        listView = (ListView)view.findViewById(R.id.mutule_list_view);
        mutualInfoList = new ArrayList<MutualInfo>();
        MutualInfo mutualInfo = new MutualInfo();
        mutualInfo.setIngreName("Beef");
        mutualInfoList.add(mutualInfo);
        mutualInfoList.add(mutualInfo);
        listView.setAdapter(new MutualAdapter(mutualInfoList,inflater));
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
