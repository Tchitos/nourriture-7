package com.android.nurriture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RecipeActivity;
import com.android.nourriture.nourriture.RecipeDetailActivity;
import com.android.nourriture.nourriture.SearchResultActivity;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;
import com.android.nuttriture.adapter.HomeRecipeAdapter;
import com.android.nuttriture.adapter.ImgPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/9.
 */
public class HomeFragment extends Fragment {

    private boolean hadIntercept;
    private ViewPager viewPager;
    private List<ImageView> imageList = new ArrayList<ImageView>();
    private List<View> dots;
    private int[] imageResId;
    private ImgPagerAdapter imgPagerAdapter;
    private int currentitem = 0;

    private MyGridView gridView;
    private List<String> recipeList;

    private MyListView listView;
    private List<RecipeInfo> recipeInfoList;
    private HomeRecipeAdapter homeRecipeAdapter;
    private LinearLayout search_linear;
    private TextView loadmore,nomore;
    private Handler handler;

    private int MaxDateNum;
    private ScrollView scrollView;
    private View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View homeView = (View)inflater.inflate(R.layout.layout_home, container,false);
        scrollView = (ScrollView)homeView.findViewById(R.id.scrollView);
        contentView = scrollView.getChildAt(0);
        loadmore = (TextView) homeView.findViewById(R.id.loadmore);
        nomore = (TextView) homeView.findViewById(R.id.nomore);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (contentView != null
                        && contentView.getMeasuredHeight() <= scrollView.getScrollY()
                        + scrollView.getHeight()) {
                    if(event.getAction() == MotionEvent.ACTION_MOVE){
//                        if(homeRecipeAdapter.getCount() == MaxDateNum ){
//                            loadmore.setVisibility(View.GONE);
//                            nomore.setVisibility(View.VISIBLE);
//                        }else{
                            loadmore.setVisibility(View.VISIBLE);
                            nomore.setVisibility(View.GONE);
                        //}

                    }else if (event.getAction() == MotionEvent.ACTION_UP ) {
                        Log.v("getMeasuredHeight:",contentView.getMeasuredHeight()+"");
                        Log.v("start load more:", "homeRecipeAdapter.getCount()" + homeRecipeAdapter.getCount());
                        Log.v("start load more:", "scrollView.getScrollY()" + scrollView.getScrollY());
//                            if (contentView != null
//                                    && contentView.getMeasuredHeight() <= scrollView.getScrollY()
//                                    + scrollView.getHeight()) {

                        if(homeRecipeAdapter.getCount() == MaxDateNum){
                            loadmore.setVisibility(View.GONE);
                            nomore.setVisibility(View.VISIBLE);
                        }else {
                            loadMoreDate();
                            //homeRecipeAdapter.notifyDataSetChanged();
                        }
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Log.v("start load more1:", "zidongjiazai");
//                                    loadMoreDate();
//                                    loadmore.setVisibility(View.GONE);
//                                    homeRecipeAdapter.notifyDataSetChanged();
//                                }
//
//                            }, 2000);
                        //}
                    }

                }

