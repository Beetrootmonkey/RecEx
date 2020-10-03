package com.bigbass.recex.recipes.util;

import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeUtil {

  public static RecipeEntry formatRegularItemStack(ItemStack stack) {
    if (stack == null || stack.getUnlocalizedName() == null) return null;

//    if (stack.getUnlocalizedName().equalsIgnoreCase("gt.integrated_circuit")) {
//      return formatGregtechItemStack(stack);
//    }
//
//    return new RecipeItemEntry(stack.getUnlocalizedName(), stack.getDisplayName(), stack.stackSize);
    return formatGregtechItemStack(stack);
  }

  public static RecipeEntry formatGregtechItemStack(ItemStack stack) {
    if (stack == null) return null;

    String id = stack.getUnlocalizedName();
//    String name = GT_LanguageManager.getTranslation(id).replace("%material", "<...>");
    String name = "";
    try {
      name = stack.getDisplayName();
    } catch (Exception e1) {
      try {
        name = GT_LanguageManager.getTranslation(stack.getUnlocalizedName());
      } catch (Exception ignored) {
      }
    }

    if (id != null && !id.isEmpty() && id.equalsIgnoreCase("gt.integrated_circuit")) { // Programmed Circuit
      id += "." + stack.getItemDamage();
      name += " (Cfg. " + stack.getItemDamage() + ")";
    }

    return new RecipeItemEntry(id, name, stack.stackSize);
  }

  public static RecipeEntry formatGregtechFluidStack(FluidStack stack) {
    if (stack == null) return null;

    return new RecipeFluidEntry(stack.getUnlocalizedName(), GT_LanguageManager.getTranslation(stack.getUnlocalizedName()), stack.amount);
  }
}
