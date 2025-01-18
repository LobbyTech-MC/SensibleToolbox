package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class InfernalDust extends BaseSTBItem {

    public InfernalDust() {}

    public InfernalDust(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public String getItemName() {
        return "烈焰粉";
    }

    @Override
    public String[] getLore() {
        return new String[] { "从烈焰人掠夺而来的粉末", "可以附加掠夺附魔以获取更多", "可以与铁粉和金粉融合" };
    }

    @Override
    public Recipe getMainRecipe() {
        // no vanilla recipe to make infernal dust, but a custom recipe will be added
        return null;
    }
}
