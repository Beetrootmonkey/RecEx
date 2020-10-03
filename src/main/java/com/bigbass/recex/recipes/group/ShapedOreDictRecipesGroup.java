package com.bigbass.recex.recipes.group;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.recipes.recipe.Recipe;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.bigbass.recex.recipes.util.RecipeEntry;
import com.bigbass.recex.recipes.util.RecipeOreDictEntry;
import com.bigbass.recex.recipes.util.RecipeUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.*;
import java.util.function.ToIntFunction;

public class ShapedOreDictRecipesGroup {

  public static RecipeGroup getRecipes() {
    List<Recipe> recipes = new ArrayList<>();

    List<?> recipeList = CraftingManager.getInstance().getRecipeList();
    for (Object obj : recipeList) {
      if (obj instanceof ShapedOreRecipe) {
        ShapedOreRecipe original = (ShapedOreRecipe) obj;
        Recipe recipe = new Recipe();

        for (Object stack : original.getInput()) {
          if (stack instanceof ItemStack) {
            RecipeEntry input = RecipeUtil.formatRegularItemStack((ItemStack) stack);
            recipe.addInput(input.getId(), input.getAmount());
          } else if (stack instanceof ArrayList<?>) {
            ArrayList<?> list = (ArrayList<?>) stack;
            if (list.size() > 0) {
              Map<String, Integer> map = new HashMap<>();

              for (Object listObj : list) {
                if (listObj instanceof ItemStack) {
                  int[] oreIDs = OreDictionary.getOreIDs((ItemStack) listObj);
                  for (int oreID : oreIDs) {
                    String oreName = OreDictionary.getOreName(oreID);
                    map.put(oreName, map.getOrDefault(oreName, 0) + 1);
                  }
                }
              }

              Optional<Map.Entry<String, Integer>> max = map.entrySet().parallelStream()
                .max(Comparator.comparingInt((ToIntFunction<Map.Entry<String, Integer>>) Map.Entry::getValue)
                  .thenComparingInt(o -> o.getKey().length()));
              if (max.isPresent()) {
                Map.Entry<String, Integer> entry = max.get();
                List<ItemStack> ores = OreDictionary.getOres(entry.getKey(), false);
                if (ores.size() != 1) {
                  // Use oreDict name as item id to create a fake item to be used as an ingredient
                  String chosenName = max.get().getKey();
                  RecipeEntry input = new RecipeOreDictEntry(chosenName, "<" + chosenName + ">", 1);
                  recipe.addInput(input.getId(), input.getAmount());
                } else {
                  // Use the only item registered as input
                  ItemStack itemStack = ores.get(0);
                  RecipeEntry input = RecipeUtil.formatRegularItemStack(itemStack);
                  recipe.addInput(input.getId(), input.getAmount());
                }
              } else {
                RecipeExporterMod.log.error("Failed to find best OreDict name!");
              }
            }
          } else if (stack != null) {
            try {
              RecipeExporterMod.log.error("OreDict input neither ItemStack nor ArrayList<ItemStack>: " + stack.getClass().getTypeName() + " | " + stack.getClass().getName());
            } catch (NullPointerException ignored) {
            }
          }
        }

        if (original.getRecipeOutput().getUnlocalizedName().equalsIgnoreCase("gt.integrated_circuit")) {
          continue;
        }

        RecipeEntry output = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());
        recipe.addOutput(output.getId(), output.getAmount());

        recipes.add(recipe);
      }
    }

    return new RecipeGroup("Shaped Ore Dict", recipes);
  }
}
