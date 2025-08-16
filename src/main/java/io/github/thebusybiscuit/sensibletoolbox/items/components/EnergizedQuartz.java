package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class EnergizedQuartz extends BaseSTBItem {

    public EnergizedQuartz() {}

    public EnergizedQuartz(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.QUARTZ;
    }

    @Override
    public String getItemName() {
        return "§d充电的石英";
    }

    @Override
    public String[] getLore() {
        return new String[] { "电子不安的涌动,发出诡异的光芒..." };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack(1));
        InfernalDust dust = new InfernalDust();
        registerCustomIngredients(dust);
        recipe.addIngredient(dust.getMaterial());
        recipe.addIngredient(Material.QUARTZ);
        return recipe;
    }

}
