package com.android.nourriture.nourriture;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nurriture.fragment.IngredientListFragment;
import com.android.nurriture.fragment.MutualSuitListFragment;
import com.android.nurriture.fragment.MyViewPager;
import com.android.nurriture.fragment.RecipeListFragment;
import com.android.nuttriture.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class SearchResultActivity extends FragmentActivity {

    private String search_type = "recipe_classification";

    private MyViewPager viewPager;
    private List<Fragment> fragmentList ;
    private FragmentAdapter mFragmentAdapter;

    private TextView[] tab = new TextView[3];

    private IngredientListFragment ingredientListFragment;
    private RecipeListFragment recipeListFragment;
    private MutualSuitListFragment mutualSuitListFragment;

    private ImageView mTabLineIv;

    private int currentIndex;

    private int screenWidth;

    private String searchcontext="";

    private ImageView back_img;

    private TextView search;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        getBundle();
        init();
        initViewPager();
        initTabLineWidth();
        editText = (EditText)findViewById(R.id.serachContext);
        editText.setText(searchcontext);
    }

    @Override
    protected void onResume() {
        int id = getIntent().getExtras().getInt("currentIndex",0);

        viewPager.setCurrentItem(id, false);

        super.onResume();
    }

    private void init()
    {
        back_img = (ImageView)findViewById(R.id.back_img);
        search = (TextView)findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchcontext = String.valueOf(editText.getText());
                search_type = "both";
                initViewPager();
            }
        });

        back_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, MainActivity.class);
                if (search_type.equals("ingredient_type")) {
                    intent.putExtra("currentIndex", 1);
                } else {
                    intent.putExtra("currentIndex", 0);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchResultActivity.this, MainActivity.class);
        if (search_type.equals("ingredient_type")) {
            intent.putExtra("currentIndex", 1);
        } else {
            intent.putExtra("currentIndex", 0);
        }
        startActivity(intent);
        finish();
    }

    private void initViewPager()
    {
        tab[0] = (TextView)findViewById(R.id.ingredient_tv);
        tab[1]  = (TextView)findViewById(R.id.recipe_tv);
       // tab[2]  = (TextView)findViewById(R.id.mutualsuit_tv);
        tab[0].setOnClickListener(new myTabClickListener(0));
        tab[1].setOnClickListener(new myTabClickListener(1));
       // tab[2].setOnClickListener(new myTabClickListener(2));

        mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);

        viewPager = (MyViewPager)findViewById(R.id.st_view_pager);
        viewPager.setCanScroll(true);

        fragmentList = new ArrayList<Fragment>();
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0, false);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        setViewPager();
    }

    private void setViewPager()
    {
        ingredientListFragment = new IngredientListFragment();
        recipeListFragment = new RecipeListFragment();
        mutualSuitListFragment = new MutualSuitListFragment();
        //Toast.makeText(getApplicationContext(), "aaaaasearchconetxt::" + searchcontext, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("search_type", search_type);
        bundle.putString("search_text", searchcontext);
        ingredientListFragment.setArguments(bundle);
        recipeListFragment.setArguments(bundle);

        fragmentList.add(ingredientListFragment);
        fragmentList.add(recipeListFragment);
        fragmentList.add(mutualSuitListFragment);
        mFragmentAdapter.notifyDataSetChanged();

    }

    private class myTabClickListener implements OnClickListener{

        private int index = 0;

        public myTabClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            Log.v("This is clicking number", index+"");
            viewPager.setCurrentItem(index,false);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int previou_index = 0;
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.v("This position is:", position+"");
            currentIndex = position;
            tab[previou_index].setTextColor(Color.parseColor("#666666"));
            tab[currentIndex].setTextColor(Color.parseColor("#f74444"));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
            switch (position){
                case 0:
                    lp.leftMargin = 60;
                    break;
                case 1:
                    lp.leftMargin = screenWidth / 2;
                    break;
                case 2:
                    lp.leftMargin = 100 + (screenWidth / 2)*2;
                    break;
            }
            mTabLineIv.setLayoutParams(lp);
            previou_index = currentIndex;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
        lp.width = screenWidth / 4;
        lp.leftMargin = 60;
        mTabLineIv.setLayoutParams(lp);
    }

    private void getBundle() {
        Bundle bundle = this.getIntent().getExtras();
        searchcontext = bundle.getString("SEARCHCONTEXT");
        search_type = bundle.getString("search_type");
    }
}
