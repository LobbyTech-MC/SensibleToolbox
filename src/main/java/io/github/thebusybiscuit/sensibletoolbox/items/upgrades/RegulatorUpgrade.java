package io.github.thebusybiscuit.sensibletoolbox.items.upgrades;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.SimpleCircuit;

public class RegulatorUpgrade extends AbstractMachineUpgrade {

    public RegulatorUpgrade() {}

    public RegulatorUpgrade(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.ENDER_EYE;
    }

    @Override
    public String getItemName() {
        return "效率升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "提升机器的效率", "以更有效的利用资源", "效果因机器而异" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        recipe.shape("ISI", "IEI", "IRI");
        recipe.setIngredient('I', Material.IRON_BARS);
        recipe.setIngredient('S', sc.getMaterial());
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('R', Material.REDSTONE);
        return recipe;
    }
}
