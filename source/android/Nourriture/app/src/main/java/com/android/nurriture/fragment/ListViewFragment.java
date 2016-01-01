package com.android.nurriture.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.SearchResultActivity;


/**
 * Created by Administrator on 2015/12/11.
 */
public class ListViewFragment extends Fragment {
    public static final String TAG = "ListViewFragment";
    private String str;
    private Context context;

    private String[] types = {"pork","beef","mutton"};
    private String[] partOfPork = {"pork","ribs","streaky","streaky","streaky","streaky","streaky","streaky","streaky"};
    private String[] partOfMutton = {"mutton","lamb"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.listview_fragment,container,false);

        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ScrollView scrollView = (ScrollView)view.findViewById(R.id.ingredientScroll);

        str = getArguments().getString(TAG);
        Log.v("this is the str:%s",str);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.ingredientTypeView);
        tv_title.setText(str);
        for (int i = 0; i < types.length; i++){
            Log.v("for:%d", i + "1");
            View deView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.listview_fragement_layout,null);
            Log.v("for:%d", i + "2");
            TextView title= (TextView)deView.findViewById(R.id.ingredientTypetitle);
            Log.v("for:%d", i + "3");
            title.setText(types[i]);
            Log.v("for:%d", types[i]);
            TableLayout tableLayout = (TableLayout)deView.findViewById(R.id.ingredientTypeTable);
            TableLayout.LayoutParams tablelayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tablelayout.setMargins(10,10,10,10);
            TableRow.LayoutParams tablerowlayout =
                    new TableRow.LayoutParams(200,TableRow.LayoutParams.WRAP_CONTENT);
            TableRow tablerow = new TableRow(this.getActivity().getApplicationContext());
            tablerowlayout.setMargins(10, 10, 10, 10);
            tablerow.setLayoutParams(tablelayout);
            tablerow.setGravity(Gravity.CENTER);
            tableLayout.addView(tablerow);

            for(int j = 0; j < partOfPork.length; j++){
                final TextView tv = new TextView(this.getActivity().getApplicationContext());
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.rounded_outer3);
                tv.setPadding(15, 15, 10, 10);
                tv.setTextColor(android.graphics.Color.parseColor("#666666"));
                tv.setText(partOfPork[j]);
                tv.setLayoutParams(tablerowlayout);
                Log.v("wight:", tv.getWidth() + "");
                Log.v("hight:",tv.getHeight()+"");
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                        Bundle bundle = new Bundle();
                        String searchcontext = tv.getText().toString();
                        bundle.putString("SEARCHCONTEXT", searchcontext);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                tablerow.addView(tv);
                if(((j+1) % 3 == 0)&&(j != partOfPork.length-1)){
                    tablerow = new TableRow(this.getActivity());
                    tableLayout.addView(tablerow);
                    tablerow.setLayoutParams(tablelayout);
                    tablerow.setGravity(Gravity.CENTER);
                }
            }

            deView.setVisibility(View.VISIBLE);
            linearLayout.addView(deView);

        }
        ViewGroup vg = (ViewGroup)view;
        return vg;
    }

}
