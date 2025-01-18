package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

public class QuartzDust extends BaseSTBItem {

    public QuartzDust() {}

    public QuartzDust(ConfigurationSection conf) {}

    @Override
    public Material getMaterial() {
        return Material.SUGAR;
    }

    @Override
    public String getItemName() {
        return "石英粉";
    }

    @Override
    public String[] getLore() {
        return new String[] { "通过烹饪(?)生成硅" };
    }

    @Override
    public Recipe getMainRecipe() {
        // no vanilla recipe - made in a masher
        return null;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new SiliconWafer().toItemStack();
    }
}
