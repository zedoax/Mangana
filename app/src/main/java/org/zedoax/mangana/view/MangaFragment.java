package org.zedoax.mangana.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class MangaFragment extends Fragment {
    ArrayList<MangaItem> manga = new ArrayList<>();
    RecyclerView mRecyclerView;
    MangaAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finds the Recycler View in the xml layout
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.manga_list);

        // Create the Linear Layout Manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create and set the Adapter
        mAdapter = new MangaAdapter(manga.toArray(new MangaItem[manga.size()]));
        mRecyclerView.setAdapter(mAdapter);

        refreshView();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manga_fragment, container, false);
    }

    private void refreshView() {
        final MangaPull mp = new MangaPull();
        //////////////////////////////////// Testing Ability for Cards ////////////////////////////////////////////////////////
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (org.zedoax.objects.MangaItem m: mp.request_mangae(null)) {
                            manga.add(new MangaItem(m));

                        }
                        /////////// Temporarily in this method.  Later it will be in Refresh Spinny Spin //////////////////////

                        mRecyclerView.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        onFinish();
                                        System.out.println("UPDATE COMPLETED IN MANGAFRAGMENT");
                                    }
                                }
                        );


                    }

                }

        ).start();



    }

    private void onFinish() {
        mAdapter.update(manga);

    }

}
