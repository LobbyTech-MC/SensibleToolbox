package io.github.thebusybiscuit.sensibletoolbox.items.upgrades;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.SimpleCircuit;

public class SpeedUpgrade extends AbstractMachineUpgrade {

    public SpeedUpgrade() {}

    public SpeedUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.SUGAR;
    }

    @Override
    public String getItemName() {
        return "速度升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "放置在机器中", "速度: x1.4", "耗电: x1.6" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        recipe.shape("ISI", "IBI", "IGI");
        recipe.setIngredient('I', Material.IRON_BARS);
        recipe.setIngredient('S', sc.getMaterial());
        recipe.setIngredient('B', Material.BLAZE_ROD);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }
}
