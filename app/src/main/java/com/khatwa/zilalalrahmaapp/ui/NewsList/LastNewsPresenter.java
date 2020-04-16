package com.khatwa.zilalalrahmaapp.ui.NewsList;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

import java.util.List;

import javax.inject.Inject;

public class LastNewsPresenter implements NewsListContract.Presenter, NewsListContract.Model.OnFinishedListener {
    private NewsListContract.View newsListView;

    private NewsListModel newsListModel;

    @Inject
    public LastNewsPresenter(NewsListContract.View newsListView, NewsListModel newsListModel) {
        this.newsListView = newsListView;
        this.newsListModel =newsListModel;
    }

    @Override
    public void onDestroy() {
        this.newsListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

        if (newsListView != null) {
            newsListView.showProgress();
        }
        newsListModel.getNewsList(this, pageNo);
    }

    @Override
    public void getNewsListFirstPage() {

        if (newsListView != null) {
            newsListView.showProgress();
        }
        newsListModel.getNewsList(this, 0);
    }

    @Override
    public void onFinished(List<NewsItem> newsArrayList) {
        newsListView.setDataToRecyclerView(newsArrayList);
        if (newsListView != null) {
            newsListView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message) {

        newsListView.onResponseFailure(message);
        if (newsListView != null) {
            newsListView.hideProgress();
        }
    }
}