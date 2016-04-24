package com.lyy.hitogether.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.first_fragment.FirstFragmentOfFriend;
import com.lyy.hitogether.bean.Demand;
import com.lyy.hitogether.bean.MyUser;
import com.lyy.hitogether.util.ToastUtil;

import org.simple.eventbus.EventBus;

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

    public List<Demand> getData() {
        return data;
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

        @Bind(R.id.tv_zan)
        TextView tvZan;

        Context context;
        Demand demand;


        public FriendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bind(Demand demand) {
            this.demand = demand;
            MyUser user = demand.getUser();

            tvName.setText(user.getNick());
            tvDesc.setText( String.format("我想在%s去%s玩耍，现在已经有%d个人了,你要不要一起？", demand.getGoTime(), demand.getDestination(), demand.getPeopleNum()));
            Glide.with(context).load(user.getAvatar()).into(pic);
            tvZan.setText(String.valueOf(demand.getZan()));
        }

        @OnClick(R.id.tv_zan)
        public void zan() {
            EventBus.getDefault().post(getAdapterPosition(), FirstFragmentOfFriend.EVENT_DEMAND);
        }

        @OnClick(R.id.tv_talk)
        public void talk() {
            ToastUtil.message("talk");
        }
    }
}
