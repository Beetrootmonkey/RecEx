package com.bigbass.recex.recipes;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.recipes.group.*;
import com.bigbass.recex.recipes.recipe.RecipeGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeExporter {

  private static RecipeExporter instance;

  private RecipeExporter() {
  }

  public static RecipeExporter getInst() {
    if (instance == null) {
      instance = new RecipeExporter();
    }

    // Item.Diamond_Pickaxe
    // [
    //  ['D', 'D', 'D'],
    //  [' ', 'S', ' '],
    //  [' ', 'S', ' '],
    //   'S', Item.Stick,
    //   'D', Item.Diamond]
    return instance;
  }

  public void run() {
    Map<String, Object> rootMap = new HashMap<>();

    List<RecipeGroup> recipeList = new ArrayList<>();
    rootMap.put("recipes", recipeList);

    // All calls to the following 'getRecipes()'s register every item/fluid used as input or output.
    // All registered items/fluids can be accessed via IngredientList.getIngredients().
    recipeList.addAll(GregtechRecipesGroup.getRecipes());
    recipeList.add(ShapedRecipesGroup.getRecipes());
    recipeList.add(ShapelessRecipesGroup.getRecipes());
    recipeList.add(ShapedOreDictRecipesGroup.getRecipes());
    recipeList.add(ShapelessOreDictRecipesGroup.getRecipes());
    recipeList.add(OreDictRecipesGroup.getRecipes());

    List<Ingredient> ingredientList = IngredientList.getIngredients();
    rootMap.put("ingredients", ingredientList);

    Gson gson = (new GsonBuilder()).create();
    try {
      saveData(gson.toJson(rootMap));
    } catch (Exception e) {
      e.printStackTrace();
      RecipeExporterMod.log.error("Failed to export data!");
    }
  }

  private void saveData(String json) {
    final File saveFile = getSaveFile();

    try {
      FileWriter writer = new FileWriter(saveFile);
      writer.write(json);
      writer.close();

      RecipeExporterMod.log.info("Recipes have been exported.");
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
      RecipeExporterMod.log.error("Recipes failed to save!");
      return;
    }

    final String zipPath = saveFile.getPath().replace(".json", ".zip");
    final ZipFile zipFile = new ZipFile(new File(zipPath));
    final ZipParameters zipParameters = new ZipParameters();
    zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
    zipParameters.setCompressionLevel(CompressionLevel.FASTEST);

    try {
      zipFile.addFile(saveFile, zipParameters);
      RecipeExporterMod.log.info("Recipes have been compressed.");
    } catch (Exception e) {
      e.printStackTrace();
      RecipeExporterMod.log.warn("Recipe compression may have failed!");
    }
  }

  private File getSaveFile() {
    String dateTime = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss"));
    File file = new File(RecipeExporterMod.clientConfigDir.getParent() + "/RecEx-Records/" + dateTime + ".json");
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return file;
  }

//  private void cleanUpSingleOreDictUses(List<RecipeGroup> recipeList, List<Ingredient> ingredientList) {
//    Map<String, Integer> oreDictKeysUsed = new HashMap<>();
//
//    // Gather all keys used with the amount of recipes they're used in.
//    recipeList.forEach(recipeGroup -> {
//      recipeGroup.getRecipeList().forEach(recipe -> {
//        recipe.getInputs().keySet().forEach(key -> {
//          Ingredient ingredient = IngredientList.getIngredient(key);
//          if (ingredient != null && ingredient.type == Ingredient.Type.ORE_DICT) {
//            oreDictKeysUsed.put(key, oreDictKeysUsed.getOrDefault(key, 0) + 1);
//          }
//        });
//      });
//    });
//
//    List<String> oreDictKeysToReplace = oreDictKeysUsed.entrySet().parallelStream()
//      .filter(entry -> entry.getValue() <= 1).map(Map.Entry::getKey).collect(Collectors.toList());
//
//    oreDictKeysToReplace.forEach(IngredientList::removeIngredient);
//
//    recipeList.forEach(recipeGroup -> {
//      recipeGroup.getRecipeList().forEach(recipe -> {
//        recipe.getInputs().keySet().forEach(key -> {
//          Ingredient ingredient = IngredientList.getIngredient(key);
//          if (ingredient != null && ingredient.type == Ingredient.Type.ORE_DICT) {
//            oreDictKeysUsedInRecipe.add(key);
//          }
//        });
//      });
//    });
//  }
}
