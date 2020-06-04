package com.example.baking.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baking.R;
import com.example.baking.RecipeDetailActivity;
import com.example.baking.RecipeDetailFragment;
import com.example.baking.StepsNIngredientsActivity;
import com.example.baking.databinding.RecipeListItemBinding;
import com.example.baking.databinding.StepsListItemBinding;
import com.example.baking.models.Constants;
import com.example.baking.models.Steps;
import com.google.android.exoplayer2.C;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder> {
    List<Steps> mSteps;
    Context mContext;

    public StepAdapter(Context context, List<Steps> steps) {
        this.mSteps = steps;
        this.mContext = context;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StepsListItemBinding itemBinding = StepsListItemBinding.inflate(layoutInflater, parent, false);
        return new StepAdapter.StepsViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        final Steps steps = mSteps.get(position);
        holder.stepsListItemBinding.stepsBtn.setText((steps.getId()) + 1 + ". " + steps.getShortDescription());
        holder.stepsListItemBinding.stepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StepsNIngredientsActivity.mTwoPane) {
                    Bundle extras = new Bundle();
                    extras.putString(Constants.VIDEO, steps.getVideoURL());
                    extras.putString(Constants.THUMBNAIL, steps.getThumbnailURL());
                    extras.putString(Constants.DESCRIPTION, steps.getDescription());
                    extras.putString(Constants.SHORT_DESCRIPTION, steps.getShortDescription());
                    RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                    recipeDetailFragment.setArguments(extras);

                    FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_details, recipeDetailFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                    intent.putExtra(Constants.VIDEO, steps.getVideoURL());
                    intent.putExtra(Constants.THUMBNAIL, steps.getThumbnailURL());
                    intent.putExtra(Constants.DESCRIPTION, steps.getDescription());
                    intent.putExtra(Constants.SHORT_DESCRIPTION, steps.getShortDescription());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mSteps.size() == 0 || mSteps == null) {
            return -1;
        }
        return mSteps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        StepsListItemBinding stepsListItemBinding;

        public StepsViewHolder(@NonNull StepsListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.stepsListItemBinding = itemBinding;
        }
    }
}
