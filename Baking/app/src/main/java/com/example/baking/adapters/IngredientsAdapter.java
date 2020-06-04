package com.example.baking.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baking.databinding.IngredientsItemListBinding;
import com.example.baking.models.Ingredients;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    List<Ingredients> ingredientsList;

    public IngredientsAdapter(List<Ingredients> ingredients) {
        ingredientsList = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        IngredientsItemListBinding itemBinding = IngredientsItemListBinding.inflate(layoutInflater, parent, false);
        return new IngredientsAdapter.IngredientsViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Ingredients ingredients = ingredientsList.get(position);
        holder.ingredientsItemListBinding.measureNQuantity.setText(ingredients.getQuantity() + " " + ingredients.getMeasure().toLowerCase());
        Log.v("ingredients", "" + ingredients.getIngredient());
        holder.ingredientsItemListBinding.ingredientTv.setText(ingredients.getIngredient());

    }

    @Override
    public int getItemCount() {
        if (ingredientsList.size() == 0 || ingredientsList == null) {
            return -1;
        }
        return ingredientsList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        IngredientsItemListBinding ingredientsItemListBinding;

        public IngredientsViewHolder(@NonNull IngredientsItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.ingredientsItemListBinding = itemListBinding;
        }
    }
}
