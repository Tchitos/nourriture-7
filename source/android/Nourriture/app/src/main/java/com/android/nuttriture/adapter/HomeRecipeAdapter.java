package com.android.nuttriture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nurriture.fragment.MyListView;
import com.android.nurriture.util.Config;
import com.android.nurriture.util.ImageLoader;
import com.android.nurriture.util.SyncImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class HomeRecipeAdapter extends BaseAdapter {
    List<RecipeInfo> recipeInfoList;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    //ImageView imageView;
    MyListView listView;
    SyncImageLoader syncImageLoader;
    private Context context;
    public HomeRecipeAdapter(Context context,List<RecipeInfo> recipeInfoList, LayoutInflater inflater,MyListView listView) {
        this.recipeInfoList = recipeInfoList;
        this.inflater = inflater;
        this.context = context;
        this.listView = listView;
        //imageLoader=new ImageLoader(context);

        syncImageLoader = new SyncImageLoader();
    }

    public HomeRecipeAdapter(List<RecipeInfo> recipeInfoList) {
        this.recipeInfoList = recipeInfoList;
    }

    @Override
    public int getCount() {
        return recipeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("Start getView:","1");
        convertView = (View)inflater.inflate(R.layout.listview_recipe_item, parent, false);
        TextView name = (TextView)convertView.findViewById(R.id.recipe_name);
        Log.v("Start getView:", "2");
        name.setText(recipeInfoList.get(position).getName());
        convertView.setTag(position);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.recipe_img);
        String imagepath = recipeInfoList.get(position).getImgpath();
        String path = Config.SERVER_URL+"/public/images/"+imagepath;
        Log.v("image path:",path);

        //imageLoader.DisplayImage(path, imageView);

        syncImageLoader.loadImage(position, path, new SyncImageLoader.OnImageLoadListener(){

            @Override
            public void onImageLoad(Integer t, Drawable drawable) {
                //BookModel model = (BookModel) getItem(t);
                Log.v("get image", "success");
                Log.v("Integer",t+"");
                Log.v("Drawable:",drawable.toString());
                View view = listView.findViewWithTag(t);
                if(view != null){
                    Log.v("view is not null","yes");
                    ImageView imageView = (ImageView)view.findViewById(R.id.recipe_img);
                    //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setBackgroundDrawable(drawable);
                    //imageView.setImageDrawable(drawable);

                }else{
                    Log.v("view is null","no");
                }

                //imageView.setImageDrawable(drawable);
            }
            @Override
            public void onError(Integer t) {
                Log.v("here is worng",t+"");
            }

        });
        return convertView;
    }
//    SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener(){
//
//        @Override
//        public void onImageLoad(Integer t, Drawable drawable) {
//            //BookModel model = (BookModel) getItem(t);
//            Log.v("get image", "success");
//            imageView.setBackgroundDrawable(drawable);
//        }
//        @Override
//        public void onError(Integer t) {
//        }
//
//    };
}
