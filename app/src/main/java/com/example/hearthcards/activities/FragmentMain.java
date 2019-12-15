package com.example.hearthcards.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends Fragment {
    private final String JSON_URL = "https://api.hearthstonejson.com/v1/latest/frFR/cards.collectible.json";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Cards> listCards;
    private RecyclerView recyclerView;
    View view;

    public FragmentMain() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);

        listCards = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view_id);
        jsonrequest();


        return view;
    }


    public void filter(String text, String classe_filter) {
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


        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }


    public void setuprecyclerview(List<Cards> listCards) {

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), listCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }


}



