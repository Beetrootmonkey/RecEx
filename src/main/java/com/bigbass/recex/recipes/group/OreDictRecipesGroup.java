package com.bigbass.recex.recipes.group;

import com.bigbass.recex.recipes.Ingredient;
import com.bigbass.recex.recipes.Ingredients;
import com.bigbass.recex.recipes.recipe.Recipe;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.bigbass.recex.recipes.util.RecipeEntry;
import com.bigbass.recex.recipes.util.RecipeUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OreDictRecipesGroup {

  public static RecipeGroup getRecipes() {
    List<Recipe> recipes = new ArrayList<>();

    List<Ingredient> ingredients = Ingredients.getIngredientList().stream().filter(ingredient -> ingredient.type == Ingredient.Type.ORE_DICT).collect(Collectors.toList());
    ingredients.forEach(ingredient -> {
      List<ItemStack> ores = OreDictionary.getOres(ingredient.id, false);
      for (ItemStack itemStack : ores) {
        Recipe recipe = new Recipe();
        RecipeEntry input = RecipeUtil.formatRegularItemStack(itemStack);
        recipe.addInput(input.getId(), input.getAmount());
        recipe.addOutput(ingredient.id, 1);
        recipes.add(recipe);
      }
    });

    return new RecipeGroup("Ore Dict", recipes);
  }
}
