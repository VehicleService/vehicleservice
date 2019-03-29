package com.example.navigationdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.navigationdemo.Fragments.Details;
import com.example.navigationdemo.Fragments.Editprofileuser;
import com.example.navigationdemo.Fragments.Home;
import com.example.navigationdemo.Fragments.Logout;
import com.example.navigationdemo.Fragments.Notification;
import com.example.navigationdemo.Fragments.Feedback;
import com.example.navigationdemo.Fragments.Userhistory;
import com.example.navigationdemo.Fragments.Userrecent;
import com.example.navigationdemo.Pojo.Nearbygarages;
import com.example.navigationdemo.R;
import com.example.navigationdemo.Utils.SessionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ArrayList<Nearbygarages> nearbygarages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        nearbygarages=(ArrayList<Nearbygarages>)getIntent().getSerializableExtra("Details");
       // Log.d("near",nearbygarages.get(0).getName());

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

        if (savedInstanceState == null) {

            navigationView.setCheckedItem(R.id.nav_home);

            setToolbarTitle("Home");
            loadHomeFragment(0);
        }

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
            Toast.makeText(MainActivity.this,"Settings",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setToolbarTitle("Home");
            loadHomeFragment(0);
        }  else if (id == R.id.nav_history) {
            setToolbarTitle("History");
            loadHomeFragment(1);
        } else if (id == R.id.nav_editprofileuser) {
            setToolbarTitle("EditProfile");
            loadHomeFragment(2);
        }

        else if (id == R.id.nav_feedback) {
            setToolbarTitle("Feedback");
            loadHomeFragment(3);
        }


        else if (id == R.id.nav_logout) {
            setToolbarTitle("LogOut");
            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Logout");
            alert.setMessage("Are you sure! you want to logout?");
            alert.setCancelable(false);
            alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(i);

                }
            });
            alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SessionManager sessionManager=new SessionManager(MainActivity.this);
                    sessionManager.writeStatus(false);
                    sessionManager.clear();
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(i);
                    finish();

                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();

                }
            });
            alert.show();
        }

        else if (id == R.id.nav_share) {
            loadHomeFragment(5);
        } else if (id == R.id.nav_send) {
            loadHomeFragment(6);
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
                Home home=new Home();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Details",nearbygarages);
                return home;

            case 1:
                Details details=new Details();
                return details;
            case 2:
               Editprofileuser user=new Editprofileuser();
               return user;
            case 3:
                Feedback feedback =new Feedback();
                return feedback;


           default:

               return new Home();
        }

    }

}
