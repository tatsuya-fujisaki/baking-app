package com.example.android.bakingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.bakingapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public final class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    public static RequestCreator picasso(String path){
        return Picasso.get()
                .load(path)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error_24px);
    }
}