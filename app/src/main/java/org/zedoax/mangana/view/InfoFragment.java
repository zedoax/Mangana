package org.zedoax.mangana.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.zedoax.MangaPull;
import org.zedoax.mangana.R;
import org.zedoax.mangana.model.InfoAdapter;
import org.zedoax.mangana.objects.MangaItem;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class InfoFragment extends Fragment {
    private MangaItem mangaItem;
    private TextView titleView;
    private TextView directoriesView;
    private TextView authorView;
    private TextView rankView;
    private TextView descriptionView;
    private ImageView mImageView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private InfoAdapter mInfoAdapter;

    private OnInfoCallbackListener onInfoCallbackListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finds and assigns the Image View
        // mImageView = (ImageView)getView().findViewById(R.id.info_cover);

        // Finds and assigns the Text View
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.info_recycler_view);

        // Create the Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mInfoAdapter = new InfoAdapter(mangaItem, mangaItem.getChapters());
        mRecyclerView.setAdapter(mInfoAdapter);

        onRefresh();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);

    }

    public void setMangaItem(MangaItem mangaItem) {
        this.mangaItem = mangaItem;

    }

    public void refresh() {
        // Loads the cover image
        // Picasso.with(mImageView.getContext()).clearCache();
        // Picasso.with(mImageView.getContext()).load(mangaItem.getCoverUrl()).into(mImageView);


        MangaPull mp = new MangaPull();
        mangaItem.setChapters(mp.request_chapters(mangaItem.getIndex()));
        mInfoAdapter.update(mangaItem, mangaItem.getChapters());

        // Load the text fields out of data from the MangaItem
        /*
        titleView.setText(mangaItem.getTitle());
        directoriesView.setText(mangaItem.getDirectories());
        authorView.setText(mangaItem.getAuthor());
        rankView.setText(mangaItem.getRank());
        descriptionView.setText(mangaItem.getDescription());
        */

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

    public static abstract class OnInfoCallbackListener {
        public abstract void onCallback();
    }

}
