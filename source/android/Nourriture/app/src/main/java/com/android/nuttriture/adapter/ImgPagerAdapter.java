package com.android.nuttriture.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ImgPagerAdapter extends PagerAdapter{
    List<ImageView> imageViewList;

    public ImgPagerAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(imageViewList.get(position));
        return imageViewList.get(position);

        /*position %= imageViewList.size();
        if (position<0){
            position = imageViewList.size()+position;
        }
        ImageView view = imageViewList.get(position);
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;*/
        /*((ViewPager)container).addView(imageViewList.get(position % imageViewList.size()), 0);
        return imageViewList.get(position % imageViewList.size());*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }
}
