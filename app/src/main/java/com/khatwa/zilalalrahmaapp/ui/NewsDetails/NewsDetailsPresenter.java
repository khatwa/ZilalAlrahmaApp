package com.khatwa.zilalalrahmaapp.ui.NewsDetails;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

import javax.inject.Inject;

public class NewsDetailsPresenter implements NewsDetailsContract.Presenter, NewsDetailsContract.Model.OnFinishedListener {

    private NewsDetailsContract.View newsDetailView;
    private NewsDetailsModel newsDetailsModel;

    @Inject
    public NewsDetailsPresenter(NewsDetailsContract.View newsDetailView,NewsDetailsModel newsDetailsModel) {
        this.newsDetailView = newsDetailView;
        this.newsDetailsModel = newsDetailsModel;
    }

    @Override
    public void onDestroy() {
        newsDetailView = null;
    }

    @Override
    public void requestNewsData(int newsId) {
        if (newsDetailView != null) {
            newsDetailView.showProgress();
        }
        newsDetailsModel.getNewsDetails(this, newsId);
    }

    @Override
    public void onFinished(NewsItem newsItem) {

        if (newsDetailView != null) {
            newsDetailView.hideProgress();
        }
        newsDetailView.setDataToViews(newsItem);
    }

    @Override
    public void onFailure(String message) {
        if (newsDetailView != null) {
            newsDetailView.hideProgress();
        }
        newsDetailView.onResponseFailure(message);
    }
}
