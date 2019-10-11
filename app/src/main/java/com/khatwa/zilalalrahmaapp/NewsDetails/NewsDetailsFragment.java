package com.khatwa.zilalalrahmaapp.NewsDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.R;

import androidx.fragment.app.Fragment;

public class NewsDetailsFragment extends Fragment  implements NewsDetailsContract.View{
       public NewsDetailsFragment() {
        // Required empty public constructor
    }
    private NewsDetailsPresenter newsDetailsPresenter;

    private ProgressBar progressBarLoading;
    private TextView textViewDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_details, container, false);
        textViewDetails = view.findViewById(R.id.textViewDetails);
        progressBarLoading = view.findViewById(R.id.progressBarLoading);
        int newsId=getArguments().getInt("newsId");

        newsDetailsPresenter = new NewsDetailsPresenter(this);
        newsDetailsPresenter.requestNewsData(newsId);
        return view;
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
        Toast.makeText(getContext(), R.string.communication_error, Toast.LENGTH_SHORT).show();
    }
}
