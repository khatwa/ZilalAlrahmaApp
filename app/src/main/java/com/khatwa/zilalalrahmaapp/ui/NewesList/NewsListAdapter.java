package com.khatwa.zilalalrahmaapp.ui.NewesList;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.khatwa.zilalalrahmaapp.Constants;
import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

    private NewsListFragment newsListFragment;
    private List<NewsItem> newsItemList;

    public NewsListAdapter(NewsListFragment newsListFragment, List<NewsItem> newsItemList) {
        this.newsListFragment = newsListFragment;
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        NewsItem newsItem = newsItemList.get(position);
        holder.textViewNewsTitle.setText(newsItem.getTitle());
        holder.textViewnewsDate.setText(newsItem.getNewsDate());

        // loading news cover using Glide library
        Glide.with(newsListFragment)
                .load(Constants.IMAGE_BASE_URL + newsItem.getImagePath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBarImageLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBarImageLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                .into(holder.imageViewNewsImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsListFragment.onNewsItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNewsTitle;

        public TextView textViewnewsDate;

        public ImageView imageViewNewsImage;

        public ProgressBar progressBarImageLoading;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewNewsTitle = itemView.findViewById(R.id.textViewNewsTitle);
            textViewnewsDate = itemView.findViewById(R.id.textViewNewsDate);
            imageViewNewsImage = itemView.findViewById(R.id.imageViewNewsImage);
            progressBarImageLoading = itemView.findViewById(R.id.progressBarImageLoading);
        }
    }
}