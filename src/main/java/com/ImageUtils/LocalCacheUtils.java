package com.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Environment;



/**
 * 本地缓存工具类
 * 
 * @author Kevin
 * 
 */
public class LocalCacheUtils {

	private static final String LOCAL_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/smile_cache";

	/**
	 * 从本地读取图片
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromLocal(String url) {

		try {
			String fileName = url;
			File file = new File(LOCAL_PATH, fileName);

			if (file.exists()) {
				// 图片压缩
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;// 表示压缩比例，2表示宽高都压缩为原来的二分之一， 面积为四分之一
				options.inPreferredConfig = Config.RGB_565;// 设置bitmap的格式，565可以降低内存占用

				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
						file), null, options);
				return bitmap;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向本地存图片
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void putBitmapToLocal(String url, Bitmap bitmap) {
		try {
			String fileName =url;
			File file = new File(LOCAL_PATH, fileName);
			File parent = file.getParentFile();

			// 创建父文件夹
			if (!parent.exists()) {
				parent.mkdirs();
			}

			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
