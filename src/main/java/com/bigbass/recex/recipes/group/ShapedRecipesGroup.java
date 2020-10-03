package com.bigbass.recex.recipes.group;

import com.bigbass.recex.recipes.recipe.Recipe;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.bigbass.recex.recipes.util.RecipeEntry;
import com.bigbass.recex.recipes.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;
import java.util.List;

public class ShapedRecipesGroup {

  public static RecipeGroup getRecipes() {
    List<Recipe> recipes = new ArrayList<>();

    List<?> recipeList = CraftingManager.getInstance().getRecipeList();
    for (Object obj : recipeList) {
      if (obj instanceof ShapedRecipes) {
        ShapedRecipes original = (ShapedRecipes) obj;
        Recipe recipe = new Recipe();

        for (Object stack : original.recipeItems) {
          if (stack instanceof ItemStack) {
            RecipeEntry input = RecipeUtil.formatRegularItemStack((ItemStack) stack);
            recipe.addInput(input.getId(), input.getAmount());
          } else if (stack instanceof Item) {
            RecipeEntry input = RecipeUtil.formatRegularItemStack(new ItemStack((Item) stack));
            recipe.addInput(input.getId(), input.getAmount());
          }
        }

        RecipeEntry output = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());
        recipe.addOutput(output.getId(), output.getAmount());

        recipes.add(recipe);
      }
    }

    return new RecipeGroup("Shaped", recipes);
  }
}
