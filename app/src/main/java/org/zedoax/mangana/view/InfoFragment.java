package org.zedoax.mangana.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zedoax.MangaPull;
import org.zedoax.mangana.R;
import org.zedoax.mangana.model.InfoAdapter;
import org.zedoax.mangana.objects.MangaItem;

import static android.content.ContentValues.TAG;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class InfoFragment extends Fragment implements InfoAdapter.OnClickListener {
    private MangaItem mangaItem;
    private MangaPull mp;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private InfoAdapter mInfoAdapter;

    private OnInfoCallbackListener onInfoCallbackListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);

        Bundle args = this.getArguments();
        if(savedInstanceState == null) {
            mangaItem = (MangaItem) args.getSerializable("manga");
        } else {
            mangaItem = (MangaItem) savedInstanceState.getSerializable("manga");
        }

        // Finds and assigns the Text View
        mRecyclerView = (RecyclerView)view.findViewById(R.id.info_recycler_view);

        // Create the Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize the API toolchain
        mp = new MangaPull();

        mInfoAdapter = new InfoAdapter(mangaItem, mangaItem.getChapters());
        mRecyclerView.setAdapter(mInfoAdapter);
        mInfoAdapter.setOnClickListener(this);

        if (savedInstanceState == null) {
            onRefresh();
        }

        return view;

    }

    public void refresh() {
        try {
            if(mangaItem != null) {
                mangaItem.setChapters(mp.request_chapter_urls(mangaItem.getIndex()));
                mInfoAdapter.update(mangaItem, mp.request_chapters(mangaItem.getIndex()));
            }
        } catch (Exception e) {
            Log.e(TAG, "refresh: failed to load fresh resources", e);
        }

        // Load the text fields out of data from the MangaItem

    }

    private void onFinish() {
        /*mAdapter.update(manga);
        mAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false); */

    }

    // @Override
    public void onRefresh() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        mRecyclerView.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mInfoAdapter.notifyDataSetChanged();
                                        onFinish();

                                    }

                                }

                        );

                    }

                }

        ).start();

    }

    public void setOnInfoCallbackListener(OnInfoCallbackListener onInfoCallbackListener) {
        this.onInfoCallbackListener = onInfoCallbackListener;

    }

    @Override
    public void onClick(String index, String chapter) {
        onInfoCallbackListener.onCallback(index, chapter);
    }

    public interface OnInfoCallbackListener {
        void onCallback(String index, String chapter);
    }

}
