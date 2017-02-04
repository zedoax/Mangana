package org.zedoax.mangana.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zedoax.MangaPull;
import org.zedoax.mangana.R;
import org.zedoax.mangana.model.ViewerAdapter;

/**
 * Created by Zedoax on 2/3/2017.
 */

public class ViewerFragment extends Fragment implements ViewerAdapter.OnLastPageListener {
    private String index;
    private String chapter;

    private ViewPager mViewPager;
    private ViewerAdapter mViewerAdapter;
    private FloatingActionButton mFAB;

    private String[] pages;

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
        } else {
            mFAB.setVisibility(View.INVISIBLE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);

        Bundle args = this.getArguments();
        this.index = args.getString("index");

        if(this.chapter != null) {
            if (!this.chapter.equals(args.getString("chapter"))) {
                this.chapter = args.getString("chapter");
                update(index, chapter);
            } else {
                finish();
            }
        } else {
            this.chapter = args.getString("chapter");
            update(index, chapter);
        }

        // Finds and Initializes the Floating Action Button for Next actions
        mFAB = (FloatingActionButton)view.findViewById(R.id.viewer_fab);

        mViewPager = (ViewPager) view.findViewById(R.id.viewer_pager);

        mViewerAdapter = new ViewerAdapter(getFragmentManager(), pages);
        mViewerAdapter.setOnLastPageListener(this);

        mViewPager.setAdapter(mViewerAdapter);

        mViewPager.setOffscreenPageLimit(3);

        return view;

    }



}
