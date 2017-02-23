package org.zedoax.mangana.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.zedoax.MangaPull;
import org.zedoax.mangana.R;
import org.zedoax.mangana.view.ViewerAdapter;
import org.zedoax.mangana.view.generics.ViewPager;

/**
 * Created by Zedoax on 2/3/2017.
 */

public class ViewerFragment extends Fragment implements ViewerAdapter.OnLastPageListener {
    private String index;
    private String chapter;
    private String[] pages;

    private ViewPager mViewPager;
    private ViewerAdapter mViewerAdapter;
    private FloatingActionButton mFAB;

    private OnFABClickedListener onFABClickedListener;
    private boolean lastPage = false;

    public ViewerFragment() {
        pages = new String[0];

    }

    public void finish() {
        mViewerAdapter.update(pages);
        mViewerAdapter.notifyDataSetChanged();

        // mSwipeRefreshLayout.setRefreshing(false);

    }

    public void update(final String index, final String chapter) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        pages = MangaPull.getInstance().request_chapter(index, chapter);
                        mViewPager.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }
                        );
                    }
                }
        ).start();


    }

    @Override
    public void onLastPage(boolean isOnLastPage) {
        if(isOnLastPage) {
            mFAB.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(mFAB.getContext(), R.anim.slide_in_right);
            mFAB.startAnimation(animation);
        } else {
            if(lastPage) {
                Animation animation = AnimationUtils.loadAnimation(mFAB.getContext(), R.anim.slide_out_right);
                mFAB.startAnimation(animation);
            }
            mFAB.setVisibility(View.INVISIBLE);
        }
        lastPage = isOnLastPage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);

        Bundle args = this.getArguments();
        this.index = args.getString("index");
        this.chapter = args.getString("chapter");

        update(index, chapter);

        // Finds and Initializes the Floating Action Button for Next actions
        mFAB = (FloatingActionButton)view.findViewById(R.id.viewer_fab);
        mFAB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFABClickedListener.onNextClicked(index, chapter);
                    }
                }
        );

        mViewPager = (ViewPager) view.findViewById(R.id.viewer_pager);

        mViewerAdapter = new ViewerAdapter(getFragmentManager(), pages);
        mViewerAdapter.setOnLastPageListener(this);
        mViewPager.addOnPageChangeListener(
                new android.support.v4.view.ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        onLastPage((position == (pages.length - 1)));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );

        mViewPager.setAdapter(mViewerAdapter);

        mViewPager.setOffscreenPageLimit(3);

        return view;

    }

    public void setOnFABClickedListener(OnFABClickedListener listener) {
        this.onFABClickedListener = listener;
    }

    public interface OnFABClickedListener {
        void onNextClicked(String index, String chapter);

    }

}
