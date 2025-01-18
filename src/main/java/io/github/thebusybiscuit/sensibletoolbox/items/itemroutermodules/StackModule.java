package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class StackModule extends ItemRouterModule {

    public StackModule() {}

    public StackModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "堆叠升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可放入物品运输器", "每放入一个堆叠升级", "可运输数量 x2", "最大到物品堆叠上限", "最多放入 6 个堆叠升级", };
    }

    @Override
    public Recipe getMainRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.BRICK);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.BRICK;
    }
}
