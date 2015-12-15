package com.android.nourriture.nourriture;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

import com.android.nurriture.fragment.HomeFragment;
import com.android.nurriture.fragment.IngredientFragment;
import com.android.nurriture.fragment.MyViewPager;
import com.android.nurriture.fragment.NutritionFragment;
import com.android.nurriture.fragment.PersonalFragment;
import com.android.nuttriture.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private MyViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private ImageView homeTab,ingredientTab,nutritionTab,personalTab;
    private TextView homeText,ingredientText,nutritionText,personalText;
    private LinearLayout id_tab_home,id_tab_ingredients,id_tab_nutrition,id_tab_personal;

    private HomeFragment homeFragment;
    private IngredientFragment ingredientFragment;
    private NutritionFragment nutritionFragment;
    private PersonalFragment personalFragment;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void initView()
    {
        homeTab = (ImageView)findViewById(R.id.homeViewButton);
        ingredientTab = (ImageView)findViewById(R.id.ingredientViewButton);
        nutritionTab = (ImageView)findViewById(R.id.nutritionViewButton);
        personalTab = (ImageView)findViewById(R.id.personViewButton);
        id_tab_home = (LinearLayout)findViewById(R.id.id_tab_home);
        id_tab_ingredients = (LinearLayout)findViewById(R.id.id_tab_ingredients);
        id_tab_nutrition = (LinearLayout)findViewById(R.id.id_tab_nutrition);
        id_tab_personal = (LinearLayout)findViewById(R.id.id_tab_personal);

        homeText = (TextView)findViewById(R.id.id_home_tv);
        ingredientText = (TextView)findViewById(R.id.id_ingredient_tv);
        nutritionText = (TextView)findViewById(R.id.id_nutrition_tv);
        personalText = (TextView)findViewById(R.id.id_personal_tv);

        viewPager = (MyViewPager)findViewById(R.id.id_view_page);
        viewPager.setCanScroll(true);

        id_tab_home.setOnClickListener(new MyOnClickListener(0));
        id_tab_ingredients.setOnClickListener(new MyOnClickListener(1));
        id_tab_nutrition.setOnClickListener(new MyOnClickListener(2));
        id_tab_personal.setOnClickListener(new MyOnClickListener(3));
    }

    private  void init()
    {
        homeFragment = new HomeFragment();
        ingredientFragment = new IngredientFragment();
        nutritionFragment = new NutritionFragment();
        personalFragment = new PersonalFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(ingredientFragment);
        fragmentList.add(nutritionFragment);
        fragmentList.add(personalFragment);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0,false);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View arg0) {
            viewPager.setCurrentItem(index,false);
        }

    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    homeTab.setImageDrawable(getResources().getDrawable(
                            R.mipmap.sehome));
                    homeText.setTextColor(Color.parseColor("#7cb645"));
                    if (currentIndex == 1) {
                        ingredientTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.uningredient));
                        ingredientText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 2) {
                        nutritionTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unnutrition));
                        nutritionText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 3) {
                        personalTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unperson));
                        personalText.setTextColor(Color.parseColor("#a2a2a2"));
                    }
                    break;
                case 1:
                    ingredientTab.setImageDrawable(getResources().getDrawable(
                            R.mipmap.seingredient));
                    ingredientText.setTextColor(Color.parseColor("#7cb645"));
                    if (currentIndex == 0) {
                        homeTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unhome));
                        homeText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 2) {
                        nutritionTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unnutrition));
                        nutritionText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 3) {
                        personalTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unperson));
                        personalText.setTextColor(Color.parseColor("#a2a2a2"));
                    }
                    break;
                case 2:
                    nutritionTab.setImageDrawable(getResources().getDrawable(
                            R.mipmap.senutrition));
                    nutritionText.setTextColor(Color.parseColor("#7cb645"));
                    if (currentIndex == 0) {
                        homeTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unhome));
                        homeText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 1) {
                        ingredientTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.uningredient));
                        ingredientText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 3) {
                        personalTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unperson));
                        personalText.setTextColor(Color.parseColor("#a2a2a2"));
                    }
                    break;
                case 3:
                    personalTab.setImageDrawable(getResources().getDrawable(
                            R.mipmap.seperson));
                    personalText.setTextColor(Color.parseColor("#7cb645"));
                    if (currentIndex == 0) {
                        homeTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unhome));
                        homeText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 1) {
                        ingredientTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.uningredient));
                        ingredientText.setTextColor(Color.parseColor("#a2a2a2"));
                    } else if (currentIndex == 2) {
                        nutritionTab.setImageDrawable(getResources().getDrawable(
                                R.mipmap.unnutrition));
                        nutritionText.setTextColor(Color.parseColor("#a2a2a2"));
                    }
                    break;
            }
            currentIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
