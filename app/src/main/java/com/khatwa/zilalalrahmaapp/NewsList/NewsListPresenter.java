package com.khatwa.zilalalrahmaapp.NewsList;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

import java.util.List;

public class NewsListPresenter implements NewsListContract.Presenter,NewsListContract.Model.OnFinishedListener {
    private NewsListContract.View newsListView;

    private NewsListContract.Model newsListModel;

    public NewsListPresenter(NewsListContract.View newsListView) {
        this.newsListView = newsListView;
        newsListModel = new NewsListModel();
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
    public void onFailure(Throwable t) {

        newsListView.onResponseFailure(t);
        if (newsListView != null) {
            newsListView.hideProgress();
        }
    }
}