package com.android.nurriture.fragment;


import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nuttriture.adapter.ListViewAdapter;

/**
 * Created by Administrator on 2015/12/9.
 */
public class IngredientFragment extends Fragment {

    private String[] ingredient = {"Meet","Vegetable","Egg/Mike","Fish","Aquatic","Beans","Fruits"};
    private ListView listView;
    private ListViewAdapter ingredientAdapter;
    private ListViewFragment listViewFragment;
    public static int mPosition;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.context = container.getContext();
        View ingredientView = (View)inflater.inflate(R.layout.layout_ingredient, container,false);
        initView(ingredientView);
        return ingredientView;
    }

    /*
     *fragment
     */
    private void initView(View view){
        listView = (ListView)view.findViewById(R.id.ingredientListview);
        ingredientAdapter = new ListViewAdapter(this.context,ingredient,"ingredient");
        listView.setAdapter(ingredientAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;

                ingredientAdapter.notifyDataSetChanged();
                for (int i = 0; i < ingredient.length; i++){
                    listViewFragment = new ListViewFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction;
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.listViewFragment, listViewFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(ListViewFragment.TAG, ingredient[position]);
                    listViewFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        }
       );

        //ListViewFragment
        listViewFragment = new ListViewFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.listViewFragment, listViewFragment);

        //bundlelistViewFragment
        Bundle bundle = new Bundle();
        bundle.putString(ListViewFragment.TAG,ingredient[mPosition]);
        listViewFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
