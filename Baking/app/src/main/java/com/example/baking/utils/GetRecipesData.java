package com.example.baking.utils;

import com.example.baking.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRecipesData {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
