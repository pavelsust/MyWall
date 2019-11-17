package com.danimahardhika.android.helpers.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

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

public class WallViewHelper {

    /** Setup toolbar for activity that have both translucent status and navigation bar */
    public static void setupToolbar(@NonNull Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Context context = WallContextHelper.getBaseContext(toolbar);
            int statusBarSize = WallWindowHelper.getStatusBarHeight(context);

            if (toolbar.getLayoutParams() instanceof AppBarLayout.LayoutParams) {
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                params.setMargins(
                        params.leftMargin,
                        params.topMargin + statusBarSize,
                        params.rightMargin,
                        params.bottomMargin);
                return;
            }

            toolbar.setPadding(
                    toolbar.getPaddingLeft(),
                    toolbar.getPaddingTop() + statusBarSize,
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom()
            );
            toolbar.getLayoutParams().height = getToolbarHeight(context) + statusBarSize;
        }
    }

    public static int getToolbarHeight(@NonNull Context context) {
        TypedValue typedValue = new TypedValue();
        int[] actionBarSize = new int[] { R.attr.actionBarSize };
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(typedValue.data, actionBarSize);
        int size = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return size;
    }

    public static void resetSpanCount(@NonNull RecyclerView recyclerView, int spanCount) {
        try {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                manager.setSpanCount(spanCount);
                manager.requestLayout();
            } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                manager.setSpanCount(spanCount);
                manager.requestLayout();
            }
        } catch (Exception ignored) {}
    }

    public static void setSearchViewTextColor(@Nullable View view, @ColorInt int textColor) {
        if (view != null) {
            int hintColor = WallColorHelper.setColorAlpha(textColor, 0.5f);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(textColor);
                ((TextView) view).setHintTextColor(hintColor);
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    setSearchViewTextColor(viewGroup.getChildAt(i), textColor);
                }
            }
        }
    }

    public static void setSearchViewBackgroundColor(@Nullable View view, int color) {
        if (view != null) {
            View background = view.findViewById(R.id.search_plate);
            if (background != null) {
                Context context = WallContextHelper.getBaseContext(view);
                background.setBackgroundColor(WallColorHelper.get(context, color));
            }
        }
    }

    public static void setSearchViewSearchIcon(@Nullable View view, @DrawableRes int resId) {
        if (view == null) return;

        Context context = WallContextHelper.getBaseContext(view);
        Drawable drawable = DrawableHelper.get(context, resId);
        setSearchViewSearchIcon(view, drawable);
    }

    public static void setSearchViewSearchIcon(@Nullable View view, @Nullable Drawable drawable) {
        if (view == null) return;

        ImageView searchIcon = view.findViewById(
                R.id.search_mag_icon);
        if (searchIcon == null) return;

        if (drawable == null) {
            ViewGroup viewGroup = (ViewGroup) searchIcon.getParent();
            if (viewGroup == null) return;

            viewGroup.removeView(searchIcon);
            viewGroup.addView(searchIcon);

            searchIcon.setAdjustViewBounds(true);
            searchIcon.setMaxWidth(0);
            searchIcon.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        searchIcon.setImageDrawable(drawable);
    }

    public static void setSearchViewCloseIcon(@Nullable View view, @DrawableRes int resId) {
        if (view == null) return;

        Context context = WallContextHelper.getBaseContext(view);
        Drawable drawable = DrawableHelper.get(context, resId);
        setSearchViewCloseIcon(view, drawable);
    }

    public static void setSearchViewCloseIcon(@Nullable View view, @Nullable Drawable drawable) {
        if (view == null) return;

        ImageView closeIcon = view.findViewById(
                R.id.search_close_btn);
        if (closeIcon != null) {
            if (drawable == null) {
                ViewGroup viewGroup = (ViewGroup) closeIcon.getParent();
                if (viewGroup == null) return;

                viewGroup.removeView(closeIcon);
                viewGroup.addView(closeIcon);

                closeIcon.setAdjustViewBounds(true);
                closeIcon.setMaxWidth(0);
                closeIcon.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
            }

            closeIcon.setImageDrawable(drawable);
        }
    }

    public static void hideNavigationViewScrollBar(@NonNull NavigationView navigationView) {
        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null)
            navigationMenuView.setVerticalScrollBarEnabled(false);
    }
}
