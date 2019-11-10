package com.khatwa.zilalalrahmaapp.ui.NewsDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.di.component.DaggerNewsDetailsComponent;
import com.khatwa.zilalalrahmaapp.di.module.NewsDetailsMvpModule;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailsContract.View {

    @Inject
    NewsDetailsPresenter presenter;

    private ProgressBar progressBarLoading;
    private TextView textViewDetails;
    private static final String TAG = "NewsDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);


        DaggerNewsDetailsComponent.builder()
                .appComponent(MyApplication.get(this).component())
                .newsDetailsMvpModule(new NewsDetailsMvpModule(this))
                .build()
                .inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textViewDetails = findViewById(R.id.textViewDetails);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        int newsId=getIntent().getIntExtra("newsId",0);
        Log.e(TAG, String.valueOf(newsId));

        presenter.requestNewsData(newsId);
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
