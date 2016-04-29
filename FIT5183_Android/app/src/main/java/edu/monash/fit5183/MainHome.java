package edu.monash.fit5183;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;


public class MainHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] items;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //call baidu map
       // SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Main Page");
        items = getResources().getStringArray(R.array.navigation_items);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, new Main_fragment())
                .commit();
    }



    private void exitExit()
    {
        new AlertDialog.Builder(this).setTitle("Tip !").setMessage("Do you want to exit this application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }})
                .setPositiveButton("No",null)
                .show();
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            if ((System.currentTimeMillis() - exitTime) > 1000) {
                exitTime = System.currentTimeMillis();
            } else {
                exitExit();
            }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .addToBackStack("backClick123")
                    .replace(R.id.content_frame, new Main_fragment())
                    .commit();
            setTitle("Main Page");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_home, menu);
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

    Fragment nextFragment = null;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_main)
        {
            nextFragment = new Main_fragment();
            callFragment();
            setTitle(items[0]);
            return true;
        }
        else if (id == R.id.nav_calorie) {
            nextFragment = new Calorie_fragment();
            callFragment();
            setTitle(items[1]);
            return true;
        } else if (id == R.id.nav_dailyDiet) {
            nextFragment = new Diet_fragment();
            callFragment();
            setTitle(items[2]);
            return true;
        } else if (id == R.id.nav_steps) {
            nextFragment = new Steps_fragment();
            callFragment();
            setTitle(items[3]);
            return true;

        } else if (id == R.id.nav_trackCalorie) {
            nextFragment = new TrackCalorie_fragment();
            callFragment();
            setTitle(items[4]);
            return true;
        } else if (id == R.id.nav_report) {
            nextFragment = new Report_fragment();
            callFragment();
            setTitle(items[5]);
            return true;
        } else if (id == R.id.nav_map) {
            nextFragment = new Map_fragment();
            callFragment();
            setTitle(items[6]);
            return true;
        }
        else if(id == R.id.nav_setting){
            new AlertDialog.Builder(this).setTitle("Tip !").setMessage("We don't have this function now... (ToT)")
                    .setPositiveButton("Yes",null)
                    .show();
        }
        else if (id == R.id.nav_exit){
            new AlertDialog.Builder(this).setTitle("Tip !").setMessage("Do you want to exit this application?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }})
                    .setPositiveButton("No",null)
                    .show();
        }
        return true;
    }

    public void callFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack("backClick")
                .replace(R.id.content_frame, nextFragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

//    public void restoreActionBar() {
//        getSupportActionBar();
//        //getSupportActionBar().setNavigationMode(toolbar.i);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle(items[3]);
//    }

}
