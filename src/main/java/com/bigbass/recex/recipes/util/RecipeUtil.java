package com.bigbass.recex.recipes.util;

import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

// TODO: Rezepte für OreDict Entries aus OreDict holen: OreDictionary.getOres(name);
public class RecipeUtil {

  public static RecipeEntry formatRegularItemStack(ItemStack stack) {
    if (stack == null) return null;

    return new RecipeItemEntry(stack.getUnlocalizedName(), stack.getDisplayName(), stack.stackSize);
  }

  public static RecipeEntry formatGregtechItemStack(ItemStack stack) {
    if (stack == null) return null;

    String id = stack.getUnlocalizedName();

    if (id != null && !id.isEmpty() && id.equalsIgnoreCase("gt.integrated_circuit")) { // Programmed Circuit
      id += "." + stack.getItemDamage();
    }

    return new RecipeItemEntry(id, GT_LanguageManager.getTranslation(stack.getUnlocalizedName()), stack.stackSize);
  }

  public static RecipeEntry formatGregtechFluidStack(FluidStack stack) {
    if (stack == null) return null;

    return new RecipeFluidEntry(stack.getUnlocalizedName(), GT_LanguageManager.getTranslation(stack.getUnlocalizedName()), stack.amount);
  }
}
