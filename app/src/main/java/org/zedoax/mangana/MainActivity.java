package org.zedoax.mangana;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.zedoax.MangaPull;

import org.zedoax.mangana.model.FragmentManager;
import org.zedoax.mangana.view.MangaFragment;

public class MainActivity extends AppCompatActivity {
    MangaPull mpull = new MangaPull();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*// Creates the initial Fragment Transaction to open manga page
        // TODO: Replace with Fragment Manager
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Fragment Transaction that moves new Manga Fragment to the Frame Layout container
        transaction.replace(R.id.container, new MangaFragment());
        transaction.addToBackStack(null);

        // Commit the Transaction (Shows the new Fragment)
        transaction.commit();*/

        FragmentManager fragmentManager = new FragmentManager(this);
        fragmentManager.changeFragment(FragmentManager.MANGA_FRAGMENT);


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
}
