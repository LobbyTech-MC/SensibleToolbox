package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.sensibletoolbox.items.components.SubspaceTransponder;
import io.github.thebusybiscuit.sensibletoolbox.utils.UnicodeSymbol;

public class HyperSenderModule extends AdvancedSenderModule {

    public HyperSenderModule() {
        super();
    }

    public HyperSenderModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.CYAN_DYE;
    }

    @Override
    public String getItemName() {
        return "终极发送升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可放入物品运输器", "将物品运输至任意接收升级", "左键已插入接收升级的物品运输器: " + ChatColor.WHITE + " 连接终极发送升级", UnicodeSymbol.ARROW_UP.toUnicode() + " 左键其他: " + ChatColor.WHITE + " 取消连接" };
    }

    @Override
    public Recipe getMainRecipe() {
        SenderModule sm = new SenderModule();
        SubspaceTransponder st = new SubspaceTransponder();
        registerCustomIngredients(sm, st);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(sm.getMaterial());
        recipe.addIngredient(st.getMaterial());
        return recipe;
    }

    @Override
    protected boolean inRange(Location ourLoc) {
        return ourLoc != null;
    }
}
