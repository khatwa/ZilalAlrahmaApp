package com.khatwa.zilalalrahmaapp.NewsDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailsContract.View {

    private NewsDetailsPresenter newsDetailsPresenter;

    private ProgressBar progressBarLoading;
    private TextView textViewDetails;
    private static final String TAG = "NewsDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textViewDetails = findViewById(R.id.textViewDetails);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        int newsId=getIntent().getIntExtra("newsId",0);
        Log.e(TAG, String.valueOf(newsId));

        newsDetailsPresenter = new NewsDetailsPresenter(this);
        newsDetailsPresenter.requestNewsData(newsId);
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
        newsDetailsPresenter.onDestroy();
    }

}
