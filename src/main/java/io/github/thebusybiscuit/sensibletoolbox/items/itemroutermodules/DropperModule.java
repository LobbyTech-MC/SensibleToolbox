package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.Vector;

import me.desht.dhutils.Debugger;

public class DropperModule extends DirectionalItemRouterModule {

    public DropperModule() {}

    public DropperModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "丢弃升级";
    }

    @Override
    public String[] getLore() {
        return makeDirectionalLore("可放入物品运输器", "将物品丢弃到指定的方向");
    }

    @Override
    public Recipe getMainRecipe() {
        BlankModule bm = new BlankModule();
        registerCustomIngredients(bm);
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        recipe.addIngredient(bm.getMaterial());
        recipe.addIngredient(Material.DROPPER);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.GRAY_DYE;
    }

    @Override
    public boolean execute(Location l) {
        if (getItemRouter() != null && getItemRouter().getBufferItem() != null) {
            if (getFilter() != null && !getFilter().shouldPass(getItemRouter().getBufferItem())) {
                return false;
            }

            int toDrop = getItemRouter().getStackSize();
            ItemStack s = getItemRouter().extractItems(BlockFace.SELF, null, toDrop, null);

            if (s != null) {
                Location targetLoc = getTargetLocation(l).add(0.5, 0.5, 0.5);
                Item item = targetLoc.getWorld().dropItem(targetLoc, s);
                item.setVelocity(new Vector(0, 0, 0));
                Debugger.getInstance().debug(2, "dropper dropped " + s + " from " + getItemRouter());
            }

            return true;
        }
        return false;
    }
}
