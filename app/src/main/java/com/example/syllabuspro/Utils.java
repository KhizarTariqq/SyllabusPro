package com.example.syllabuspro;

import android.content.Context;

public class Utils {
    public static int dpToPx(Context context, int dp) {
        /**
         * Converts a value in density-independent pixels (dp) to pixels (px)
         * based on the current screen density.
         *
         * @param context The context used to access display metrics.
         * @param dp The value in dp to convert.
         * @return The converted value in pixels.
         */

        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
