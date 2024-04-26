package com.arun.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arun.movieapp.R;
import com.arun.movieapp.Utils.Const;
import com.arun.movieapp.listeners.MovieDataFetchListener;
import com.arun.movieapp.service.MovieAPIBuilder;
import com.arun.movieapp.service.MovieService;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    private static final String TAG = MovieActivity.class.getSimpleName();
    private String movie_title ;
    private String movie_image_link;
    private String movie_desc;
    private String movie_backdrop_image_link ;
    private String movie_id;

    private String rating ;

    private TextView movieTitle ;
    private TextView movieDesc;
    private ImageView moviePoster ;
    private ImageView moviePosterBackground ;

    private MovieService movieService;

    private YouTubePlayerView youTubePlayerView;
    private RatingBar movieRatingBar ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        movieService = new MovieService();
        initLayout();
        getIntentData();
    }

    void initLayout(){
        movieTitle = findViewById(R.id.md_movie_title);
        movieDesc = findViewById(R.id.md_movie_summary);
        movieDesc.setMovementMethod(new ScrollingMovementMethod());
        moviePoster = findViewById(R.id.md_movie_img);
        moviePosterBackground = findViewById(R.id.movie_img_background);
        youTubePlayerView = findViewById(R.id.trailer_player);
        movieRatingBar = findViewById(R.id.movie_rating);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void getIntentData() {
        Log.d(TAG, "onNewIntent: new intent");
        try {
            if(getIntent().hasExtra(Const.MOVIE_TITLE ) && getIntent().hasExtra(Const.MOVIE_DESC) && getIntent().hasExtra(Const.MOVIE_IMAGE_LINK)){
                movie_title = getIntent().getStringExtra(Const.MOVIE_TITLE);
                movie_desc = getIntent().getStringExtra(Const.MOVIE_DESC);
                movie_image_link = getIntent().getStringExtra(Const.MOVIE_IMAGE_LINK);
                movie_backdrop_image_link = getIntent().getStringExtra(Const.MOVIE_BACKDROP_IMAGE_LINK);
                movie_id =getIntent().getStringExtra(Const.MOVIE_ID);
                rating = getIntent().getStringExtra(Const.MOVIE_RATING);
//                getMovieTrailer(movie_id);
                movieService.fetchMovieYoutubeTrailerId(movie_id);

                movieDataFetchListener();
                displayData();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(),e );
        }
    }

    void displayData(){
        try {
            movieTitle.setText(movie_title);
            movieDesc.setText(movie_desc);
            movieRatingBar.setRating(Float.parseFloat(rating));
            Picasso.get().load(movie_image_link).into(moviePoster);
            Picasso.get().load(movie_backdrop_image_link).into(moviePosterBackground);
            ;
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e );
            e.printStackTrace();
        }
    }

    void movieDataFetchListener(){
        movieService.setMovieDataFetchListener(new MovieDataFetchListener() {
            @Override
            public void onTopRatedMoveDataFetched() {

            }
            @Override
            public void onMovieYoutubeTrailerIdFetched(String id) {
                Log.d(TAG , "movie id " + id);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        Log.d(TAG , "youtube ready ");
//                               youTubePlayer.
                        youTubePlayer.loadVideo(id , 0);
                    }
                });

                youTubePlayerView.addFullscreenListener(new FullscreenListener() {
                    @Override
                    public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                        Log.d(TAG ,"entered full screen");
                    }

                    @Override
                    public void onExitFullscreen() {
                        Log.d(TAG, "onExitFullscreen: ");

                    }
                });
            }
        });
    }





}