                    return false;
            }
        }
        );
        initView(homeView);
        initPager();
        initRecipeGridView(homeView, inflater);
        initRecipeListView(homeView, inflater);
        return homeView;
    }

    private void initRecipeListView(View view,LayoutInflater inflater)
    {
        HttpUtil connectNet = new HttpUtil(
                "/getRecipesCount",
                HttpMethod.GET){
            @Override
            protected void getResult(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    String value = jsonObject.getString("value");
                    Log.v("getRecipesCountvalue", value);
                    if(statusCode.equals("200") ||statusCode=="200"){
                        MaxDateNum = Integer.parseInt(value);
                    }else if(statusCode.equals("401") ||statusCode=="401"){

                        MaxDateNum = 0;
                        Toast.makeText(getActivity().getApplicationContext(), "An error occured during the search" ,
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        connectNet.execute();

        handler = new Handler();
        listView = (MyListView)view.findViewById(R.id.recipe_home_listView);
        recipeInfoList = new ArrayList<RecipeInfo>();
        homeRecipeAdapter = new HomeRecipeAdapter(getActivity().getApplicationContext(),recipeInfoList, inflater,listView);
        loadMoreDate();

        listView.setAdapter(homeRecipeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("recipename", recipeInfoList.get(position).getName());
                        intent.putExtras(bundle);
                        startActivity(intent);

            }
        });

    }

    private void loadMoreDate() {
        int count = homeRecipeAdapter.getCount();
        int page = count/5 + 1;
        Log.v("the page:", page + "");
        Log.v("count", count + "");
        Log.v("MaxDateNum", MaxDateNum + "");
        //if(count < MaxDateNum){
            HttpUtil connectNet = new HttpUtil(
                    "/getRecipesByPage/:"+page,
                    HttpMethod.GET){
                @Override
                protected void getResult(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.getString("statusCode");
                        String value = jsonObject.getString("value");
                        Log.v("statusCode", statusCode);
                        Log.v("getRecipesCountvalue", value);
                        if(statusCode.equals("201") ||statusCode=="201"){

                            Toast.makeText(getActivity().getApplicationContext(), "No recipes found." ,
                                    Toast.LENGTH_SHORT).show();
                        }else if(statusCode.equals("401") ||statusCode=="401"){
                            Toast.makeText(getActivity().getApplicationContext(), "An error occured during the search" ,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                JSONArray jsonArray = new JSONArray(value);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.optString("name");
                                    String image = jsonObject.optString("image");
                                    Log.v("jsonObject.optString",jsonObject.optString("image"));
                                    RecipeInfo recipe = new RecipeInfo();
                                    Log.v("name:", name);
                                    recipe.setName(name);
                                    recipe.setImgpath(image);
                                    Log.v("image:", image);
                                    recipeInfoList.add(recipe);
                                }
                                loadmore.setVisibility(View.GONE);
                                homeRecipeAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };
            connectNet.execute();
        //}

    }
    private void initRecipeGridView(View view,LayoutInflater inflater)
    {
        gridView = (MyGridView)view.findViewById(R.id.gv_recipe);
        recipeList = new ArrayList<String>();
        recipeList.add("Health");
        recipeList.add("Diet");
        recipeList.add("Buddhism");
        recipeList.add("Islam");
        recipeList.add("Woman");
        recipeList.add("Baby");
        recipeList.add("Christianity");
        recipeList.add("The old");
        recipeList.add("Diabetes");
        recipeList.add("Gastritis");
        recipeList.add("Climacteric");
        recipeList.add("More");
        gridView.setAdapter(new MyGridViewAdapter(recipeList,inflater));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Log.v("HomePage id:",""+id);
                Log.v("HomePage size:", recipeList.size()+"");
                if (id == recipeList.size()-1) {

                    intent = new Intent(getActivity(), RecipeActivity.class);
                } else {
                    intent = new Intent(getActivity(), SearchResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("SEARCHCONTEXT", "search");
                    intent.putExtras(bundle);
                }

                startActivity(intent);
            }
        });
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
            convertView = (View)inflater.inflate(R.layout.gridview_recipe,parent,false);
            TextView recipeType = (TextView)convertView.findViewById(R.id.recipe_type);
            recipeType.setText(stringList.get(position));
            return convertView;
        }
    }

    private void initView(View view)
    {
        viewPager = (ViewPager) view.findViewById(R.id.img_view_pager);
        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.v_dot0));
        dots.add(view.findViewById(R.id.v_dot1));
        dots.add(view.findViewById(R.id.v_dot2));
        imageResId = new int[] {R.mipmap.home_img1,R.mipmap.home_img1,R.mipmap.home_img1};
        for(int i=0; i<3;i++)
        {
            ImageView imageView = new ImageView(this.getActivity());
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageList.add(imageView);
        }

        search_linear = (LinearLayout)view.findViewById(R.id.search_linear);
        search_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SEARCHCONTEXT", "search");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initPager()
    {
        imgPagerAdapter = new ImgPagerAdapter(imageList);
        viewPager.setAdapter(imgPagerAdapter);
        viewPager.setOnPageChangeListener(new myImgPageChangeListener());
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class myImgPageChangeListener implements ViewPager.OnPageChangeListener
    {
        private int oldposition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentitem = position;
            dots.get(oldposition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(currentitem).setBackgroundResource(R.drawable.dot_focused);
            oldposition = currentitem;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
