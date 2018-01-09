package com.example.ryanrogers.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by ryanrogers on 16/09/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    public static final String LOG_TAG = "myLogs";

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    private List<Trailer> mTrailers;

    private Context mContext;

    private String youTubeLink;


    public TrailerAdapter(Context context, List<Trailer> trailers) {
        mTrailers = trailers;
        mContext = context;
    }


    private Context getContext(){
        return mContext;
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.trailer_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {


        Trailer trailer = mTrailers.get(position);

        youTubeLink = trailer.getTrailerYoutubeUrl();


        Picasso
                .with(getContext())
                .load(trailer.getTrailerImageUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerInside()
                .into(holder.imageViewTrailer);


    }




    @Override
    public int getItemCount() {
        return (null != mTrailers ? mTrailers.size() : 0);
    }



    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageViewTrailer;



        public TrailerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.imageViewTrailer = (ImageView) itemView.findViewById(R.id.imageViewThree);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick " + getPosition());
            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink));
            mContext.startActivity(intent);
        }

    }

}



