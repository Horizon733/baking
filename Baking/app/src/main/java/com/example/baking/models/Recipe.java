package com.example.baking.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private Ingredients[] ingredients;
    private Steps[] steps;
    private int servings;

    public Recipe(int id, String name, Ingredients[] ingredients, Steps[] steps, int servings) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Ingredients[] getIngredients() {
        return this.ingredients;
    }

    public Steps[] getSteps() {
        return this.steps;
    }

    public int getServings() {
        return this.servings;
    }
}
