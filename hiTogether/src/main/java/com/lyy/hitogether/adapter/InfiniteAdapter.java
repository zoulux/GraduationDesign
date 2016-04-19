package com.lyy.hitogether.adapter;

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

    private List<RoundImg> data = Collections.emptyList();

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext()).load(data.get(position).img).into(imageView);
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
