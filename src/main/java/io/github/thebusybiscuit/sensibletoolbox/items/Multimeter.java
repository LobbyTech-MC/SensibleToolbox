package io.github.thebusybiscuit.sensibletoolbox.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.energy.EnergyNet;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBMachine;
import io.github.thebusybiscuit.sensibletoolbox.items.components.SimpleCircuit;
import io.github.thebusybiscuit.sensibletoolbox.utils.HoloMessage;
import io.github.thebusybiscuit.sensibletoolbox.utils.STBUtil;

public class Multimeter extends BaseSTBItem {

    public Multimeter() {
        super();
    }

    public Multimeter(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.CLOCK;
    }

    @Override
    public String getItemName() {
        return "万用表";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可以对导线或机器使用", "以查看它们的能源连接和能源使用", "右键" + ChatColor.WHITE + "使用" };
    }

    @Override
    public boolean hasGlow() {
        return true;
    }

    @Override
    public Recipe getMainRecipe() {
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        recipe.shape("IGI", "CSC", " T ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('G', Material.GLOWSTONE_DUST);
        recipe.setIngredient('C', sc.getMaterial());
        recipe.setIngredient('S', Material.OAK_SIGN);
        recipe.setIngredient('T', Material.STICK);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getHand() == EquipmentSlot.HAND) {
            e.setCancelled(true);
            EnergyNet net = SensibleToolbox.getEnergyNet(e.getClickedBlock());
            Player p = e.getPlayer();

            if (net != null) {
                showNetInfo(p, net, e.getClickedBlock());
            } else {
                BaseSTBMachine machine = SensibleToolbox.getBlockAt(e.getClickedBlock().getLocation(), BaseSTBMachine.class, true);

                if (machine != null && machine.getMaxCharge() > 0) {
                    showMachineInfo(p, machine, e.getClickedBlock());
                } else {
                    // nothing to examine here
                    STBUtil.complain(p);
                }
            }
        }
    }

    private void showNetInfo(final Player p, EnergyNet net, Block clicked) {
        String[] lines = new String[] { net.getSourceCount() + ChatColor.GOLD.toString() + " 源" + ChatColor.WHITE + ", " + net.getSinkCount() + ChatColor.GOLD.toString() + " 汇流", net.getCableCount() + ChatColor.GOLD.toString() + " 导线" + String.format("消耗: " + ChatColor.GOLD + "%5.2f SCU/t", net.getDemand()), String.format("供电: " + ChatColor.GOLD + "%5.2f SCU/t", net.getSupply()), };
        HoloMessage.popup(p, clicked.getLocation(), 5, lines);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
    }

    private void showMachineInfo(Player p, BaseSTBMachine machine, Block clicked) {
        int n = machine.getAttachedEnergyNets().length;
        String[] lines = new String[] { ChatColor.GOLD + machine.getItemName() + ChatColor.WHITE + ": 连接 " + n + " 个能源网络", "已存入: " + STBUtil.getChargeString(machine), "最大充能速率: " + ChatColor.GOLD + machine.getChargeRate() + " SCU/t", };
        HoloMessage.popup(p, clicked.getLocation(), 5, lines);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
    }
}
