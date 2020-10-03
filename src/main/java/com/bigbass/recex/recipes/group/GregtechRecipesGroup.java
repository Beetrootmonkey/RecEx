package com.bigbass.recex.recipes.group;

import com.bigbass.recex.recipes.VoltageLevel;
import com.bigbass.recex.recipes.recipe.Recipe;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.bigbass.recex.recipes.util.RecipeEntry;
import com.bigbass.recex.recipes.util.RecipeUtil;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GregtechRecipesGroup {

  public static List<RecipeGroup> getRecipes() {
    List<RecipeGroup> list = new ArrayList<>();

    for (GT_Recipe.GT_Recipe_Map map : GT_Recipe.GT_Recipe_Map.sMappings) {

      String name = GT_LanguageManager.getTranslation(map.mUnlocalizedName);
      if (name == null || name.isEmpty()) {
        name = map.mUnlocalizedName;
      }
      RecipeGroup recipeGroup = new RecipeGroup(name);

      for (GT_Recipe rec : map.mRecipeList) {
        Recipe recipe = new Recipe();
        recipe.setDuration(rec.mDuration);
        recipe.setVoltageLevel(VoltageLevel.getFromEuPerTick(rec.mEUt));

        // Item inputs
        for (ItemStack stack : rec.mInputs) {
          RecipeEntry input = RecipeUtil.formatGregtechItemStack(stack);
          if (input != null) recipe.addInput(input.getId(), input.getAmount());
        }

        // Item outputs
        ItemStack[] mOutputs = rec.mOutputs;
        for (int i = 0; i < mOutputs.length; i++) {
          ItemStack stack = mOutputs[i];
          RecipeEntry input = RecipeUtil.formatGregtechItemStack(stack);
          if (input != null) {
            recipe.addOutput(input.getId(), input.getAmount());
            recipe.addOutputChance(input.getId(), rec.mChances[i]);
          }
        }

        // Fluid inputs
        for (FluidStack stack : rec.mFluidInputs) {
          RecipeEntry input = RecipeUtil.formatGregtechFluidStack(stack);
          if (input != null) recipe.addInput(input.getId(), input.getAmount());
        }

        // Fluid outputs
        for (FluidStack stack : rec.mFluidOutputs) {
          RecipeEntry input = RecipeUtil.formatGregtechFluidStack(stack);
          if (input != null) recipe.addOutput(input.getId(), input.getAmount());
        }

        recipeGroup.addRecipe(recipe);
      }
      list.add(recipeGroup);
    }

    return list;
  }
}
