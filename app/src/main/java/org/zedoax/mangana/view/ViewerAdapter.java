package org.zedoax.mangana.view;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.zedoax.mangana.R;
import org.zedoax.mangana.view.generics.TouchImageView;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Zedoax on 2/2/2017.
 */

public class ViewerAdapter extends FragmentStatePagerAdapter {
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    /*
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page: " + position;
    }
    */

    public void setOnLastPageListener(OnLastPageListener listener) {
        this.onLastPageListener = listener;

    }

    public static class ViewerHolder extends Fragment {
        private String page;

        private Matrix matrix;
        private ScaleGestureDetector scaleGestureDetector;

        private TouchImageView imageView;

        public static ViewerHolder getInstance(String page) {
            ViewerHolder viewerHolder = new ViewerHolder();
            Bundle args = new Bundle();
            args.putString("page", page);
            viewerHolder.setArguments(args);
            return viewerHolder;

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
            imageView = (TouchImageView) view.findViewById(R.id.viewer_image);
            scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
            matrix = new Matrix();
            imageView.setOnTouchListener(
                    new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            scaleGestureDetector.onTouchEvent(event);
                            return true;
                        }
                    }
            );

            Picasso.with(view.getContext()).load(page).error(R.drawable.error_drawable).into(imageView);
            return view;
        }



        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
                matrix.setScale(scaleFactor, scaleFactor);
                imageView.setImageMatrix(matrix);
                return true;
            }
        }

    }

    public interface OnLastPageListener {
        void onLastPage(boolean isOnLastPage);
    }
}
