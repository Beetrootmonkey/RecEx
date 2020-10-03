package com.bigbass.recex.recipes;

import java.util.*;

public class IngredientList {
  static Map<String, Ingredient> ingredientList = new HashMap<>();

  public static void addIngredient(Ingredient ingredient) {
    ingredientList.put(ingredient.id, ingredient);
  }

  public static Ingredient getIngredient(String id) {
    return ingredientList.get(id);
  }

  public static void removeIngredient(String id) {
    ingredientList.remove(id);
  }

  public static List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredientList.values());
  }
}
