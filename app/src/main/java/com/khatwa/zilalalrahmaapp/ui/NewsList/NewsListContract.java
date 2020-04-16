package com.khatwa.zilalalrahmaapp.ui.NewsList;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

import java.util.List;

public interface NewsListContract {


    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<NewsItem> newsArrayList);

        void onResponseFailure(String message);

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void getNewsListFirstPage();

    }

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<NewsItem>newsArrayList);

            void onFailure(String message);
        }

        void getNewsList(OnFinishedListener onFinishedListener,int pageNo);

    }
}
