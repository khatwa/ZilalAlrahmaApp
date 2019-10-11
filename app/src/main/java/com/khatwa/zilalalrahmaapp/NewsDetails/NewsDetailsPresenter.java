package com.khatwa.zilalalrahmaapp.NewsDetails;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

public class NewsDetailsPresenter implements NewsDetailsContract.Presenter, NewsDetailsContract.Model.OnFinishedListener {

    private NewsDetailsContract.View newsDetailView;
    private NewsDetailsContract.Model newsDetailsModel;

    public NewsDetailsPresenter(NewsDetailsContract.View newsDetailView) {
        this.newsDetailView = newsDetailView;
        this.newsDetailsModel = new NewsDetailsModel();
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
