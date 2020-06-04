package com.example.baking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking.adapters.IngredientsAdapter;
import com.example.baking.models.Ingredients;

import java.io.Serializable;
import java.util.List;


public class IngredientsFragment extends Fragment {

    static List<Ingredients> ingredientsList;

    public IngredientsFragment() {

    }

    public IngredientsFragment(List<Ingredients> ingredients) {
        // Required empty public constructor
        ingredientsList = ingredients;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null) {
            ingredientsList = (List<Ingredients>) savedInstanceState.getSerializable("ingredients");
        }
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        IngredientsAdapter mAdapter = new IngredientsAdapter(ingredientsList);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.ingredients_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ingredients", (Serializable) ingredientsList);
    }
}
