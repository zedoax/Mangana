package org.zedoax.mangana.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.zedoax.mangana.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class ViewerFragment extends Fragment {
    String page;

    public static ViewerFragment getInstance(String page) {
        ViewerFragment viewerFragment = new ViewerFragment();
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
