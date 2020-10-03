package com.bigbass.recex.recipes.util;

import com.bigbass.recex.recipes.Ingredient;
import com.bigbass.recex.recipes.Ingredients;

public class RecipeOreDictEntry extends RecipeEntry {

  public RecipeOreDictEntry(String id, String name, int amount) {
    super(id, name, amount);
    Ingredients.addIngredient(new Ingredient(id, name, Ingredient.Type.ORE_DICT));
  }
}
