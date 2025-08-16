package io.github.thebusybiscuit.sensibletoolbox.blocks.machines;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.energy.EnergyNet;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.utils.STBUtil;

public class HolographicMonitor extends BaseSTBBlock {

    private Hologram hologram;
    
    public HolographicMonitor() {}

    public HolographicMonitor(ConfigurationSection conf) {
        super(conf);
    }
    
    
    @Override
    public Material getMaterial() {
        return Material.LIGHT_BLUE_STAINED_GLASS;
    }

    @Override
    public String getItemName() {
        return "§4能量监视器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "需要先把 §6告示牌 §7贴在该设备上才可以工作", "用于显示连接的 §6电网 §7的供电详情" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("GGG", "LPL", "GGG");
        PowerMonitor monitor = new PowerMonitor();
        registerCustomIngredients(monitor);
        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('P', monitor.getMaterial());
        recipe.setIngredient('L', Material.LAPIS_LAZULI);
        return recipe;
    }

    @Override
    public int getTickRate() {
        return 120;
    }

    @Override
    public void onServerTick() {
        super.onServerTick();
        if (hologram == null) {
            return;
        }

        for (BlockFace f : STBUtil.getMainHorizontalFaces()) {
            EnergyNet net = SensibleToolbox.getEnergyNet(getRelativeLocation(f).getBlock());
            if (net != null) {
                double stat = net.getSupply() - net.getDemand();
                String prefix;

                if (stat > 0) {
                    prefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "+";
                } else {
                    prefix = ChatColor.DARK_RED + "" + ChatColor.BOLD + "-";
                }

                String line = prefix + " " + ChatColor.GRAY + STBUtil.getCompactDouble(Double.valueOf(String.valueOf(stat).replace("-", ""))) + " SCU/t";
                DHAPI.setHologramLines(hologram, Arrays.asList(line));
                break;
            }
        }
    }

    @Override
    public void onBlockRegistered(Location l, boolean isPlacing) {
        super.onBlockRegistered(l, isPlacing);

        onServerTick();
        this.hologram = DHAPI.createHologram("holo_monitor_" + System.currentTimeMillis(), getLocation().add(0.5, 1.4, 0.5));
        DHAPI.setHologramLines(hologram, Arrays.asList("加载中...", "整理数据中..."));
        onServerTick();
    }

    @Override
    public void onBlockUnregistered(Location l) {
        super.onBlockUnregistered(l);
        if (hologram != null) {
            hologram.delete();
        }
    }
}
