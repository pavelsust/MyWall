package com.danimahardhika.android.helpers.core;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/*
 * Android Helpers
 *
 * Copyright (c) 2017 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ColorHelper {

    /**
     * @param color can be ColorInt or ColorRes
     */
    @ColorInt
    public static int get(@NonNull Context context, int color) {
        try {
            return ContextCompat.getColor(context, color);
        } catch (Exception e) {
            return color;
        }
    }

    public static void setStatusBarColor(@NonNull Context context, @ColorInt int color) {
        setStatusBarColor(context, color, false);
    }

    public static void setStatusBarColor(@NonNull Context context, @ColorInt int color, boolean transparent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (transparent) ((Activity) context).getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            ((Activity) context).getWindow().setStatusBarColor(color);
        }
    }

    public static void setNavigationBarColor(@NonNull Context context, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) context).getWindow().setNavigationBarColor(color);
        }
    }

    @ColorInt
    public static int getAttributeColor(Context context, @AttrRes int attr) {
        if (context == null) {
            Log.e("ColorHelper", "getAttributeColor() context is null");
            return Color.WHITE;
        }

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    @ColorInt
    public static int getTitleTextColor(@ColorInt int color) {
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return (darkness < 0.35) ? getDarkerColor(color, 0.25f) : Color.WHITE;
    }

    @ColorInt
    public static int getBodyTextColor(@ColorInt int color) {
        int title = getTitleTextColor(color);
        return setColorAlpha(title, 0.7f);
    }

    @ColorInt
    public static int getDarkerColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float transparency) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= transparency;
        return Color.HSVToColor(hsv);
    }

    @ColorInt
    public static int setColorAlpha(@ColorInt int color, @FloatRange(from = 0.0f, to = 1.0f) float alpha) {
        int alpha2 = Math.round(Color.alpha(color) * alpha);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha2, red, green, blue);
    }

    public static ColorStateList getColorStateList(@ColorInt int color) {
        int[][] states = new int[][] {
                new int[] {android.R.attr.state_pressed},
                new int[] {android.R.attr.state_focused},
                new int[] {}
        };
        int[] colors = new int[] {
                ColorHelper.getDarkerColor(color, 0.8f),
                ColorHelper.getDarkerColor(color, 0.8f),
                color
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList getCheckedColorStateList(@ColorInt int unchecked, @ColorInt int checked) {
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked},
                new int[] {android.R.attr.state_checked},
        };

        int[] colors = new int[] {
                unchecked,
                checked
        };
        return new ColorStateList(states, colors);
    }

    public static boolean isLightColor(@ColorInt int color) {
        int title = ColorHelper.getTitleTextColor(color);
        return title != Color.WHITE;
    }

    public static void setupStatusBarIconColor(Context context) {
        if (context == null) {
            Log.e("ColorHelper", "setupStatusBarIconColor() context is null");
            return;
        }
        int color = getAttributeColor(context, R.attr.colorPrimaryDark);
        setupStatusBarIconColor(context, isLightColor(color));
    }

    public static void setupStatusBarIconColor(Context context, boolean isLightToolbar) {
        if (context == null) {
            Log.e("ColorHelper", "setupStatusBarIconColor() context is null");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (((AppCompatActivity) context).getWindow() == null) {
                Log.e("ColorHelper", "setupStatusBarIconColor() getWindow() returns null");
                return;
            }

            View view = ((AppCompatActivity) context).getWindow().getDecorView();
            if (view != null) {
                if (isLightToolbar){
                    view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    return;
                }

                view.setSystemUiVisibility(0);
            }
        }
    }

    public static boolean isValidColor(String string) {
        try {
            Color.parseColor(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
