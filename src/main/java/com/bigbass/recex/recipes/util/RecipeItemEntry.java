package com.bigbass.recex.recipes.util;

import com.bigbass.recex.recipes.Ingredient;
import com.bigbass.recex.recipes.Ingredients;

public class RecipeItemEntry extends RecipeEntry {

  public RecipeItemEntry(String id, String name, int amount) {
    super(id, name, amount);
    Ingredients.addIngredient(new Ingredient(id, name, Ingredient.Type.ITEM));
  }
}
