package io.github.thebusybiscuit.sensibletoolbox.items;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;
import io.github.thebusybiscuit.sensibletoolbox.utils.UnicodeSymbol;
import me.desht.dhutils.Debugger;
import me.desht.dhutils.MiscUtil;

public class TapeMeasure extends BaseSTBItem {

    private String world;
    private int x;
    private int y;
    private int z;

    public TapeMeasure() {
        super();
        world = null;
        x = y = z = 0;
    }

    public TapeMeasure(ConfigurationSection conf) {
        super(conf);
        world = conf.getString("world");
        x = conf.getInt("x");
        y = conf.getInt("y");
        z = conf.getInt("z");
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration res = super.freeze();
        res.set("world", world);
        res.set("x", x);
        res.set("y", y);
        res.set("z", z);
        return res;
    }

    @Override
    public Material getMaterial() {
        return Material.STRING;
    }

    @Override
    public String getItemName() {
        return "测距尺";
    }

    @Override
    public String[] getLore() {
        return new String[] { UnicodeSymbol.ARROW_UP.toUnicode() + "右键第一个方块以设置锚点", "右键第二个方块开始测距" };
    }

    @Override
    public String[] getExtraLore() {
        if (world != null) {
            return new String[] { ChatColor.YELLOW + "锚点: " + ChatColor.WHITE + world + "," + x + "," + y + "," + z };
        } else {
            return new String[0];
        }
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("SSS", "SIS", "SSS");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('I', Material.IRON_INGOT);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().isSneaking()) {
                setAnchor(e.getClickedBlock());
                updateHeldItemStack(e.getPlayer(), e.getHand());
                MiscUtil.statusMessage(e.getPlayer(), ChatColor.YELLOW + "已设置锚点");
            } else {
                makeMeasurement(e.getPlayer(), e.getClickedBlock());
            }
        } else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            setAnchor(null);
            updateHeldItemStack(e.getPlayer(), e.getHand());
            MiscUtil.statusMessage(e.getPlayer(), ChatColor.YELLOW + "已清除锚点");
        }

        e.setCancelled(true);
    }

    @ParametersAreNonnullByDefault
    private void makeMeasurement(Player p, Block b) {
        Debugger.getInstance().debug(this + ": make measurement at " + b);

        if (world != null && world.equals(b.getWorld().getName())) {
            int xOff = b.getX() - x;
            int yOff = b.getY() - y;
            int zOff = b.getZ() - z;

            xOff = Math.abs(xOff);
            yOff = Math.abs(yOff);
            zOff = Math.abs(zOff);

            int totalBlocks = xOff + yOff + zOff + 1; // +1 to count the clicked block

            MiscUtil.statusMessage(p, String.format(ChatColor.YELLOW + "测量结果: " + ChatColor.WHITE + "距离: X: %d Y: %d Z: %d, 共: %d 个方块", xOff, yOff, zOff, totalBlocks));
        }
    }

    private void setAnchor(@Nullable Block clickedBlock) {
        if (clickedBlock != null) {
            world = clickedBlock.getWorld().getName();
            x = clickedBlock.getX();
            y = clickedBlock.getY();
            z = clickedBlock.getZ();
        } else {
            world = null;
        }
    }
}
