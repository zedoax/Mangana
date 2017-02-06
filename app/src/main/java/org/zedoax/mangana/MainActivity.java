package org.zedoax.mangana;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.zedoax.MangaPull;

import org.zedoax.mangana.model.FragmentManager;
import org.zedoax.mangana.objects.MangaItem;
import org.zedoax.mangana.view.fragments.InfoFragment;
import org.zedoax.mangana.view.fragments.MangaFragment;
import org.zedoax.mangana.view.fragments.ViewerFragment;

public class MainActivity extends AppCompatActivity implements MangaFragment.OnMangaCallbackListener, InfoFragment.OnInfoCallbackListener, ViewerFragment.OnFABClickedListener {

    private FragmentManager fragmentManager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = FragmentManager.getInstance(this);
        // ((InfoFragment)fragmentManager.getFragment(FragmentManager.INFO_FRAGMENT))
        ((MangaFragment)fragmentManager.getFragment(FragmentManager.MANGA_FRAGMENT))
                .setOnMangaCallbackListener(this);
        ((InfoFragment)fragmentManager.getFragment(FragmentManager.INFO_FRAGMENT))
                .setOnInfoCallbackListener(this);

        fragmentManager.changeFragment(new MangaItem[0]);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCallback(String index, String chapter) {
        fragmentManager.changeFragment(index, chapter);
        ((ViewerFragment) fragmentManager.getFragment(FragmentManager.VIEWER_FRAGMENT)).setOnFABClickedListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragmentManager.goBack();

    }

    @Override
    public void onCallback(MangaItem mangaItem) {
        fragmentManager.changeFragment(mangaItem);
    }

    @Override
    public void onNextClicked(String index, String chapter) {
        String[] chapters = ((InfoFragment) fragmentManager.getFragment(FragmentManager.INFO_FRAGMENT)).getChapters();
        for (int i = 1; i < chapters.length - 1; i++) {
            if(chapters[i].equals(chapter)) {
                onBackPressed();
                fragmentManager.changeFragment(index, chapters[i - 1]);
                ((ViewerFragment) fragmentManager.getFragment(FragmentManager.VIEWER_FRAGMENT)).setOnFABClickedListener(this);


            }
        }

    }

}
