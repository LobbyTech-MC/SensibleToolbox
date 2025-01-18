package io.github.thebusybiscuit.sensibletoolbox.items.components;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;
import io.github.thebusybiscuit.sensibletoolbox.blocks.machines.FiftyKBatteryBox;

public class UnlinkedSCURelay extends BaseSTBItem {

    public UnlinkedSCURelay() {
        super();
    }

    public UnlinkedSCURelay(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.CYAN_STAINED_GLASS;
    }

    @Override
    public String getItemName() {
        return "未连接的 SCU 传输器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "只需两个黄豆就可以合成大西瓜!" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        FiftyKBatteryBox bb = new FiftyKBatteryBox();
        IntegratedCircuit ic = new IntegratedCircuit();
        EnergizedGoldIngot eg = new EnergizedGoldIngot();
        registerCustomIngredients(bb, ic, eg);
        recipe.shape("GCG", " E ", " B ");
        recipe.setIngredient('B', bb.getMaterial());
        recipe.setIngredient('C', ic.getMaterial());
        recipe.setIngredient('E', Material.ENDER_CHEST);
        recipe.setIngredient('G', eg.getMaterial());
        return recipe;
    }
}
