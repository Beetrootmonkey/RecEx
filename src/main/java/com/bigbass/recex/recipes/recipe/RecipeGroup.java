package com.bigbass.recex.recipes.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeGroup {
  String title;
  List<Recipe> recipeList = new ArrayList<>();

  public String getTitle() {
    return title;
  }

  public List<Recipe> getRecipeList() {
    return recipeList;
  }

  public void addRecipe(Recipe recipe) {
    recipeList.add(recipe);
  }

  public RecipeGroup(String title) {
    this.title = title;
  }

  public RecipeGroup(String title, List<Recipe> recipeList) {
    this.title = title;
    this.recipeList = recipeList;
  }
}
