package com.arun.movieapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean isNetworkAvailable(Context context){
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       if(connectivityManager != null){
         NetworkInfo  networkInfo =  connectivityManager.getActiveNetworkInfo();
         return networkInfo != null && networkInfo.isAvailable();
       }
       return false;
    }
}
