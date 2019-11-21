package com.khatwa.zilalalrahmaapp.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.khatwa.zilalalrahmaapp.R;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private NavController navController;

    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.newsListFragment);
        topLevelDestinations.add(R.id.donationFragment);
        topLevelDestinations.add(R.id.socialFragment);
        topLevelDestinations.add(R.id.waterFragment);
        topLevelDestinations.add(R.id.whoWeAreFragment);
        topLevelDestinations.add(R.id.educationFragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations)
                .setDrawerLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this,
                this.navController,
                this.appBarConfiguration);

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {

            case R.id.menu_latest_news:
                if (navController.getCurrentDestination().getId() != R.id.newsListFragment)
                    navController
                            .navigate(R.id.newsListFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.donationFragment,
                                                    true)
                                            .build()
                            );
                break;

            case R.id.menu_donation:
                if (navController.getCurrentDestination().getId() != R.id.donationFragment)
                    navController
                            .navigate(R.id.donationFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.newsListFragment,
                                                    true)
                                            .build()
                            );
                break;

            case R.id.menu_education:
                if (navController.getCurrentDestination().getId() != R.id.educationFragment)
                    navController
                            .navigate(R.id.educationFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.newsListFragment,
                                                    true)
                                            .build()
                            );
                break;

            case R.id.menu_social:
                if (navController.getCurrentDestination().getId() != R.id.socialFragment)
                    navController
                            .navigate(R.id.socialFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.newsListFragment,
                                                    true)
                                            .build()
                            );
                break;

            case R.id.menu_water:
                if (navController.getCurrentDestination().getId() != R.id.waterFragment)
                    navController
                            .navigate(R.id.waterFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.newsListFragment,
                                                    true)
                                            .build()
                            );
                break;

            case R.id.menu_whoWeAre:
                if (navController.getCurrentDestination().getId() != R.id.whoWeAreFragment)
                    navController
                            .navigate(R.id.whoWeAreFragment,
                                    null,
                                    new NavOptions.Builder()
                                            .setPopUpTo(R.id.newsListFragment,
                                                    true)
                                            .build()
                            );
                break;
        }
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        return true;
    }
}