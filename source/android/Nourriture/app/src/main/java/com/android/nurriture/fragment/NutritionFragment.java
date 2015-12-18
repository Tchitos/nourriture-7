package com.android.nurriture.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.SearchResultActivity;
import com.android.nuttriture.adapter.ListViewAdapter;

/**
 * Created by Administrator on 2015/12/9.
 */
public class NutritionFragment extends Fragment{

    private String[] nutrition = {"Nutrient","Mineral"};

    private String[] Nutrient = {"Vitamin A","Vitamin B1","Vitamin B2","Vitamin B3","Vitamin B4","Vitamin B5","Vitamin B6",
            "Vitamin C","Vitamin D","Vitamin E","Vitamin H","Vitamin K","Vitamin P","Vitamin B11","Vitamin B12"};

    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.context = container.getContext();
        View nutritionView = (View)inflater.inflate(R.layout.layout_nutrition, container, false);
        ScrollView scrollView = (ScrollView)nutritionView.findViewById(R.id.ingredientScroll);

        LinearLayout linearLayout = (LinearLayout)nutritionView.findViewById(R.id.ingredientTypeView);
        for (int i = 0; i < nutrition.length; i++){
            View deView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.listview_fragement_layout,null);
            TextView title= (TextView)deView.findViewById(R.id.ingredientTypetitle);
            title.setText(nutrition[i]);
            Log.v("for:%d", nutrition[i]);
            TableLayout tableLayout = (TableLayout)deView.findViewById(R.id.ingredientTypeTable);
            TableLayout.LayoutParams tablelayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tablelayout.setMargins(10,10,10,10);
            TableRow.LayoutParams tablerowlayout =
                    new TableRow.LayoutParams(300,TableRow.LayoutParams.WRAP_CONTENT);
            tablerowlayout.setMargins(10, 10, 10, 10);
            TableRow tablerow = new TableRow(this.getActivity().getApplicationContext());
            tablerow.setLayoutParams(tablelayout);
            tablerow.setGravity(Gravity.CENTER);
            tableLayout.addView(tablerow);

            for(int j = 0; j < Nutrient.length; j++){
                final TextView tv = new TextView(this.getActivity().getApplicationContext());
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.rounded_outer3);
                tv.setPadding(15, 15, 10, 10);
                tv.setText(Nutrient[j]);
                tv.setTextColor(android.graphics.Color.parseColor("#666666"));
                tv.setLayoutParams(tablerowlayout);
                tablerow.addView(tv);
                if(((j+1) % 3 == 0)&&(j != Nutrient.length-1)){
                    tablerow = new TableRow(this.getActivity());
                    tableLayout.addView(tablerow);
                    tablerow.setLayoutParams(tablelayout);
                    tablerow.setGravity(Gravity.CENTER);
                }
            }

            deView.setVisibility(View.VISIBLE);
            linearLayout.addView(deView);

        }
        ViewGroup vg = (ViewGroup)nutritionView;
        return nutritionView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
