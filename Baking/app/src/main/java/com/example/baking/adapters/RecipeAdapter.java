package com.example.baking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baking.MainActivity;
import com.example.baking.R;
import com.example.baking.StepsNIngredientsActivity;
import com.example.baking.databinding.RecipeListItemBinding;
import com.example.baking.models.Constants;
import com.example.baking.models.Recipe;
import com.example.baking.models.Steps;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipeList;
    Context mContext;

    public RecipeAdapter(List<Recipe> recipeList, Context context){
        this.recipeList = recipeList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeListItemBinding itemBinding = RecipeListItemBinding.inflate(layoutInflater,parent,false);
        return new RecipeViewHolder(itemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        final Recipe currentRecipe = recipeList.get(position);
        holder.mRecipeList.recipeName.setText(currentRecipe.getName());
        holder.mRecipeList.servings.setText("Servings: "+currentRecipe.getServings());
        holder.mRecipeList.recipeCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StepsNIngredientsActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(Constants.RECIPE,currentRecipe);
                intent.putExtras(extras);
                mContext.startActivity(intent);
            }
        });
        Log.e(""+currentRecipe.getName(),""+currentRecipe.getSteps().length);
    }

    @Override
    public int getItemCount() {
        if (recipeList == null || recipeList.size() == 0) {
            return -1;
        }
        return recipeList.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        RecipeListItemBinding mRecipeList;

        public RecipeViewHolder(@NonNull RecipeListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mRecipeList = itemBinding;
        }
    }
}
