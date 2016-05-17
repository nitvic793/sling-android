package in.sling.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.sling.R;
import in.sling.fragments.ChatFragment;
import in.sling.fragments.NoticeEditorFragment;
import in.sling.fragments.NoticeFragment;
import in.sling.fragments.ReviewFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        NoticeFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Notice Board");
        getSupportActionBar().setSubtitle("School name here");


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_notice_board) {
            // Handle the camera action
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            NoticeFragment.newInstance()).commit();
            getSupportActionBar().setTitle("Notice Board");
            getSupportActionBar().setSubtitle("School name here");

        } else if (id == R.id.nav_chats) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            ChatFragment.newInstance()).commit();
            getSupportActionBar().setTitle("Chats");
            getSupportActionBar().setSubtitle(null);

        } else if (id == R.id.nav_student_review) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            ReviewFragment.newInstance()).commit();

            getSupportActionBar().setTitle("Reviews");
            getSupportActionBar().setSubtitle(null);

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}