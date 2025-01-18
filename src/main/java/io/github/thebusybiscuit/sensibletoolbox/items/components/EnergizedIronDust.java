package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class EnergizedIronDust extends BaseSTBItem {

    public EnergizedIronDust() {}

    public EnergizedIronDust(ConfigurationSection conf) {

    }

    @Override
    public Material getMaterial() {
        return Material.GUNPOWDER;
    }

    @Override
    public String getItemName() {
        return "充能铁粉";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可冶炼成充能铁锭" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack(1));
        InfernalDust dust1 = new InfernalDust();
        IronDust dust2 = new IronDust();
        registerCustomIngredients(dust1, dust2);
        recipe.addIngredient(dust1.getMaterial());
        recipe.addIngredient(dust2.getMaterial());
        return recipe;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new EnergizedIronIngot().toItemStack();
    }
}
