package com.example.baking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;

import com.example.baking.models.Constants;
import com.example.baking.models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        List<Steps> steps = (List<Steps>) getIntent().getSerializableExtra(Constants.INGREDIENTS);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container
                        , new RecipeDetailFragment(getIntent().getIntExtra(Constants.ID,0),getIntent().getStringExtra(Constants.VIDEO)
                                , getIntent().getStringExtra(Constants.THUMBNAIL)
                                , getIntent().getStringExtra(Constants.DESCRIPTION)
                                , getIntent().getStringExtra(Constants.SHORT_DESCRIPTION)
                                ,steps))
                .commit();


    }
}
