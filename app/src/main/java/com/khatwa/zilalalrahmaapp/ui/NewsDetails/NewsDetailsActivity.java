package com.khatwa.zilalalrahmaapp.ui.NewsDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.di.component.DaggerNewsDetailsComponent;
import com.khatwa.zilalalrahmaapp.di.module.NewsDetailsMvpModule;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailsContract.View {

    @Inject
    NewsDetailsPresenter presenter;

    private ProgressBar progressBarLoading;
    private TextView textViewDetails;
    private ImageView imageView;
    private static final String TAG = "NewsDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        setToolbar();

        DaggerNewsDetailsComponent.builder()
                .appComponent(MyApplication.get(this).component())
                .newsDetailsMvpModule(new NewsDetailsMvpModule(this))
                .build()
                .inject(this);

        imageView = findViewById(R.id.image_news_details);
        textViewDetails = findViewById(R.id.textViewDetails);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        int newsId = getIntent().getIntExtra("newsId", 0);
        String imagePath = getIntent().getStringExtra("imagePath");
        Log.e(TAG, String.valueOf(newsId));

//        Glide.with(this)
//                .load(Constants.IMAGE_BASE_URL + imagePath)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        //progressBarImageLoading.setVisibility(View.GONE);
//                        setToolbar();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        //progressBarImageLoading.setVisibility(View.GONE);
//                        setToolbar();
//                        return false;
//                    }
//
//                })
//                .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
//                .into(imageView);



        presenter.requestNewsData(newsId);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
    }

    @Override
    public void showProgress() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(NewsItem news) {
        textViewDetails.setText(news.getDetails());
    }

    @Override
    public void onResponseFailure(String message) {
        Toast.makeText(this, R.string.communication_error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
