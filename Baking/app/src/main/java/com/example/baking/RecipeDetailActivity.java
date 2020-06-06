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
import com.google.android.exoplayer2.C;
import com.google.android.material.navigation.NavigationView;

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
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);
       // Menu menu = navigationView.getMenu();
        //menu.add("Hello");
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.steps_list_root)
                .setDrawerLayout(drawerLayout).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
}
