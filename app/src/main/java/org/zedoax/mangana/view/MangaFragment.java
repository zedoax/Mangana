package org.zedoax.mangana.view;

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
import org.zedoax.mangana.model.MangaAdapter;
import org.zedoax.mangana.objects.MangaItem;

import java.util.ArrayList;

/**
 * Created by Zedoax on 1/29/2017.
 */

public class MangaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MangaAdapter.OnClickListener {
    private ArrayList<MangaItem> manga = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MangaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnMangaCallbackListener onMangaCallbackListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finds the Recycler View in the xml layout
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.manga_list);

        // Finds and Initializes the Swipey Swipe Refresh Thingy
        mSwipeRefreshLayout = (SwipeRefreshLayout)getView().findViewById(R.id.manga_swipe_refresh);
        ((SwipeRefreshLayout)getView().findViewById(R.id.manga_swipe_refresh)).setOnRefreshListener(this);

        // Create the Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create and set the Adapter
        mAdapter = new MangaAdapter(manga.toArray(new MangaItem[manga.size()]));
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        onRefresh();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manga_fragment, container, false);

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
        //////////////////////////////////// Testing Ability for Cards ////////////////////////////////////////////////////////
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        manga.clear();
                        for (org.zedoax.objects.MangaItem m: mp.request_mangae(null)) {
                            manga.add(new MangaItem(m));

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
    public void onClick(int position) {
        onMangaCallbackListener.onCallback(mAdapter.get(position));
    }

    public interface OnMangaCallbackListener {
        void onCallback(MangaItem mangaItem);
    }

}
