package com.example.baking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BuildConfig;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.baking.adapters.CategoryAdapter;
import com.example.baking.models.Constants;
import com.example.baking.models.Ingredients;
import com.example.baking.models.Recipe;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepsNIngredientsActivity extends AppCompatActivity {
    public static boolean mTwoPane;
    Recipe recipe;
    private SharedPreferences sharedPreferences;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_n_ingredients);
        if (savedInstanceState != null) {
            Log.e("saveInstance", "Gone");
            this.recipe = (Recipe) savedInstanceState.getSerializable(Constants.RECIPE);
        }
        if (findViewById(R.id.steps_details) != null) {
            mTwoPane = true;
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_details, recipeDetailFragment)
                    .commit();

            recipe = (Recipe) getIntent().getSerializableExtra(Constants.RECIPE);
            if (recipe != null) {
                Log.e("steps", "" + recipe.getIngredients().length);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(recipe.getName());
                CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager()
                        , recipe.getSteps(), recipe.getIngredients());
                ViewPager viewPager = findViewById(R.id.viewpager);
                viewPager.setAdapter(categoryAdapter);
                TabLayout tabLayout = findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(viewPager);
            } else {
                Log.e("StepsNIngredientsActivity", "Recipe object empty");
            }
        } else {
            mTwoPane = false;
            recipe = (Recipe) getIntent().getSerializableExtra(Constants.RECIPE);
            if (recipe != null) {
                Log.e("steps", "" + recipe.getIngredients().length);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(recipe.getName());
                CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager()
                        , recipe.getSteps(), recipe.getIngredients());

                ViewPager viewPager = findViewById(R.id.viewpager);
                viewPager.setAdapter(categoryAdapter);
                TabLayout tabLayout = findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(viewPager);
            } else {
                Log.e("StepsNIngredientsActivity", "Recipe object empty");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("saveInstance Method", "Gone");
        outState.putSerializable(Constants.RECIPE, recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if ((sharedPreferences.getInt("ID", -1) == recipe.getId())) {
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_filled);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorite) {
            boolean recipeInWidget = sharedPreferences.getInt(Constants.ID, -1) == recipe.getId();
            if (item.isChecked()) {
                item.setIcon(R.drawable.ic_favorite_border);
                item.setChecked(false);
                sharedPreferences.edit()
                        .remove(Constants.ID)
                        .remove(Constants.TITLE)
                        .remove(Constants.INGREDIENTS)
                        .apply();
                Toast.makeText(this, "" + recipe.getName() + " is removed from widget", Toast.LENGTH_SHORT).show();
            } else {
                item.setChecked(true);
                item.setIcon(R.drawable.ic_favorite_filled);
                if (recipeInWidget) {
                    sharedPreferences.edit()
                            .remove(Constants.ID)
                            .remove(Constants.TITLE)
                            .remove(Constants.INGREDIENTS)
                            .apply();
                } else {
                    sharedPreferences.edit()
                            .putInt(Constants.ID, recipe.getId())
                            .putString(Constants.TITLE, recipe.getName())
                            .putString(Constants.INGREDIENTS, getIngredientsString(recipe.getIngredients()))
                            .apply();
                }
                Toast.makeText(this, "" + recipe.getName() + " is added to widget", Toast.LENGTH_SHORT).show();
                ComponentName provider = new ComponentName(this, RecipeWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] ids = appWidgetManager.getAppWidgetIds(provider);
                RecipeWidget bakingWidgetProvider = new RecipeWidget();
                bakingWidgetProvider.onUpdate(this, appWidgetManager, ids);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String getIngredientsString(Ingredients[] ingredients) {
        String str = "";
        List<Ingredients> ingredientsList = Arrays.asList(ingredients);
        for (int i = 0; i < ingredientsList.size(); i++) {
            str += ingredientsList.get(i).getQuantity() + " " + ingredientsList.get(i).getMeasure()
                    + " " + ingredientsList.get(i).getIngredient() + "\n";
        }
        return str;
    }
}
