package com.lyy.hitogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.HotScenic;

import org.simple.eventbus.EventBus;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zoulux on 2016-04-19  22:54.
 */
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {
    private List<HotScenic> data = Collections.emptyList();
    private static Random random = new Random();

    @Override
    public GuideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_text_item, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuideViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(List<HotScenic> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class GuideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.image)
        ImageView pic;
        @Bind(R.id.title)
        TextView name;
        @Bind(R.id.id_tv_enjoy)
        TextView enjoy;

        View itemView;
        HotScenic hotScenic;

        public GuideViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
        }

        public void bind(final HotScenic hotScenic) {
            this.hotScenic = hotScenic;
            Glide.with(pic.getContext()).load(hotScenic.getPhotoPath()).into(pic);
            name.setText(hotScenic.getHotName());
            enjoy.setText(String.format("%s人", hotScenic.getEnjoy()));

        }


        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(hotScenic, HotScenic.TAG);
        }
    }
}
