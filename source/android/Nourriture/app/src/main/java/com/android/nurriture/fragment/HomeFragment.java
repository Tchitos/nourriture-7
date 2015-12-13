package com.android.nurriture.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.SearchResultActivity;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nuttriture.adapter.HomeRecipeAdapter;
import com.android.nuttriture.adapter.ImgPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2015/12/9.
 */
public class HomeFragment extends Fragment{

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

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View homeView = (View)inflater.inflate(R.layout.layout_home, container,false);
        initView(homeView);
        initPager();
        initRecipeGridView(homeView,inflater);
        initRecipeListView(homeView,inflater);
        return homeView;
    }

    private void initRecipeListView(View view,LayoutInflater inflater)
    {
        listView = (MyListView)view.findViewById(R.id.recipe_home_listView);
        recipeInfoList = new ArrayList<RecipeInfo>();
        RecipeInfo recipe = new RecipeInfo();
        recipe.setName("Cheese");
        recipeInfoList.add(recipe);
        RecipeInfo recipe2 = new RecipeInfo();
        recipe2.setName("Hanamaki");
        recipeInfoList.add(recipe2);
        RecipeInfo recipe3 = new RecipeInfo();
        recipe3.setName("Flaky pastry");
        recipeInfoList.add(recipe3);
        listView.setAdapter(new HomeRecipeAdapter(recipeInfoList,inflater));
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
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SEARCHCONTEXT", "search");
                intent.putExtras(bundle);
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

/*    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg){
            viewPager.setCurrentItem(currentitem);
        }
    };

    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                System.out.println("currentItem: " + currentitem);
                currentitem = (currentitem + 1) % imageList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }*/

}
