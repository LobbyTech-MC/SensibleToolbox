package io.github.thebusybiscuit.sensibletoolbox.blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.RedstoneBehaviour;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.GUIUtil;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.InventoryGUI;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.AccessControlGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.NumericGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.RedstoneBehaviourGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.math.IntRange;
import me.desht.dhutils.Debugger;

public class BlockUpdateDetector extends BaseSTBBlock {

    private long lastPulse;
    private int duration;
    private int quiet;
    private boolean active = false;

    public BlockUpdateDetector() {
        quiet = 1;
        duration = 2;
    }

    public BlockUpdateDetector(ConfigurationSection conf) {
        super(conf);
        setDuration(conf.getInt("duration"));
        setQuiet(conf.getInt("quiet"));
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("duration", getDuration());
        conf.set("quiet", getQuiet());
        return conf;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        update(false);
    }

    public int getQuiet() {
        return quiet;
    }

    public void setQuiet(int quiet) {
        this.quiet = quiet;
        update(false);
    }

    @Override
    public Material getMaterial() {
        return active ? Material.REDSTONE_BLOCK : Material.PURPLE_TERRACOTTA;
    }

    @Override
    public String getItemName() {
        return "§d红石缓冲发生器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可以 §6周期性 §7的发生红石脉冲信号", "可以 §6自定义 §7脉冲间隔和持续时间", "右键设备: §6配置脉冲"};
    }

    @Override
    public String[] getExtraLore() {
        return new String[] { "间隔: " + ChatColor.GOLD + getDuration() + "§7分", "持续: " + ChatColor.GOLD + getQuiet() + "§7粉", };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe res = new ShapedRecipe(getKey(), toItemStack());
        res.shape("SRS", "SPS", "STS");
        res.setIngredient('S', Material.STONE);
        res.setIngredient('P', Material.STICKY_PISTON);
        res.setIngredient('R', Material.REDSTONE);
        res.setIngredient('T', Material.REDSTONE_TORCH);
        return res;
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block b = event.getBlock();
        long timeNow = getLocation().getWorld().getFullTime();
        Debugger.getInstance().debug(this + ": BUD physics: time=" + timeNow + ", lastPulse=" + lastPulse + ", duration=" + getDuration());

        if (timeNow - lastPulse > getDuration() + getQuiet() && isRedstoneActive()) {
            // emit a signal for one or more ticks
            lastPulse = timeNow;
            active = true;
            repaint(b);

            Bukkit.getScheduler().runTaskLater(getProviderPlugin(), () -> {
                active = false;
                repaint(b);
            }, duration);
        }
    }

    @Override
    public void onInteractBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
            getGUI().show(event.getPlayer());
            event.setCancelled(true);
        }

        super.onInteractBlock(event);
    }

    @Override
    protected InventoryGUI createGUI() {
        InventoryGUI gui = GUIUtil.createGUI(this, 9, ChatColor.DARK_PURPLE + getItemName());

        gui.addGadget(new NumericGadget(gui, 1, "间隔", new IntRange(1, Integer.MAX_VALUE), getDuration(), 10, 1, newValue -> {
            setDuration(newValue);
            return true;
        }));

        gui.addGadget(new NumericGadget(gui, 0, "持续", new IntRange(0, Integer.MAX_VALUE), getQuiet(), 10, 1, newValue -> {
            setQuiet(newValue);
            return true;
        }));

        gui.addGadget(new RedstoneBehaviourGadget(gui, 8));
        gui.addGadget(new AccessControlGadget(gui, 7));
        return gui;
    }

    @Override
    public void onBlockUnregistered(Location location) {
        // ensure the non-active form of the item is always dropped
        active = false;
        super.onBlockUnregistered(location);
    }

    @Override
    public boolean supportsRedstoneBehaviour(RedstoneBehaviour behaviour) {
        return behaviour != RedstoneBehaviour.PULSED;
    }
}
