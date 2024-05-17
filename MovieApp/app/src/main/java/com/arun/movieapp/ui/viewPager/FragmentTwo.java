package com.arun.movieapp.ui.viewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.arun.movieapp.R;

public class FragmentTwo extends Fragment {

    private static final String title = "Horror";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view ;
        view = inflater.inflate(R.layout.fragment_two, container , false);
        return view ;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }








}
