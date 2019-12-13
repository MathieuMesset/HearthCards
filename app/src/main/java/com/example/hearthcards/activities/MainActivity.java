package com.example.hearthcards.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hearthcards.R;
import com.example.hearthcards.adapters.RecyclerViewAdapter;
import com.example.hearthcards.model.Cards;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String JSON_URL = "https://api.hearthstonejson.com/v1/latest/frFR/cards.collectible.json";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Cards> listCards;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private String filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = "NO_FILTER";

        listCards = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_id);
        jsonrequest();

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void filter(String text, String classe_filter) {
        ArrayList<Cards> filteredList = new ArrayList<>();

        for (Cards card : listCards) {
            if (card.getName().toLowerCase().contains(text.toLowerCase())) {  //|| card.getClasse().toLowerCase().contains(text.toLowerCase()

                if (classe_filter.contains("NO_FILTER")) {
                    filteredList.add(card);
                } else if (card.getClasse().contains(classe_filter)) {
                    filteredList.add(card);
                }
            }
        }
        setuprecyclerview(filteredList);
    }


    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Cards card = new Cards();
                        card.setName(jsonObject.getString("name"));
                        card.setArtist(jsonObject.getString("artist"));
                        card.setFlavor(jsonObject.getString("flavor"));
                        card.setId(jsonObject.getString("id"));
                        card.setClasse(jsonObject.getString("cardClass"));
                        listCards.add(card);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                setuprecyclerview(listCards);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "UTF-8"));
                    return Response.success(new JSONArray(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);


    }

    private void setuprecyclerview(List<Cards> listCards) {

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, listCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

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
                filter(newText, filter);
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
        filter("", filter);

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
