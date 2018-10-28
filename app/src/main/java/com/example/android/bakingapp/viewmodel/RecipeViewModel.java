package com.example.android.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.room.entity.Recipe;
import com.example.android.bakingapp.room.repository.RecipeRepository;
import com.example.android.bakingapp.util.ApiResponse;

import java.util.List;

import javax.inject.Inject;

public class RecipeViewModel extends ViewModel {
    private final RecipeRepository recipeRepository;

    private LiveData<List<Recipe>> recipes;

    @Inject
    public RecipeViewModel(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public ApiResponse<LiveData<List<Recipe>>> getRecipes() {
        if (recipes != null) {
            return ApiResponse.success(recipes);
        }

        ApiResponse<LiveData<List<Recipe>>> apiResponse = recipeRepository.getRecipes();

        if (apiResponse.isSuccessful) {
            recipes = apiResponse.data;
        }

        return apiResponse;
    }
}