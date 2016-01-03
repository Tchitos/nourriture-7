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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.nourriture.nourriture.NutritionDetailActivity;
import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;


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

    private List<String> ingredientList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.listview_fragment,container,false);

        //TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        ScrollView scrollView = (ScrollView)view.findViewById(R.id.ingredientScroll);

        str = getArguments().getString(TAG);
        Log.v("this is the str:%s",str);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.ingredientTypeView);
        //tv_title.setText(str);

        initData();

        for (int i = 0; i < types.length; i++){
            Log.v("for:%d", i + "1");
            View deView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.listview_fragement_layout,null);
            Log.v("for:%d", i + "2");
            TextView title= (TextView)deView.findViewById(R.id.ingredientTypetitle);
            Log.v("for:%d", i + "3");
            title.setText(types[i]);
            Log.v("for:%d", types[i]);

            MyGridView gridView = (MyGridView)deView.findViewById(R.id.gv_ingredient);
            gridView.setAdapter(new MyGridViewAdapter(ingredientList,inflater));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("SEARCHCONTEXT", ingredientList.get(position));
                    bundle.putString("search_type","ingredient_type");
                    bundle.putInt("currentIndex",0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            /*TableLayout tableLayout = (TableLayout)deView.findViewById(R.id.ingredientTypeTable);
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
*/
            deView.setVisibility(View.VISIBLE);
            linearLayout.addView(deView);

        }
        ViewGroup vg = (ViewGroup)view;
        return vg;
    }

    private void initData()
    {

        ingredientList.add("pork");
        ingredientList.add("ribs");
        ingredientList.add("Cabbage");
        ingredientList.add("streaky");
        ingredientList.add("pork");
        ingredientList.add("ribs");
        ingredientList.add("streaky");
        ingredientList.add("streaky");
        ingredientList.add("pork");
        ingredientList.add("ribs");
        ingredientList.add("streaky");
        ingredientList.add("streaky");

    }

    private class MyGridViewAdapter extends BaseAdapter
    {
        List<String> stringList;
        LayoutInflater inflater;
        public MyGridViewAdapter(List<String> stringList,LayoutInflater inflater) {
            this.stringList = stringList;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = (View)inflater.inflate(R.layout.gridview_ingredient,parent,false);
            TextView recipeType = (TextView)convertView.findViewById(R.id.recipe_type);
            recipeType.setText(stringList.get(position));
            return convertView;
        }
    }
}

