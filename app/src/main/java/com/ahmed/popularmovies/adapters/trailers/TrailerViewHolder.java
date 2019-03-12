package com.ahmed.popularmovies.adapters.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.models.TrailerItem;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = TrailerViewHolder.class.getSimpleName();

    TextView tv_TrailerName;
    ImageView iv_play;

    public TrailerViewHolder(@NonNull View itemView, View.OnClickListener onItemClickListener) {
        super(itemView);
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
        View view = itemView.findViewById(R.id.view_trailer_row);
        tv_TrailerName = view.findViewById(R.id.tv_trailer_name);
        iv_play = view.findViewById(R.id.iv_trailer_play);

    }

    public void bindData(TrailerItem trailerItem){
//        String posterPath = Constants.getTmbdImagesBaseUrl()+movieItem.getPosterPath();
//        Log.d(TAG, "trailerItem : "+trailerItem);
        tv_TrailerName.setText(trailerItem.getName());
    }


}
