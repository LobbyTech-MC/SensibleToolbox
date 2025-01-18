package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class SpeedModule extends ItemRouterModule {

    public SpeedModule() {}

    public SpeedModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "速度升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可放入物品运输器", "提升工作速度:", "0 个速度升级 = 1 次 / 20 游戏刻", "1 = 1 / 15, 2 = 1 / 10, 3 = 1 / 5", "最多放入 3 个速度升级" };
    }

    @Override
    public Recipe getMainRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.BLAZE_POWDER);
        recipe.addIngredient(Material.EMERALD);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }
}
