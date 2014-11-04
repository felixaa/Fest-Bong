package com.felixaa.fest_bong;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chris on 11/4/2014.
 */
public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder> {

    public List<FestivalInfo> festivalList;

    public FestivalAdapter(List<FestivalInfo> festivalList) {
        this.festivalList = festivalList;
    }

    @Override
    public int getItemCount() {
        return festivalList.size();
    }

    @Override
    public void onBindViewHolder(FestivalViewHolder festivalViewHolder, int i) {
        FestivalInfo fi = festivalList.get(i);
        festivalViewHolder.festName.setText(fi.festName);
        festivalViewHolder.festInfo.setText(fi.festInfo);
    }

    @Override
    public FestivalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new FestivalViewHolder(itemView);
    }

    public static class FestivalViewHolder extends RecyclerView.ViewHolder {
        protected TextView festName;
        protected TextView festInfo;

        public FestivalViewHolder (View v) {
            super(v);
            festName = (TextView) v.findViewById(R.id.festNavn);
            festInfo = (TextView) v.findViewById(R.id.festInfo);
        }
    }

}