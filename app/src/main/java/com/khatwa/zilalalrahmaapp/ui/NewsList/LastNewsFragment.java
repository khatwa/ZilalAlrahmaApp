package com.khatwa.zilalalrahmaapp.ui.NewsList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.di.component.DaggerNewsListComponent;
import com.khatwa.zilalalrahmaapp.di.module.NewsListMvpModule;
import com.khatwa.zilalalrahmaapp.ui.NewsDetails.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.khatwa.zilalalrahmaapp.ui.NewsList.GridSpacingItemDecoration.dpToPx;

public class LastNewsFragment extends Fragment implements NewsListContract.View, NewsItemClickListener  {

    @Inject
    LastNewsPresenter lastNewsPresenter;
    private List<NewsItem> newsList;
    private NewsListAdapter newsAdapter;
    private ProgressBar progressBarLoading;
    private static final String TAG = "LastNewsFragment";
    private int pageNo;
    //Constants for load more
    private int previousTotal;
    private boolean loading = true;
    private int visibleThreshold = 7;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private Activity activity;
    private NestedScrollView mNestedScrollView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    private GridLayoutManager mLayoutManager;

    public LastNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_last_news, container, false);

        DaggerNewsListComponent.builder()
                .appComponent(MyApplication.get(getActivity()).component())
                .newsListMvpModule(new NewsListMvpModule(this))
                .build()
                .inject(this);

        RecyclerView recyclerViewNewsList = myView.findViewById(R.id.recyclerViewNewsList);
        recyclerViewNewsList.setNestedScrollingEnabled(false);
        newsList = new ArrayList<>();
        newsAdapter = new NewsListAdapter(this, newsList);
        mLayoutManager = new GridLayoutManager(activity, 2);
        recyclerViewNewsList.setLayoutManager(mLayoutManager);
        recyclerViewNewsList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(activity, 10), true));
        recyclerViewNewsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNewsList.setAdapter(newsAdapter);
        progressBarLoading = myView.findViewById(R.id.progressBarLoading);
        mNestedScrollView = myView.findViewById(R.id.mNestedScrollView);

        pageNo = 0;
        previousTotal = 0;

        lastNewsPresenter.getNewsListFirstPage();

      /*  recyclerViewNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                // Handling the infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    newsListPresenter.getMoreData(pageNo);
                    loading = true;
                }

            }
        });  */

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                        // Handling the infinite scroll
                        if (loading) {
                            if (totalItemCount > previousTotal) {
                                loading = false;
                                previousTotal = totalItemCount;
                            }
                        }
                        if (!loading && (totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + visibleThreshold)) {
                            lastNewsPresenter.getMoreData(pageNo);
                            loading = true;
                        }
                    }
                }
            }
        });

        ImageButton buttonFacebook = myView.findViewById(R.id.button_facebook);
        ImageButton buttonTwitter = myView.findViewById(R.id.button_twitter);
        ImageButton buttonYoutube = myView.findViewById(R.id.button_youtube);

        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink(view);
            }
        });
        buttonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink(view);
            }
        });
        buttonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLink(view);
            }
        });

        return myView;
    }

    public void openLink(View view) {
        int id = view.getId();
        String url = "";
        switch (id) {
            case R.id.button_facebook:
                url = "https://www.facebook.com/zelalalrahma/";
                break;
            case R.id.button_twitter:
                url = "https://twitter.com/zelalalrhma";
                break;
            case R.id.button_youtube:
                url = "https://www.youtube.com/channel/UCKdPPLMQ6J0X65vDrt1hpgA";
                break;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
    public void setDataToRecyclerView(List<NewsItem> newsArrayList) {
        newsList.addAll(newsArrayList);
        newsAdapter.notifyDataSetChanged();
        Log.e(TAG, "new data page= " + pageNo);
        pageNo++;
    }

    @Override
    public void onResponseFailure(String message) {
        Log.e(TAG, message);
        Toast.makeText(activity, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNewsItemClick(int position) {

        if (position == -1) {
            return;
        }
        // Bundle bundle = new Bundle();
        //bundle.putInt("newsId", newsList.get(position).getId());
        //Navigation.findNavController(activity,R.id.nav_host_fragment).navigate(R.id.action_lastNewsFragment_to_newsDetailsFragment, bundle);
        Intent i = new Intent(activity, NewsDetailsActivity.class);
        i.putExtra("newsId", newsList.get(position).getId());
        i.putExtra("imagePath", newsList.get(position).getImagePath());
        startActivity(i);
    }

}
