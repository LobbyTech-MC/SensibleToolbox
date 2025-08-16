package io.github.thebusybiscuit.sensibletoolbox.items.upgrades;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Directional;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.GUIUtil;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.InventoryGUI;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.DirectionGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBMachine;
import io.github.thebusybiscuit.sensibletoolbox.items.components.SimpleCircuit;

public class EjectorUpgrade extends AbstractMachineUpgrade implements Directional {

    public static final int DIRECTION_LABEL_SLOT = 2;
    private BlockFace direction;

    public EjectorUpgrade() {
        direction = BlockFace.SELF;
    }

    public EjectorUpgrade(ConfigurationSection conf) {
        super(conf);
        direction = BlockFace.valueOf(conf.getString("direction"));
    }

    @Override
    public YamlConfiguration freeze() {
        YamlConfiguration conf = super.freeze();
        conf.set("direction", getFacing().toString());
        return conf;
    }

    @Override
    public Material getMaterial() {
        return Material.QUARTZ;
    }

    @Override
    public String getItemName() {
        return "输出升级";
    }

    @Override
    public String getDisplaySuffix() {
        return direction != null && direction != BlockFace.SELF ? direction.toString() : null;
    }

    @Override
    public String[] getLore() {
        return new String[] { "放置在机器中 ", "自动输出物品", "左键方块设置输出方向" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack());
        SimpleCircuit sc = new SimpleCircuit();
        registerCustomIngredients(sc);
        recipe.shape("ISI", "IBI", "IGI");
        recipe.setIngredient('I', Material.IRON_BARS);
        recipe.setIngredient('S', sc.getMaterial());
        recipe.setIngredient('B', Material.PISTON);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            setFacingDirection(e.getBlockFace().getOppositeFace());
            updateHeldItemStack(e.getPlayer(), e.getHand());
            e.setCancelled(true);
        } else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // open ejector configuration GUI
            Block b = e.getClickedBlock();
            BaseSTBMachine machine = b == null ? null : SensibleToolbox.getBlockAt(b.getLocation(), BaseSTBMachine.class, true);

            if (b == null || machine == null && !b.getType().isInteractable()) {
                InventoryGUI gui = createGUI(e.getPlayer());
                gui.show(e.getPlayer());
                e.setCancelled(true);
            }
        }
    }

    private InventoryGUI createGUI(Player p) {
        InventoryGUI gui = GUIUtil.createGUI(p, this, 27, ChatColor.DARK_RED + "输出升级配置");
        gui.addLabel("方向", DIRECTION_LABEL_SLOT, null, "机器工作完毕后，物品输出的方向");

        ItemStack texture = GUIUtil.makeTexture(getMaterial(), "输出方向");
        DirectionGadget dg = new DirectionGadget(gui, 13, texture);
        dg.setAllowSelf(false);
        gui.addGadget(dg);

        return gui;
    }

    @Override
    public void setFacingDirection(BlockFace blockFace) {
        direction = blockFace;
    }

    @Override
    public BlockFace getFacing() {
        return direction;
    }

    @Override
    public void onGUIClosed(HumanEntity p) {
        p.setItemInHand(toItemStack(p.getItemInHand().getAmount()));
    }
}
