package com.arun.movieapp.ui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.arun.movieapp.R;
import com.arun.movieapp.Utils.Utils;
import com.arun.movieapp.database.MovieDao;
import com.arun.movieapp.database.MovieDatabase;
import com.arun.movieapp.dto.MovieDTO;
import com.arun.movieapp.model.data.MovieData;
import com.arun.movieapp.model.responses.MovieResponseList;
import com.arun.movieapp.model.responses.MovieResponse;
//import com.arun.movieapp.listeners.LastItemReachedCallback;
//import com.arun.movieapp.receivers.WifiStateReceiver;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context context;
    private MovieAPIBuilder movieAPIBuilder;
    private Gson gson;
    private MovieResponseList movieResponseList;

    private List<MovieResponse> movieResponses;

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;

    private static MainActivity mainActivity;

    private List<MovieResponse> movieDetailList;

    boolean isLoading = false;

    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setFullScreen();
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

    void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    void initAdapter() {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, new ArrayList<>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }


    void populateData() {

    }

    void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && movieResponses != null &&
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() == movieResponses.size() - 1) {
                        Log.d(TAG, "scroll last ");
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


    void loadMore() {
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

    private void fetchMovieFromAPI() {
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
                    movieResponses = movieResponseList.getResponses();


                    for (MovieResponse movieResponse : movieResponses) {
                        movieResponse.setImageLink(movieResponse.getImageLink());
                        movieResponse.setBackDropImage(movieResponse.getBackDropImage());
                    }
                    isLoading = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieRecyclerViewAdapter.addMoviesList(movieResponses);
                        }
                    });
                    new InsertMovieThread().start();
                } else {
                    Log.d(TAG, response.code() + " " + response.message());
                }
            }
        });
    }

    public void fetchTopRatedMovies() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Log.d(TAG, "network availale ");
            fetchMovieFromAPI();
        } else {
            fetchMovieDetailsFromRoom();
        }
    }

    private void fetchMovieDetailsFromRoom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                        MovieDatabase.class, "hamro_cinema").build();
                MovieDao movieDao = db.getMovieDao();
                List<MovieData> movieDataList = movieDao.getTopRatedMovies();

                List<MovieResponse> movieResponses1 = MovieDTO.mapToMovieResponseList(movieDataList);
                movieResponses = movieResponses1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieRecyclerViewAdapter.addMoviesList(movieResponses);
                    }
                });
            }
        }).start();

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

    void fetchNextPage() {
        Log.d(TAG, "fetch next page");
        int pageNo = movieAPIBuilder.getPageNo();
        pageNo = pageNo + 1;
        Log.d(TAG, "fetchNextPage: " + pageNo);
        movieAPIBuilder.setPageNo(pageNo);
        fetchTopRatedMovies();
    }


    class InsertMovieThread extends Thread {
        @Override
        public void run() {
            super.run();
            List<MovieData> movieDataList = MovieDTO.mapToMovieDataList(movieResponseList.getResponses());
            MovieDatabase db = Room.databaseBuilder(getApplicationContext(),
                    MovieDatabase.class, "hamro_cinema").build();
            MovieDao movieDao = db.getMovieDao();

            Log.d(TAG, "run: inserted into the database");
            movieDao.insert(movieDataList);
        }
    }

}
