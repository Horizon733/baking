package com.example.baking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.baking.models.Constants;
import com.google.android.exoplayer2.C;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container
                        , new RecipeDetailFragment(getIntent().getStringExtra(Constants.VIDEO)
                                , getIntent().getStringExtra(Constants.THUMBNAIL)
                                , getIntent().getStringExtra(Constants.DESCRIPTION)
                                , getIntent().getStringExtra(Constants.SHORT_DESCRIPTION)))
                .commit();
    }
}
