package org.zedoax.mangana.model;

import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;

import org.zedoax.mangana.view.ViewerFragment;

/**
 * Created by Zedoax on 2/2/2017.
 */

public class ViewerAdapter extends FragmentPagerAdapter {
    private String[] pages;

    public ViewerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.pages = new String[0];

    }

    public void update(String[] pages) {
        this.pages = pages;

    }

    @Override
    public Fragment getItem(int position) {
        return ViewerFragment.getInstance(pages[position]);
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page: " + position;
    }
}
