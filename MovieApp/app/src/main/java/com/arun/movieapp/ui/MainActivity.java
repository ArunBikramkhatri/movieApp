package com.arun.movieapp.ui;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arun.movieapp.R;
import com.arun.movieapp.model.MovieResponseList;
import com.arun.movieapp.model.MovieResponse;
//import com.arun.movieapp.listeners.LastItemReachedCallback;
import com.arun.movieapp.receivers.WifiStateReceiver;
import com.arun.movieapp.service.MovieAPIBuilder;
import com.arun.movieapp.ui.recyclerView.MovieRecyclerViewAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context context;
    private MovieAPIBuilder movieAPIBuilder;
    private Gson gson;
    private MovieResponseList movieResponseList ;

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;

    private static MainActivity mainActivity;

    private List<MovieResponse> movieDetailList ;

    boolean isLoading = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();
        movieAPIBuilder = new MovieAPIBuilder();
        gson = new Gson();
        recyclerView = findViewById(R.id.recycler_view);

        fetchTopRatedMovies();
        populateData();
        initAdapter();
        initScrollListener();


    }


    void initAdapter(){
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, new ArrayList<>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,  2);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }


    void populateData(){

    }

    void initScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(!isLoading){
                    if(linearLayoutManager != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieResponseList.getResponses().size() - 1 ){
                        Log.d(TAG , "scroll last ");
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


    void loadMore(){
      fetchNextPage();
    }
    void fetchMovieById() {
        try {
            movieAPIBuilder.getMovieDetailsByID(123, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logException(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        MovieResponse movieResponse = gson.fromJson(response.body().string(), MovieResponse.class);
                        Log.d(TAG, movieResponse.toString());
                    } else {
                        Log.d(TAG, response.code() + " " + response.message());
                    }
                }
            });
        } catch (Exception e) {
            logException(e);
        }
    }

    public void fetchTopRatedMovies() {
        movieAPIBuilder.getTopRatedMovies(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                logException(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    movieResponseList = new Gson().fromJson(responseString, MovieResponseList.class);
                    for (MovieResponse movieResponse : movieResponseList.getResponses()) {
                        movieResponse.setImageLink(movieResponse.getImageLink());
                        movieResponse.setBackDropImage(movieResponse.getBackDropImage());
                    }
                    isLoading =false ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieRecyclerViewAdapter.addMoviesList(movieResponseList.getResponses());
                        }
                    });
                } else {
                    Log.d(TAG, response.code() + " " + response.message());
                }
            }
        });
    }

    void logException(Exception e) {
        Log.e(TAG, e.getLocalizedMessage(), e);
        e.printStackTrace();
    }


    public static MainActivity getInstance() {
        if (mainActivity == null) {
            mainActivity = new MainActivity();
        }
        return mainActivity;
    }

    void fetchNextPage(){
        Log.d(TAG , "fetch next page");
        int pageNo = movieAPIBuilder.getPageNo();
        pageNo++;
        movieAPIBuilder.setPageNo(pageNo);
        fetchTopRatedMovies();
    }
}
