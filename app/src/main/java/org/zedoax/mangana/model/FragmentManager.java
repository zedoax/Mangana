package org.zedoax.mangana.model;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.zedoax.mangana.R;
import org.zedoax.mangana.view.InfoFragment;
import org.zedoax.mangana.view.MangaFragment;
import org.zedoax.mangana.view.ViewerFragment;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class FragmentManager {

    public static int MANGA_FRAGMENT = 0;
    public static int INFO_FRAGMENT = 1;
    public static int VIEWER_FRAGMENT = 2;

    private android.support.v4.app.FragmentManager fragmentManager;
    private MangaFragment mangaFragment;
    private InfoFragment infoFragment;
    private ViewerFragment viewerFragment;

    public FragmentManager(Context context) {
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        mangaFragment = new MangaFragment();
        infoFragment = new InfoFragment();
        viewerFragment = new ViewerFragment();

    }

    public void changeFragment(int fragment_id) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        if(fragment_id == MANGA_FRAGMENT) {
            transaction.replace(R.id.container, mangaFragment);

        } else if(fragment_id == INFO_FRAGMENT) {
            transaction.replace(R.id.container, infoFragment);

        } else if(fragment_id == VIEWER_FRAGMENT) {
            transaction.replace(R.id.container, viewerFragment);

        } else {
            transaction.replace(R.id.container, mangaFragment);

        }

        // Sets the stackback.  Might be changed later?
        transaction.addToBackStack(null);

        // Commit the Transaction (Shows the new Fragment)
        transaction.commit();

    }

}
