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

import com.example.hearthcards.R;
import com.example.hearthcards.adapters.RecyclerViewAdapter;
import com.example.hearthcards.model.Cards;

import java.util.List;

public class FragmentMain extends Fragment {

    private RecyclerView recyclerView;
    View view;

    public FragmentMain() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_id);


        return view;
    }


    public void setuprecyclerview(List<Cards> listCards) {

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), listCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }


}



