package in.sling.activities;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import in.sling.R;
import in.sling.fragments.ChatFragment;
import in.sling.fragments.NoticeEditorFragment;
import in.sling.fragments.NoticeFragment;
import in.sling.fragments.ReviewFragment;
import in.sling.fragments.SettingsFragement;
import in.sling.models.User;
import in.sling.models.UserPopulated;
import in.sling.services.DataService;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataService = new DataService(getSharedPreferences("in.sling", Context.MODE_PRIVATE));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        UserPopulated user = dataService.getUser();
        TextView userName = (TextView)header.findViewById(R.id.user_name);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        TextView profileType = (TextView)header.findViewById(R.id.profile_type);
        String type = "";
        if(user.getIsParent()){
            type="Parent Profile";
        }
        else if(user.getIsTeacher()){
            type="Teacher Profile";
        }
        profileType.setText(type);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,
                        NoticeFragment.newInstance()).commit();
        getSupportActionBar().setTitle("Notice Board");
        getSupportActionBar().setSubtitle(dataService.getUser().getSchool().getName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 0) {
                // write your code to switch between fragments.
            } else {
                super.onBackPressed();
            }
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
            getSupportActionBar().setSubtitle(dataService.getUser().getSchool().getName());

        } else if (id == R.id.nav_chats) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            ChatFragment.newInstance()).addToBackStack("review").commit();
            getSupportActionBar().setTitle("Chats");
            getSupportActionBar().setSubtitle(null);

        } else if (id == R.id.nav_student_review) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container,
                            ReviewFragment.newInstance()).addToBackStack("review").commit();

            getSupportActionBar().setTitle("Reviews");
            getSupportActionBar().setSubtitle(null);

        } else if (id == R.id.nav_settings) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            new SettingsFragement()).addToBackStack("review").commit();

            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setSubtitle(null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
