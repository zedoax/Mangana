package org.zedoax.mangana.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zedoax.MangaPull;

import org.zedoax.mangana.R;
import org.zedoax.mangana.objects.MangaItem;
import org.zedoax.mangana.view.MangaAdapter;

/**
 * Created by Zedoax on 1/29/2017.
 */

public class MangaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MangaAdapter.OnClickListener {
    private MangaItem[] manga;
    private RecyclerView mRecyclerView;
    private MangaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnMangaCallbackListener onMangaCallbackListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (manga != null) {
            if (manga.length > 0) {
                onFinish();

            } else {
                onRefresh();

            }
        } else {
            onRefresh();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manga_fragment, container, false);

        Bundle args = this.getArguments();

        // Finds the Recycler View in the xml layout
        mRecyclerView = (RecyclerView)view.findViewById(R.id.manga_list);

        // Finds and Initializes the Swipey Swipe Refresh Thingy
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.manga_swype);
        ((SwipeRefreshLayout)view.findViewById(R.id.manga_swype)).setOnRefreshListener(this);

        // Create the Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create and set the Adapter
        mAdapter = new MangaAdapter((MangaItem[])args.get("mangae"));
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }

    private void onFinish() {
        mAdapter.update(manga);
        mAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        final MangaPull mp = new MangaPull();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        org.zedoax.objects.MangaItem[] mangae = mp.request_mangae(null);
                        manga = new MangaItem[mangae.length];
                        for (int i = 0; i < mangae.length; i++) {
                            manga[i] = new MangaItem(mangae[i]);
                        }

                        mRecyclerView.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        onFinish();

                                    }

                                }

                        );

                    }

                }

        ).start();

    }

    public void setOnMangaCallbackListener(OnMangaCallbackListener onMangaCallbackListener) {
        this.onMangaCallbackListener = onMangaCallbackListener;

    }

    @Override
    public void onClick(MangaItem item) {
        onMangaCallbackListener.onCallback(item);
    }

    public interface OnMangaCallbackListener {
        void onCallback(MangaItem mangaItem);
    }

}
