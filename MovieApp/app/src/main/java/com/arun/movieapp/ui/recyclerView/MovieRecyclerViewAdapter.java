package com.arun.movieapp.ui.recyclerView;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.arun.movieapp.R;
import com.arun.movieapp.Utils.Const;
import com.arun.movieapp.model.MovieResponse;
import com.arun.movieapp.ui.MovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();

    private Activity activity;
    private List<MovieResponse> movieResponses;


    public MovieRecyclerViewAdapter() {
        Log.d(TAG , "object created ");
    }


    public MovieRecyclerViewAdapter(Activity activity, List<MovieResponse> responses) {
        this.activity = activity;
        this.movieResponses = responses;
    }


    @NonNull
    @Override
    public MovieRecyclerViewAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
        View view = inflater.inflate(R.layout.movie_layout , parent ,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.MovieViewHolder holder, int position) {
        try {

            Log.d(TAG, "onBindViewHolder: " + position);
            MovieResponse response = movieResponses.get(position);
            holder.movieTitle.setText(response.getMovieName());
            holder.ratingBar.setRating(Float.parseFloat(response.getRating()));
            Picasso.get().load(response.getImageLink()).into(holder.movieImage);


            holder.movieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity , MovieActivity.class);
                    intent.putExtra(Const.MOVIE_TITLE, response.getMovieName());
                    intent.putExtra(Const.MOVIE_DESC , response.getDescription());
                    intent.putExtra(Const.MOVIE_IMAGE_LINK ,response.getImageLink() );
                    intent.putExtra(Const.MOVIE_BACKDROP_IMAGE_LINK , response.getBackDropImage());
                    intent.putExtra(Const.MOVIE_ID , response.getMovieId());
                    intent.putExtra(Const.MOVIE_RATING , response.getRating());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right , R.anim.no_animation);

                }
            });
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getLocalizedMessage(),e );
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return movieResponses.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        private ImageView movieImage;
        private TextView  movieTitle;
        private TextView movieDesc ;
        private RatingBar ratingBar;

        private ConstraintLayout movieLayout ;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            ratingBar = itemView.findViewById(R.id.rating);
            movieLayout = itemView.findViewById(R.id.parent_movie_layout);
        }
    }

    public void addMoviesList(List<MovieResponse> responses){
        Log.d(TAG , responses.toString());

        movieResponses.addAll(responses);
        notifyDataSetChanged();
    }
}
