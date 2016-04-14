package com.lyy.hitogether.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lyy.hitogether.util.ImageUtils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;



/**
 * 高仿效果中3D图片浏览的适配器
 * @author Administrator
 *
 */
public class GalleryAdapter extends MyBaseAdapter<Integer> {
	
	private Context context;
	List<Integer>list = new ArrayList<Integer>();
	public GalleryAdapter(Context context, List<Integer> commonDatas) {
		super(context, commonDatas);
		this.context = context;
	}

	
	
	 int pos;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageView iv = null;
		if(convertView == null) {
			iv = new ImageView(context);
		} else {
			iv = (ImageView) convertView;
		}
		
		Bitmap bitmap = ImageUtils.getBitmap(context, commonDatas.get(position), position);
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		bd.setAntiAlias(true);
		iv.setImageDrawable(bd);
		
		//iv.setLayoutParams(new LayoutParams(160, 240));
//		iv.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (pos==position) {
//					System.out.println(position+"position>>>>>>>>>>>>>>>>");
//				}
//				
//				
//			}
//		});
		
		
		return iv;
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}

}
