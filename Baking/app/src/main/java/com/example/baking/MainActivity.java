package com.example.baking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.baking.adapters.RecipeAdapter;
import com.example.baking.models.Recipe;
import com.example.baking.utils.GetRecipesData;
import com.example.baking.utils.RetrofitInstance;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    static List<Recipe> recipeList;
    static GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recipe_list);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            recipeList = (List<Recipe>) savedInstanceState.getSerializable("recipe");
            Log.e("saved", "" + recipeList.size());
            mAdapter = new RecipeAdapter(recipeList, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                LinearLayout noConnection = findViewById(R.id.empty_view);
                noConnection.setVisibility(View.GONE);
                loadData();
            } else {
                LinearLayout noConnection = findViewById(R.id.empty_view);
                noConnection.setVisibility(View.VISIBLE);

            }
            loadData();
            Log.e("Not saved", "not saved");
        }

    }

    private void loadData() {
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        GetRecipesData getRecipesData = retrofit.create(GetRecipesData.class);
        Call<List<Recipe>> call = getRecipesData.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                Log.e("List", "Count " + recipeList.size());
                mAdapter = new RecipeAdapter(recipeList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("Error", String.valueOf(t.getCause()));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putSerializable("recipe", (Serializable) recipeList);
    }


}
