package com.bigbass.recex.recipes.util;

public abstract class RecipeEntry {
  String id;
  String name;
  int amount;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAmount() {
    return amount;
  }

  public RecipeEntry(String id, String name, int amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }
}
