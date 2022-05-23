package com.example.musiquitadummy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Context mContext;
    private List<Items> mItems;
    private YouTubeVideo videoItem;
    public MyAdapter(List<Items> results, Context applicationContext) {
        this.mContext = applicationContext;
        this.mItems = results;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.search_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.titleRow.setText(mItems.get(position).getSnippet().getTitle());
        holder.channelRow.setText(mItems.get(position).getSnippet().getChannelTitle());
        holder.dateRow.setText(mItems.get(position).getSnippet().getPublishedAt());
        Glide.with(mContext).load(mItems.get(position).getSnippet().getThumbnails().getMedium().getUrl()).fitCenter().into(holder.imageRow);
        holder.relativeLayout.setOnClickListener(v -> {
            videoItem = new YouTubeVideo();
            videoItem.setId(mItems.get(position).getId().videoId);
            videoItem.setDuration("3:23");
            videoItem.setTitle("La Santa");
            videoItem.setViewCount("2");
            videoItem.setThumbnailURL("https://i3.ytimg.com/vi/JUxITamPWrY/maxresdefault.jpg");

            Intent serviceIntent = new Intent(mContext, BackgroundAudioService.class);

            serviceIntent.setAction(BackgroundAudioService.ACTION_PLAY);
            serviceIntent.putExtra(Config.YOUTUBE_TYPE, ItemType.YOUTUBE_MEDIA_TYPE_VIDEO);
            serviceIntent.putExtra(Config.YOUTUBE_TYPE_VIDEO, videoItem);
            mContext.startService(serviceIntent);
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleRow;
        TextView channelRow;
        TextView dateRow;
        ImageView imageRow;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleRow = itemView.findViewById(R.id.TituloRow);
            channelRow = itemView.findViewById(R.id.ChannelRow);
            dateRow = itemView.findViewById(R.id.DateRow);
            imageRow = itemView.findViewById(R.id.imageRow);
            relativeLayout = itemView.findViewById(R.id.vistarelativa);
        }

    }
}
