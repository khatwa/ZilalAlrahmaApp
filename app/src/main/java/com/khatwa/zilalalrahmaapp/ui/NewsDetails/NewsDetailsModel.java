package com.khatwa.zilalalrahmaapp.ui.NewsDetails;

import android.util.Log;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.Network.APIInterface;

import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailsModel implements NewsDetailsContract.Model {

    private APIInterface apiInterface;
    private final String TAG = "NewsDetailsModel";

    @Inject
    public NewsDetailsModel(APIInterface apiInterface){
        this.apiInterface=apiInterface;
    }

    @Override
    public void getNewsDetails(final NewsDetailsContract.Model.OnFinishedListener onFinishedListener, int newsId) {
//        APIInterface apiService =
//                ApiClient.getClient().create(APIInterface.class);

        Call<NewsItem> call = apiInterface.getNewsDetails(newsId);
        call.enqueue(new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                if (response.isSuccessful()) {
                    NewsItem newsItem = response.body();
                    if (newsItem.getDetails().equals(""))
                        newsItem.setDetails("no details");
                    onFinishedListener.onFinished(newsItem);

                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        onFinishedListener.onFailure(jObjError.getJSONObject("error").getString("message"));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                Log.e(TAG,  t.toString());
                onFinishedListener.onFailure(t.getMessage());
            }
        });

    }

}
