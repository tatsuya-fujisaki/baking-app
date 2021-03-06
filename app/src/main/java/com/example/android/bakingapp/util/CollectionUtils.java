package com.example.android.bakingapp.util;

import android.text.TextUtils;
import android.util.SparseIntArray;

public class CollectionUtils {
    public static boolean containsKey(SparseIntArray xs, int key) {
        return xs.indexOfKey(key) >= 0;
    }

    @SafeVarargs
    public static <T> T coalesce(T... xs) {
        for (T x : xs) {
            if (x != null) {
                return x;
            }
        }

        return null;
    }

    public static String coalesceString(String... ss) {
        for (String s : ss) {
            if (!TextUtils.isEmpty(s)) {
                return s;
            }
        }

        return null;
    }
}