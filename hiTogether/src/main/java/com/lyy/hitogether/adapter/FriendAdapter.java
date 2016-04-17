package com.lyy.hitogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyy.hitogether.R;
import com.lyy.hitogether.bean.Demand;
import com.lyy.hitogether.util.ToastUtil;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zoulux on 2016-04-17  14:59.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private List<Demand> data = Collections.emptyList();

    public void setData(List<Demand> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static public class FriendViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_pic)
        ImageView pic;

        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_dsc)
        TextView tvDesc;


        public FriendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Demand demand) {

            tvName.setText("11");
            tvDesc.setText(demand.getDestination()+": "+demand.getPeopleNum());
        }

        @OnClick(R.id.tv_zan)
        public void zan() {
            ToastUtil.message("zan");
        }

        @OnClick(R.id.tv_talk)
        public void talk() {
            ToastUtil.message("talk");
        }
    }
}
