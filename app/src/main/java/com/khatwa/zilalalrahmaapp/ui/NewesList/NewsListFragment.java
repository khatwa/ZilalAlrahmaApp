package com.khatwa.zilalalrahmaapp.ui.NewesList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.khatwa.zilalalrahmaapp.Model.NewsItem;
import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.di.component.DaggerNewsListComponent;
import com.khatwa.zilalalrahmaapp.di.module.NewsListMvpModule;
import com.khatwa.zilalalrahmaapp.ui.NewsDetails.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.khatwa.zilalalrahmaapp.ui.NewesList.GridSpacingItemDecoration.dpToPx;

public class NewsListFragment extends Fragment implements NewsListContract.View, NewsItemClickListener {

    @Inject
    NewsListPresenter newsListPresenter;
    private List<NewsItem> newsList;
    private NewsListAdapter newsAdapter;
    private ProgressBar progressBarLoading;
    private static final String TAG = "NewsListFragment";
    private int pageNo ;
    //Constants for load more
    private int previousTotal ;
    private boolean loading = true;
    private int visibleThreshold = 7;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity= (Activity) context;
    }

    private GridLayoutManager mLayoutManager;

    public NewsListFragment() {
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

        recyclerViewNewsList = myView.findViewById(R.id.recyclerViewNewsList);
        RecyclerView recyclerViewNewsList = myView.findViewById(R.id.recyclerViewNewsList);

        newsList = new ArrayList<>();
        newsAdapter = new NewsListAdapter(this, newsList);

        mLayoutManager = new GridLayoutManager(activity, 2);
        recyclerViewNewsList.setLayoutManager(mLayoutManager);
        recyclerViewNewsList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(activity, 10), true));
        recyclerViewNewsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNewsList.setAdapter(newsAdapter);

        progressBarLoading = myView.findViewById(R.id.progressBarLoading);

        //Initializing presenter
       // newsListPresenter = new NewsListPresenter(this);

        pageNo=0;
        previousTotal=0 ;

        newsListPresenter.getNewsListFirstPage();

        recyclerViewNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        });

        return myView;
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
        Log.e(TAG,"new data page= " + pageNo);
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
        Intent i = new Intent(activity, NewsDetailsActivity.class) ;
        i.putExtra("newsId",newsList.get(position).getId());
        startActivity(i);
    }



}
