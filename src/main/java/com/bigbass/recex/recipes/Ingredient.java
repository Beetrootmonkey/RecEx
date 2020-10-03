package com.bigbass.recex.recipes;

import java.util.Objects;

public class Ingredient {

  public enum Type {
    ITEM, FLUID, ORE_DICT
  }

  public String id;

  public String name;

  public Type type;

  public Ingredient() {

  }

  public Ingredient(String id, String name, Type type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ingredient that = (Ingredient) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
