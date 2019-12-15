package com.example.hearthcards.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.hearthcards.R;
import com.example.hearthcards.adapters.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private String filter;
    private FragmentMain mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = "NO_FILTER";

        mFragment = (FragmentMain) getSupportFragmentManager().findFragmentByTag("Main");

        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewPager_id);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FragmentMain(), "Main");
        viewPagerAdapter.AddFragment(new FragmentFavorites(), "Favorites");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mFragment.filter(newText, filter);
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String newFilter;
        switch (menuItem.getItemId()) {

            case R.id.druid_filter:
                newFilter = "DRUID";
                break;
            case R.id.hunter_filter:
                newFilter = "HUNTER";
                break;
            case R.id.mage_filter:
                newFilter = "MAGE";
                break;
            case R.id.paladin_filter:
                newFilter = "PALADIN";
                break;
            case R.id.priest_filter:
                newFilter = "PRIEST";
                break;
            case R.id.rogue_filter:
                newFilter = "ROGUE";
                break;
            case R.id.shaman_filter:
                newFilter = "SHAMAN";
                break;
            case R.id.warlock_filter:
                newFilter = "WARLOCK";
                break;
            case R.id.warrior_filter:
                newFilter = "WARRIOR";
                break;
            case R.id.neutral_filter:
                newFilter = "NEUTRAL";
                break;
            default:
                newFilter = "NO_FILTER";
                break;

        }

        if (filter.contains(newFilter)) {
            filter = "NO_FILTER";
            menuItem.setChecked(false);
        } else {
            filter = newFilter;
            menuItem.setChecked(true);
        }
        mFragment.filter("", filter);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
