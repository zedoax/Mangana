package org.zedoax.mangana.model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.zedoax.mangana.R;
import org.zedoax.mangana.objects.MangaItem;
import org.zedoax.mangana.view.InfoFragment;
import org.zedoax.mangana.view.MangaFragment;
import org.zedoax.mangana.view.ViewerFragment;
import org.zedoax.mangana.view.ViewerFragmentActivity;

/**
 * Created by Zedoax on 1/30/2017.
 */

public class FragmentManager {

    private static FragmentManager instance;
    public static int MANGA_FRAGMENT = 0;
    public static int INFO_FRAGMENT = 1;

    private android.support.v4.app.FragmentManager fragmentManager;
    private MangaFragment mangaFragment;
    private InfoFragment infoFragment;

    private FragmentManager(Context context) {
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

        mangaFragment = new MangaFragment();
        infoFragment = new InfoFragment();

    }

    public void changeFragment(int fragment_id, MangaItem mangaItem) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        if(fragment_id == INFO_FRAGMENT) {
            infoFragment.setMangaItem(mangaItem);
        }
        transaction.replace(R.id.container, getFragment(fragment_id));

        // Sets the stackback.  Might be changed later?
        transaction.addToBackStack(null);

        // Commit the Transaction (Shows the new Fragment)
        transaction.commit();

    }

    public void changeFragment(int fragment_id) {
        // Creates the initial Fragment Transaction to open manga page
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        transaction.replace(R.id.container, getFragment(fragment_id));

        // Sets the stackback.  Might be changed later?
        transaction.addToBackStack(null);

        // Commit the Transaction (Shows the new Fragment)
        transaction.commit();

    }

    public Fragment getFragment(int fragment_id) {
        switch (fragment_id) {
            case 0:
                return mangaFragment;

            case 1:
                return infoFragment;

            default:
                return mangaFragment;

        }

    }

    public static FragmentManager getInstance(Context context) {
        if (instance==null) {
            instance = new FragmentManager(context);

        }
        return instance;

    }

}
