package io.github.thebusybiscuit.sensibletoolbox.items.upgrades;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.IntegratedCircuit;

public class ThoroughnessUpgrade extends AbstractMachineUpgrade {

    public static final int BONUS_OUTPUT_CHANCE = 8; // percent

    public ThoroughnessUpgrade() {}

    public ThoroughnessUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.SPIDER_EYE;
    }

    @Override
    public String getItemName() {
        return "资源升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放置在机器中", "速度: x0.7", "耗电量: x1.6", "输出: +" + BONUS_OUTPUT_CHANCE + "%" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("ICI", "IEI", "IGI");
        IntegratedCircuit ic = new IntegratedCircuit();
        registerCustomIngredients(ic);
        recipe.setIngredient('I', Material.IRON_BARS);
        recipe.setIngredient('C', ic.getMaterial());
        recipe.setIngredient('E', Material.SPIDER_EYE);
        recipe.setIngredient('G', Material.GLASS_PANE);
        return recipe;
    }
}
