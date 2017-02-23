package org.zedoax.mangana.view.generics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zedoax on 2/5/2017.
 */

public class ViewPager extends android.support.v4.view.ViewPager {

    public ViewPager(Context context) {
        super(context);
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof TouchImageView) {
            return ((TouchImageView) v).canScrollHorizontallyFroyo(-dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

}
