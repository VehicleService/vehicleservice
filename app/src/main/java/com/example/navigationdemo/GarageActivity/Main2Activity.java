package com.example.navigationdemo.GarageActivity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.navigationdemo.Fragments.Home;
import com.example.navigationdemo.Fragments.Notification;
import com.example.navigationdemo.GarageFragments.DetailsG;
import com.example.navigationdemo.GarageFragments.Editprofile;
import com.example.navigationdemo.GarageFragments.Exit;
import com.example.navigationdemo.GarageFragments.MyService;
import com.example.navigationdemo.R;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setActionView(R.layout.notification_dot);

//        if (savedInstanceState == null) {
//
//            navigationView.setCheckedItem(R.id.nav_home);
//
//            setToolbarTitle("Home");
//            loadHomeFragment(0);
//        }
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
        getMenuInflater().inflate(R.menu.main, menu);

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
            Toast.makeText(Main2Activity.this,"Settings",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myservice) {
            setToolbarTitle("MyService");
            loadHomeFragment(0);
        } else if (id == R.id.nav_details) {
            setToolbarTitle("Details");
            loadHomeFragment(1);
        } else if (id == R.id.nav_editprofile) {
            setToolbarTitle("EditProfile");
            loadHomeFragment(2);
        }
        else if (id == R.id.nav_exit) {
            setToolbarTitle("Exit");
            loadHomeFragment(3);
        }else if (id == R.id.nav_share) {
            loadHomeFragment(4);
        } else if (id == R.id.nav_send) {
            loadHomeFragment(5);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    private void loadHomeFragment(final int index) {

        // set toolbar title
        // update the main content by replacing fragments
        Fragment fragment = getHomeFragment(index);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.parent, fragment);
        fragmentTransaction.commitAllowingStateLoss();

        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment(int index){
        switch (index){
            case 0:
                MyService myService=new MyService();
                return myService;

            case 1:
                DetailsG detailsG=new DetailsG();
                return detailsG;
            case 2:
                Editprofile profile =new Editprofile();
                return profile;
            case 3:
                Exit exit=new Exit();
                return exit;
            default:

                return new Home();
        }

    }
}
