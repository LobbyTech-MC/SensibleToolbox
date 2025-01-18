package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import me.desht.dhutils.Debugger;

public class ReceiverModule extends ItemRouterModule {

    public ReceiverModule() {}

    public ReceiverModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "接收升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可放入物品运输器", "被动接收物品", "从面朝的发送升级或", "连接的高级发送升级" };
    }

    @Override
    public Recipe getMainRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.HOPPER);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.ORANGE_DYE;
    }

    public int receiveItem(ItemStack item, UUID senderUUID) {
        int received = getItemRouter().insertItems(item, BlockFace.SELF, false, senderUUID);
        if (received > 0) {
            Debugger.getInstance().debug(2, "receiver in " + getItemRouter() + " received " + received + " of " + item + ", now has " + getItemRouter().getBufferItem());
        }
        return received;
    }
}
