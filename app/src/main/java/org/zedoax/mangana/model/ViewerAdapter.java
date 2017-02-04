package org.zedoax.mangana.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.zedoax.mangana.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Zedoax on 2/2/2017.
 */

public class ViewerAdapter extends FragmentPagerAdapter {
    private String[] pages;

    private OnLastPageListener onLastPageListener;

    public ViewerAdapter(FragmentManager fragmentManager, String[] pages) {
        super(fragmentManager);
        this.pages = pages;

    }

    public void update(String[] pages) {
        this.pages = pages;

    }

    @Override
    public Fragment getItem(int position) {
        if(position == (pages.length - 1)) {
            onLastPageListener.onLastPage(true);
        }
        else {
            onLastPageListener.onLastPage(false);

        }
        return ViewerHolder.getInstance(pages[position]);
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page: " + position;
    }

    public void setOnLastPageListener(OnLastPageListener listener) {
        this.onLastPageListener = listener;

    }

    public static class ViewerHolder extends Fragment {
        String page;

        public static ViewerHolder getInstance(String page) {
            ViewerHolder viewerFragment = new ViewerHolder();
            Bundle args = new Bundle();
            args.putString("page", page);
            viewerFragment.setArguments(args);
            return viewerFragment;

        }



        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = this.getArguments();
            this.page = args.getString("page");
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.view_item, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.viewer_image);
            Picasso.with(view.getContext()).load(page).into(imageView);
            return view;
        }

    }

    public interface OnLastPageListener {
        void onLastPage(boolean isOnLastPage);
    }
}
