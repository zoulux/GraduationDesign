package com.lyy.hitogether.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyy.hitogether.bean.RoundImg;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by zoulux on 2016-04-19  2:15.
 */
public class InfiniteAdapter extends InfinitePagerAdapter {

    private Context context;
    private List<RoundImg> data = Collections.emptyList();

    public InfiniteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(data.get(position).img).into(imageView);
        return imageView;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<RoundImg> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
