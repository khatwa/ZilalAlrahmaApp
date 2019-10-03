package com.khatwa.zilalalrahmaapp.NewsDetails;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;

public interface NewsDetailsContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(NewsItem news);

            void onFailure(Throwable t);
        }

        void getNewsDetails(OnFinishedListener onFinishedListener, int newsId);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(NewsItem news);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestNewsData(int newsId);
    }
}
