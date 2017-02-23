package org.zedoax.mangana.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.zedoax.mangana.R;
import org.zedoax.mangana.objects.MangaItem;
import org.zedoax.mangana.view.fragments.InfoFragment;
import org.zedoax.mangana.view.fragments.MangaFragment;
import org.zedoax.mangana.view.fragments.ViewerFragment;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class FragmentManager {

    private static FragmentManager instance;
    public static int MANGA_FRAGMENT = 0;
    public static int INFO_FRAGMENT = 1;
    public static int VIEWER_FRAGMENT = 2;

    private int current_fragment;

    private android.support.v4.app.FragmentManager fragmentManager;
    private MangaFragment mangaFragment;
    private InfoFragment infoFragment;
    private ViewerFragment viewerFragment;

    private FragmentManager(Context context) {
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        mangaFragment = new MangaFragment();
        infoFragment = new InfoFragment();

    }

    public void changeFragment(String index, String chapter) {
        // Set the arguments for the Info Fragment
        viewerFragment = new ViewerFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        args.putString("chapter", chapter);
        viewerFragment.setArguments(args);
        current_fragment = FragmentManager.VIEWER_FRAGMENT;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        transaction.replace(R.id.container, viewerFragment)

                // Sets the stackback.  Might be changed later?
                .addToBackStack(null)

                // Commit the Transaction (Shows the new Fragment)
                .commit();

    }

    public void changeFragment(MangaItem mangaItem) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Set the arguments for the Info Fragment
        Bundle args = new Bundle();
        args.putSerializable("manga", mangaItem);
        infoFragment.setArguments(args);
        current_fragment = FragmentManager.INFO_FRAGMENT;

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        transaction.replace(R.id.container, getFragment(FragmentManager.INFO_FRAGMENT))

                // Queue replacing the current fragment with the next
                // .replace(R.id.container, getFragment(FragmentManager.INFO_FRAGMENT))

                // Sets the stackback.  Might be changed later?
                .addToBackStack(null)

                // Commit the Transaction (Shows the new Fragment)
                .commit();

    }

    public void changeFragment(MangaItem[] manga) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        Bundle args = new Bundle();
        args.putSerializable("mangae", manga);
        mangaFragment.setArguments(args);
        current_fragment = FragmentManager.MANGA_FRAGMENT;

        transaction.replace(R.id.container, getFragment(FragmentManager.MANGA_FRAGMENT))

                // Sets the stackback.  Might be changed later?
                .addToBackStack(null)

                // Commit the Transaction (Shows the new Fragment)
                .commit();

    }

    public void changeFragment(int fragment_id) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        transaction.replace(R.id.container, getFragment(fragment_id))

        // Sets the stackback.  Might be changed later?
        .addToBackStack(null)

        // Commit the Transaction (Shows the new Fragment)
        .commit();

    }

    public Fragment getFragment(int fragment_id) {
        switch (fragment_id) {
            case 2:
                return viewerFragment;

            case 1:
                return infoFragment;

            default:
                return mangaFragment;

        }

    }

    public int getCurrentFragment() {
        return current_fragment;
    }

    public void popStack() {
        fragmentManager.popBackStack();
    }

    public void goBack() {
        switch (current_fragment) {
            case 2:
                current_fragment = FragmentManager.INFO_FRAGMENT;
                changeFragment(FragmentManager.INFO_FRAGMENT);
                fragmentManager.popBackStack();
                break;

            case 1:
                current_fragment = FragmentManager.MANGA_FRAGMENT;
                changeFragment(FragmentManager.MANGA_FRAGMENT);
                fragmentManager.popBackStack();
                break;

        }

    }

    public static FragmentManager getInstance(Context context) {
        if (instance==null) {
            instance = new FragmentManager(context);

        }
        return instance;

    }

}
