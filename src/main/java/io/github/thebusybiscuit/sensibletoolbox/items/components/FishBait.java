package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class FishBait extends BaseSTBItem {

    public FishBait() {}

    public FishBait(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.ROTTEN_FLESH;
    }

    @Override
    public String getItemName() {
        return "鱼饵";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放置在渔网中以捕鱼" };
    }

    @Override
    public Recipe getMainRecipe() {
        return null;
    }
}
