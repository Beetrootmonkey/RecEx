package com.bigbass.recex.recipes.util;

import com.bigbass.recex.recipes.Ingredient;
import com.bigbass.recex.recipes.IngredientList;

public class RecipeOreDictEntry extends RecipeEntry {

  public RecipeOreDictEntry(String id, String name, int amount) {
    super(id, name, amount);
    IngredientList.addIngredient(new Ingredient(id, name, Ingredient.Type.ORE_DICT));
  }
}
