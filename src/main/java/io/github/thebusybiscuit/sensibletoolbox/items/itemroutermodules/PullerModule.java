package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class PullerModule extends DirectionalItemRouterModule {

    public PullerModule() {}

    public PullerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "取出升级";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("可放入物品运输器", "——从相邻的机器中提取原料");
    }

    @Override
    public Recipe getMainRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.STICKY_PISTON);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.LIME_DYE;
    }

    @Override
    public boolean execute(Location l) {
        return getItemRouter() != null && doPull(getFacing(), l);
    }
}
