package com.android.nurriture.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bimp
{
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();

	// 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
	public static List<String> drr = new ArrayList<String>();

	public static Bitmap revitionImageSize(String path,int reqWidth,int reqHeight) throws IOException
	{
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		final int width = options.outWidth;
		final int height = options.outHeight;
		int widthRatio =1;
		if (width > reqWidth ) {
			// 计算出实际宽度和目标宽度的比率
			widthRatio = Math.round((float) width / (float) reqWidth);
			widthRatio = (Math.round((float)width/reqWidth)+Math.round((float)height/reqHeight))/2;
		}
		while (true)
		{
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000))
			{
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));

				options.inSampleSize = widthRatio;
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
}
