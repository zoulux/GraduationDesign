package com.lyy.hitogether.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyy.hitogether.R;
import com.lyy.hitogether.activity.fragment.first_fragment.FirstFragmentDestination;
import com.lyy.hitogether.bean.Service;

import org.simple.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zoulux on 2016-04-19  1:15.
 */
public class DeatinationAdapter extends RecyclerView.Adapter<DeatinationAdapter.DeatiationViewHolder> {

    private static final String TAG = "DeatinationAdapter";


    public DeatinationAdapter() {
        EventBus.getDefault().register(this);
    }


    public void setData(List<Service> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<Service> data = Collections.emptyList();

    @Override
    public DeatiationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deatination, parent, false);
        return new DeatiationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeatiationViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    final static class DeatiationViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.id_third_fragment_scen_img)
        ImageView ivBg;

        @Bind(R.id.id_third_fragment_desc)
        TextView tvDsc;

        @Bind(R.id.id_third_fragment_guide_avartar)
        ImageView tvPhoto;

        @Bind(R.id.id_third_fragment_guide_name)
        TextView tvName;

        @Bind(R.id.id_fragment_bt_appointment)
        TextView tvAppointment;

        @Bind(R.id.id_third_fragment_place)
        TextView tvPlace;

        @Bind(R.id.id_third_fragment_grade)
        RatingBar rbGrade;

        View itemView;
        Service service;

        public DeatiationViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Service service) {
            this.service = service;
            Glide.with(itemView.getContext()).load(service.getShowImg()).into(ivBg);
            Glide.with(itemView.getContext()).load(service.getUser().getAvatar()).into(tvPhoto);

            tvName.setText(service.getUser().getNick());
            tvDsc.setText(service.getSummary());
            tvPlace.setText(service.getDestination());
            rbGrade.setRating(service.getUser().getStar());


        }


        @OnClick(R.id.id_fragment_bt_appointment)
        public void talk() {
            EventBus.getDefault().post(service.getUser(), FirstFragmentDestination.EVENT_TALK);
        }

        @OnClick(R.id.id_third_fragment_scen_img)
        public void jumpInfo() {
            EventBus.getDefault().post(service, FirstFragmentDestination.EVENT_JUMP_INFO);
        }

    }

}
