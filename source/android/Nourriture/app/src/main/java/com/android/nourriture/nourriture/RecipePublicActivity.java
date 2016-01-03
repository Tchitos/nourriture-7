package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Matrix;

import com.android.nurriture.util.Base64Util;
import com.android.nurriture.util.Bimp;

/**
 * Created by Administrator on 2015/12/31.
 */
public class RecipePublicActivity extends Activity {

    private static final String tag = "RecipePublicActivity";
    private ImageView add_ingredient,add_sub;
    private LinearLayout ingredient_linearlayout,sub_linearlayout;
    private ImageView back_img;

    private ImageView menu_cover,step_img_1,step_img_2,step_img_3;
    private EditText recipe_name,recipe_profile,step_descrip_1,step_descrip_2,step_descrip_3;

    private TextView publish;

    LinearLayout.LayoutParams lp3;

    int count_ingredient = 1;
    int count_sub = 100;

    private int img_select;

    //存放主料和辅料id的list
    private List<Map<String,Integer>> idMainIngredientList = new ArrayList<Map<String,Integer>>();
    private List<Map<String,Integer>> idSubIngredientList = new ArrayList<Map<String,Integer>>();

    //存放主料和辅料内容的list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_public);
        init();
    }

    private void init()
    {
        add_ingredient = (ImageView)findViewById(R.id.add_ingredient);
        ingredient_linearlayout = (LinearLayout)findViewById(R.id.ingredient_linearlayout);
        add_sub = (ImageView)findViewById(R.id.add_sub);
        sub_linearlayout = (LinearLayout)findViewById(R.id.sub_linearlayout);
        back_img = (ImageView)findViewById(R.id.back_img);
        publish = (TextView)findViewById(R.id.publish);
        recipe_name = (EditText)findViewById(R.id.recipe_name);
        recipe_profile = (EditText)findViewById(R.id.recipe_profile);
        step_descrip_1 = (EditText)findViewById(R.id.step_descrip_1);
        step_descrip_2 = (EditText)findViewById(R.id.step_descrip_2);
        step_descrip_3 = (EditText)findViewById(R.id.step_descrip_3);

        publish.setOnClickListener(new MyPublishListenr());

        menu_cover = (ImageView)findViewById(R.id.menu_cover);
        step_img_1 = (ImageView)findViewById(R.id.step_img_1);
        step_img_2 = (ImageView)findViewById(R.id.step_img_2);
        step_img_3 = (ImageView)findViewById(R.id.step_img_3);

        menu_cover.setOnClickListener(new MyOnClickListener());
        step_img_1.setOnClickListener(new MyOnClickListener());
        step_img_2.setOnClickListener(new MyOnClickListener());
        step_img_3.setOnClickListener(new MyOnClickListener());

        img_select = menu_cover.getId();

        Map<String,Integer> mainIngred0 = new HashMap<String,Integer>();
        mainIngred0.put("id", R.id.ingredient_name_1);
        mainIngred0.put("content", R.id.ingredient_cotent_1);
        idMainIngredientList.add(mainIngred0);

        Map<String,Integer> subIngred0 = new HashMap<String,Integer>();
        subIngred0.put("id", R.id.sub_name_1);
        subIngred0.put("content",R.id.sub_cotent_1);
        idSubIngredientList.add(mainIngred0);

        lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
        lp3.setMargins(0, 10, 20, 0);
        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int name_id = ++count_ingredient;
                int content_id = ++count_ingredient;

                Map<String,Integer> mainIngred = new HashMap<String,Integer>();
                mainIngred.put("id", name_id);
                mainIngred.put("content", content_id);
                idMainIngredientList.add(mainIngred);

                LinearLayout newLinearLayout = new LinearLayout(RecipePublicActivity.this);
                newLinearLayout = generate_ingredient(name_id, content_id);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ingredient_linearlayout.addView(newLinearLayout, lp);

                View view = new View(RecipePublicActivity.this);
                view.setBackgroundColor(Color.parseColor("#dddddd"));
                view.setPadding(20, 0, 20, 0);
                ingredient_linearlayout.addView(view, lp3);
            }
        });

        add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int name_id = ++count_sub;
                int content_id = ++count_sub;

                Map<String,Integer> mainIngred = new HashMap<>();
                mainIngred.put("id", name_id);
                mainIngred.put("content", content_id);
                idSubIngredientList.add(mainIngred);

                LinearLayout newLinearLayout = new LinearLayout(RecipePublicActivity.this);
                newLinearLayout = generate_ingredient(name_id,content_id);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                sub_linearlayout.addView(newLinearLayout,lp);

                View view = new View(RecipePublicActivity.this);
                view.setBackgroundColor(Color.parseColor("#dddddd"));
                view.setPadding(20,0,20,0);
                sub_linearlayout.addView(view, lp3);
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePublicActivity.this,MainActivity.class);
                intent.putExtra("currentIndex",3);
                startActivity(intent);
                finish();
            }
        });
    }

    private class MyPublishListenr implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //获取菜谱名称
            String recipename = String.valueOf(recipe_name.getText());
            //获取主料array
            JSONArray mainIngredientArray = new JSONArray();
            try {
                for(Map map:idMainIngredientList){
                    EditText ingredientname =(EditText)findViewById((Integer)map.get("id"));
                    EditText ingredientcontent =(EditText)findViewById((Integer)map.get("content"));
                    if(ingredientname.getText()!=null && ingredientcontent.getText()!=null)
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ingredient_name", ingredientname.getText());
                        jsonObject.put("ingredient_content", ingredientcontent.getText());
                        mainIngredientArray.put(jsonObject);
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //获取辅料array
            JSONArray subIngredientArray = new JSONArray();
            try {
                for(Map map:idSubIngredientList){
                    EditText ingredientname =(EditText)findViewById((Integer)map.get("id"));
                    EditText ingredientcontent =(EditText)findViewById((Integer)map.get("content"));
                    if(ingredientname.getText()!=null && ingredientcontent.getText()!=null)
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ingredient_name", ingredientname.getText());
                        jsonObject.put("ingredient_content", ingredientcontent.getText());
                        subIngredientArray.put(jsonObject);
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //获取菜谱简介
            String recipefile = String.valueOf(recipe_profile.getText());
            //获取步骤array
            JSONArray stepArray = new JSONArray();
            try {
                JSONObject step1 = new JSONObject();
                step1.put("step_descrip",String.valueOf(step_descrip_1.getText()));
                step1.put("step_img_name",UUID.randomUUID() + ".JPEG");
                step1.put("step_img",Base64Util.bitmapToBase64(((BitmapDrawable)step_img_1.getDrawable()).getBitmap()));
                stepArray.put(step1);

                JSONObject step2 = new JSONObject();
                step2.put("step_descrip", String.valueOf(step_descrip_1.getText()));
                step2.put("step_img_name",UUID.randomUUID() + ".JPEG");
                step2.put("step_img",Base64Util.bitmapToBase64(((BitmapDrawable) step_img_1.getDrawable()).getBitmap()));
                stepArray.put(step2);

                JSONObject step3 = new JSONObject();
                step3.put("step_descrip",String.valueOf(step_descrip_1.getText()));
                step3.put("step_img_name",UUID.randomUUID() + ".JPEG");
                step3.put("step_img",Base64Util.bitmapToBase64(((BitmapDrawable)step_img_1.getDrawable()).getBitmap()));
                stepArray.put(step3);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //发送
            Map<String, String> map = new HashMap<String, String>();
            map.put("menu_cover_name", UUID.randomUUID() + ".JPEG");
            map.put("menu_cover_img", Base64Util.bitmapToBase64(((BitmapDrawable)menu_cover.getDrawable()).getBitmap()));
            map.put("recipe_name",recipename);
            map.put("recipe_profile",recipefile);
        }
    }

    private LinearLayout generate_ingredient(int ingredient_name_id,int ingredient_content_id)
    {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0,10,0,0);

        TextView textView1 = new TextView(this);
        textView1.setHint("Ingredient");
        textView1.setTextSize(15);
        textView1.setTextColor(Color.parseColor("#000000"));
        textView1.setHintTextColor(Color.parseColor("#999999"));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        linearLayout.addView(textView1, lp);

        EditText editText1 = new EditText(this);
        editText1.setId(ingredient_name_id);
        editText1.setTextSize(15);
        editText1.setTextColor(Color.parseColor("#000000"));
        editText1.setPadding(2, 0, 0, 0);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        linearLayout.addView(editText1, lp);

        View view = new View(this);
        view.setBackgroundColor(Color.parseColor("#888888"));

        LinearLayout.LayoutParams lp6 = new LinearLayout.LayoutParams(2,LinearLayout.LayoutParams.MATCH_PARENT);
        lp6.setMargins(0,10,0,10);
        linearLayout.addView(view, lp6);

        TextView textView2 = new TextView(this);
        textView2.setHint("content");
        textView2.setTextSize(15);
        textView2.setTextColor(Color.parseColor("#000000"));
        textView2.setHintTextColor(Color.parseColor("#999999"));
        textView2.setPadding(10,0,0,0);

        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        linearLayout.addView(textView2, lp4);

        EditText editText2 = new EditText(this);
        editText2.setId(ingredient_name_id);
        editText2.setTextSize(15);
        editText2.setTextColor(Color.parseColor("#000000"));
        editText2.setPadding(2, 0, 0, 0);

        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        linearLayout.addView(editText2, lp5);

        return linearLayout;
    }

    public class PopupWindows extends PopupWindow
    {

        public PopupWindows(Context mContext,View parent)
        {

            super(mContext);

            img_select = parent.getId();

            View view = View.inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

            setWidth(LayoutParams.MATCH_PARENT);
            setHeight(LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    /*Intent intent = new Intent(WorkCirclePub.this, SelectPhotoAlbum.class);
                    startActivity(intent);*/
                    gallery(v);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    dismiss();
                }
            });

        }
    }



    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
         //   Toast.makeText(getApplicationContext(), "默认的Toast", Toast.LENGTH_SHORT).show();
            new PopupWindows(RecipePublicActivity.this,v);
        }
    }

    private static final int PHOTO_REQUEST_GALLERY = 3021;
    private static final int PHOTO_REQUEST_CUT = 3022;
    private static final int TAKE_PICTURE = 3023;

    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        /*intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);*/
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    private String path = "";

    private String filename ="";
    public void photo()
    {
        String status= Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED))
        {
            File dir=new File(Environment.getExternalStorageDirectory() + "/myimage/");
            if(!dir.exists())dir.mkdirs();

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            filename = String.valueOf(System.currentTimeMillis()) +".jpg";
            File file = new File(dir, filename);
            //File file = new File(dir, "mypic.jpg");
            path = file.getPath();
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            //openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
        else{
            Toast.makeText(RecipePublicActivity.this, "No storage card!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                Bitmap mBitmap = null;
                Uri uri = data.getData();
                if(uri != null){
                    String realPath = getRealPathFromURI(uri);
                    Log.e(tag, "Huo qu tu pian chenggong，path=" + realPath);
                    Bitmap bmp = BitmapFactory.decodeFile(realPath);

                    /*Bitmap bmp = null;
                    try {
                        bmp = Bimp.revitionImageSize(realPath, menu_cover.getWidth(), menu_cover.getHeight());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mBitmap = Bitmap.createBitmap(bmp, 0,0,menu_cover.getWidth(), menu_cover.getHeight());*/

                    switch (img_select)
                    {
                        case R.id.menu_cover:
                            mBitmap = Bitmap.createScaledBitmap(bmp, menu_cover.getWidth(), menu_cover.getHeight(), true);
                            this.menu_cover.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_1:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_1.getWidth(), step_img_1.getHeight(), true);
                            this.step_img_1.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_2:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_2.getWidth(), step_img_2.getHeight(), true);
                            this.step_img_2.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_3:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_3.getWidth(), step_img_3.getHeight(), true);
                            this.step_img_3.setImageBitmap(mBitmap);
                            break;

                    }
                }else
                    Log.e(tag, "从相册获取图片失败");
            }


        }else if (requestCode == TAKE_PICTURE) {
           File f=new File(Environment.getExternalStorageDirectory()
                    +"/myimage/"+filename);

             try {
                Bitmap mBitmap = null;
                Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
                                f.getAbsolutePath(), null, null));
                if(uri != null){
                    String realPath = getRealPathFromURI(uri);
                    Log.e(tag, "Huo qu tu pian chenggong，path=" + realPath);
                    Bitmap bmp = BitmapFactory.decodeFile(realPath);

                    switch (img_select)
                    {
                        case R.id.menu_cover:
                            mBitmap = Bitmap.createScaledBitmap(bmp, menu_cover.getWidth(), menu_cover.getHeight(), true);
                            this.menu_cover.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_1:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_1.getWidth(), step_img_1.getHeight(), true);
                            this.step_img_1.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_2:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_2.getWidth(), step_img_2.getHeight(), true);
                            this.step_img_2.setImageBitmap(mBitmap);
                            break;
                        case R.id.step_img_3:
                            mBitmap = Bitmap.createScaledBitmap(bmp, step_img_3.getWidth(), step_img_3.getHeight(), true);
                            this.step_img_3.setImageBitmap(mBitmap);
                            break;

                    }
                }else
                    Log.e(tag, "从相册获取图片失败");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /*if (data != null) {
                Bitmap mBitmap = null;
                Uri uri = data.getData();
                if(uri != null){
                    toast("zhezhe");
                    String realPath = getRealPathFromURI(uri);
                    toast("zhezhepath:"+realPath);
                    Log.e(tag, "Huo qu tu pian chenggong，path="+realPath);
                    Bitmap bmp = BitmapFactory.decodeFile(realPath);
                    mBitmap = Bitmap.createScaledBitmap(bmp, menu_cover.getWidth(), menu_cover.getHeight(), true);
                    *//*Bitmap bmp = null;
                    try {
                        bmp = Bimp.revitionImageSize(realPath, menu_cover.getWidth(), menu_cover.getHeight());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mBitmap = Bitmap.createBitmap(bmp, 0,0,menu_cover.getWidth(), menu_cover.getHeight());*//*
                    this.menu_cover.setImageBitmap(mBitmap);
                }else
                    Log.e(tag, "从相册获取图片失败");
            }*/

        }

    }

    public String getRealPathFromURI(Uri contentUri){
        try{
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }catch (Exception e){
            return contentUri.getPath();
        }
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
