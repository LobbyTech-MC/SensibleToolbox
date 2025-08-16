package io.github.thebusybiscuit.sensibletoolbox.items.recipebook;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class AdvancedRecipeBook extends RecipeBook {

    public AdvancedRecipeBook() {
        super();
    }

    public AdvancedRecipeBook(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "高级合成环";
    }

    @Override
    public boolean isAdvanced() {
        return true;
    }

    @Override
    public String[] getExtraLore() {
        return new String[] { "制作时可以从临近的容器中提取物品" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        RecipeBook book = new RecipeBook();
        registerCustomIngredients(book);
        recipe.addIngredient(Material.DIAMOND);
        recipe.addIngredient(book.getMaterial());
        return recipe;
    }
}
