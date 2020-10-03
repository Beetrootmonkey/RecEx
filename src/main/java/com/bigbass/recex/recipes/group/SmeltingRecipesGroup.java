package com.bigbass.recex.recipes.group;

import com.bigbass.recex.recipes.recipe.Recipe;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.bigbass.recex.recipes.util.RecipeEntry;
import com.bigbass.recex.recipes.util.RecipeUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmeltingRecipesGroup {

  public static RecipeGroup getRecipes() {
    List<Recipe> recipes = new ArrayList<>();

    Map smeltingList = FurnaceRecipes.smelting().getSmeltingList();
    for (Object obj : smeltingList.entrySet()) {
      if (obj instanceof HashMap.Entry) {
        try {
          Object inputStack = FieldUtils.readField(obj, "key", true);
          Object outputStack = FieldUtils.readField(obj, "value", true);

          if (inputStack instanceof ItemStack && outputStack instanceof ItemStack) {
            Recipe recipe = new Recipe();

            RecipeEntry input = RecipeUtil.formatRegularItemStack((ItemStack) inputStack);
            if (input == null) continue;
            recipe.addInput(input.getId(), input.getAmount());

            RecipeEntry output = RecipeUtil.formatRegularItemStack((ItemStack) outputStack);
            if (output == null) continue;
            recipe.addOutput(output.getId(), output.getAmount());

            recipes.add(recipe);
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return new RecipeGroup("Smelting", recipes);
  }
}
