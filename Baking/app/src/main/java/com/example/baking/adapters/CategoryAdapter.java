package com.example.baking.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.baking.IngredientsFragment;
import com.example.baking.R;
import com.example.baking.StepsFragment;
import com.example.baking.models.Ingredients;
import com.example.baking.models.Steps;

import java.util.Arrays;
import java.util.List;

public class CategoryAdapter extends FragmentPagerAdapter {
        List<Steps> stepsList;
        List<Ingredients> ingredientsList;
        Context mContext;
    public CategoryAdapter(Context context, @NonNull FragmentManager fm, Steps[] steps, Ingredients[] ingredients) {
        super(fm);
        mContext =context;
        stepsList = Arrays.asList(steps);
        ingredientsList = Arrays.asList(ingredients);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new IngredientsFragment(ingredientsList);
        }else {
            return new StepsFragment(stepsList);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_ingredients);
        } else {
            return mContext.getString(R.string.category_steps);
        }
    }
}
