package com.bigbass.recex.recipes;

import java.util.*;

public class Ingredients {
  static Map<String, Ingredient> ingredientMap = new HashMap<>();

  public static void addIngredient(Ingredient ingredient) {
    ingredientMap.put(ingredient.id, ingredient);
  }

  public static Ingredient getIngredient(String id) {
    return ingredientMap.get(id);
  }

  public static void removeIngredient(String id) {
    ingredientMap.remove(id);
  }

  public static List<Ingredient> getIngredientList() {
    return new ArrayList<>(ingredientMap.values());
  }
  public static Map<String, Ingredient> getIngredients() {
    return ingredientMap;
  }
}
