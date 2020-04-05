package com.khatwa.zilalalrahmaapp.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.khatwa.zilalalrahmaapp.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        boolean navUp = NavigationUI.navigateUp(navController, drawerLayout);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        return navUp;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {

            case R.id.menu_donation:
                if (navController.getCurrentDestination().getId() != R.id.donationFragment)
                    navController
                            .navigate(R.id.donationFragment);
                break;

            case R.id.menu_education:
                if (navController.getCurrentDestination().getId() != R.id.educationFragment)
                    navController
                            .navigate(R.id.educationFragment);
                break;

            case R.id.menu_social:
                if (navController.getCurrentDestination().getId() != R.id.socialFragment)
                    navController
                            .navigate(R.id.socialFragment);
                break;

            case R.id.menu_water:
                if (navController.getCurrentDestination().getId() != R.id.waterFragment)
                    navController.navigate(R.id.waterFragment);
                break;

            case R.id.menu_whoWeAre:
                if (navController.getCurrentDestination().getId() != R.id.whoWeAreFragment)
                    navController.navigate(R.id.whoWeAreFragment);
                break;
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        return true;
    }
}