package io.github.thebusybiscuit.sensibletoolbox.items;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.enderstorage.EnderStorage;
import io.github.thebusybiscuit.sensibletoolbox.api.enderstorage.EnderTunable;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.GUIUtil;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.InventoryGUI;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.SlotType;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.AccessControlGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.NumericGadget;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets.ToggleButton;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;
import io.github.thebusybiscuit.sensibletoolbox.utils.IntRange;
import io.github.thebusybiscuit.sensibletoolbox.utils.STBUtil;

public class EnderTuner extends BaseSTBItem {

    private static final ItemStack GLOBAL_TEXTURE = GUIUtil.makeTexture(Material.BLUE_STAINED_GLASS, "公开", "公共背包");
    private static final ItemStack PERSONAL_TEXTURE = GUIUtil.makeTexture(Material.YELLOW_STAINED_GLASS, "私有", "独立背包");
    public static final int TUNING_GUI_SIZE = 27;
    public static final int TUNED_ITEM_SLOT = 11;
    public static final int FREQUENCY_BUTTON_SLOT = 13;
    public static final int GLOBAL_BUTTON_SLOT = 8;
    public static final int ACCESS_CONTROL_SLOT = 17;

    private InventoryGUI inventoryGUI;
    private EnderTunable tuningBlock = null;

    public EnderTuner() {}

    public EnderTuner(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_NUGGET;
    }

    @Override
    public String getItemName() {
        return "量子末影调频器";
    }

    @Override
    public String[] getLore() {
        return new String[] { "在六个维度中振动", "拿着量子末影调频器右键后放入量子末影背包或量子末影箱以" + ChatColor.WHITE + "调频", "右键量子末影箱: " + ChatColor.WHITE + "打开对应量子末影箱的调频设置界面"};
    }

    @Override
    public Recipe getMainRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), toItemStack(1));
        recipe.shape("SES", "III", " G ");
        recipe.setIngredient('S', Material.GLOWSTONE_DUST);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }

    @Override
    public void onInteractItem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clicked = e.getClickedBlock();
            BaseSTBBlock stb = clicked == null ? null : SensibleToolbox.getBlockAt(clicked.getLocation(), true);

            if (stb instanceof EnderTunable && stb.hasAccessRights(e.getPlayer())) {
                tuningBlock = (EnderTunable) stb;
            }

            inventoryGUI = makeTuningGUI(e.getPlayer());
            inventoryGUI.show(e.getPlayer());
            e.setCancelled(true);
        }
    }

    @Nonnull
    private InventoryGUI makeTuningGUI(@Nonnull Player p) {
        InventoryGUI gui = GUIUtil.createGUI(p, this, TUNING_GUI_SIZE, ChatColor.DARK_PURPLE + "量子末影调频器");

        for (int slot = 0; slot < gui.getInventory().getSize(); slot++) {
            gui.setSlotType(slot, SlotType.BACKGROUND);
        }

        gui.setSlotType(TUNED_ITEM_SLOT, SlotType.ITEM);
        int freq = 1;
        boolean global = false;

        if (tuningBlock != null) {
            gui.setItem(TUNED_ITEM_SLOT, ((BaseSTBBlock) tuningBlock).toItemStack());
            gui.addLabel("量子末影调频器", TUNED_ITEM_SLOT - 1, null);
            freq = tuningBlock.getEnderFrequency();
            global = tuningBlock.isGlobal();
            gui.addGadget(new AccessControlGadget(gui, ACCESS_CONTROL_SLOT, (BaseSTBBlock) tuningBlock));
        } else {
            gui.addLabel("量子末影背包", TUNED_ITEM_SLOT - 1, null, "这里放置量子末影背包或量子末影调频器已调整它的频道");
        }

        gui.addGadget(new ToggleButton(gui, GLOBAL_BUTTON_SLOT, global, GLOBAL_TEXTURE, PERSONAL_TEXTURE, newValue -> {
            ItemStack s = gui.getItem(TUNED_ITEM_SLOT);
            BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(s);

            if (item instanceof EnderTunable) {
                ((EnderTunable) item).setGlobal(newValue);
                gui.setItem(TUNED_ITEM_SLOT, item.toItemStack(s.getAmount()));

                if (tuningBlock != null) {
                    tuningBlock.setGlobal(newValue);
                } else {
                    return true;
                }
            }

            return false;

        }));

        gui.addGadget(new NumericGadget(gui, FREQUENCY_BUTTON_SLOT, "频道", new IntRange(1, EnderStorage.MAX_ENDER_FREQUENCY), freq, 1, 10, newValue -> {
            ItemStack s = gui.getItem(TUNED_ITEM_SLOT);
            BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(s);

            if (item instanceof EnderTunable) {
                ((EnderTunable) item).setEnderFrequency(newValue);
                gui.setItem(TUNED_ITEM_SLOT, item.toItemStack(s.getAmount()));

                if (tuningBlock != null) {
                    tuningBlock.setEnderFrequency(newValue);
                }

                return true;
            } else {
                return false;
            }
        }));

        return gui;
    }

    @Override
    public boolean onSlotClick(HumanEntity p, int slot, ClickType click, ItemStack inSlot, ItemStack onCursor) {
        if (tuningBlock != null) {
            if (p instanceof Player) {
                STBUtil.complain((Player) p);
            }
            return false;
        }

        if (slot == TUNED_ITEM_SLOT) {
            if (onCursor.getType() == Material.AIR) {
                ((NumericGadget) inventoryGUI.getGadget(FREQUENCY_BUTTON_SLOT)).setValue(1);
                return true;
            } else {
                BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(onCursor);
                if (item instanceof EnderTunable) {
                    ((NumericGadget) inventoryGUI.getGadget(FREQUENCY_BUTTON_SLOT)).setValue(((EnderTunable) item).getEnderFrequency());
                    ((ToggleButton) inventoryGUI.getGadget(GLOBAL_BUTTON_SLOT)).setValue(((EnderTunable) item).isGlobal());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onPlayerInventoryClick(HumanEntity p, int slot, ClickType click, ItemStack inSlot, ItemStack onCursor) {
        return true;
    }

    @Override
    public int onShiftClickInsert(HumanEntity p, int slot, ItemStack toInsert) {
        if (tuningBlock != null) {
            if (p instanceof Player) {
                STBUtil.complain((Player) p);
            } else {
                return 0;
            }
        }

        BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(toInsert);

        if (item instanceof EnderTunable && inventoryGUI.getItem(TUNED_ITEM_SLOT) == null) {
            inventoryGUI.setItem(TUNED_ITEM_SLOT, toInsert);
            ((NumericGadget) inventoryGUI.getGadget(FREQUENCY_BUTTON_SLOT)).setValue(((EnderTunable) item).getEnderFrequency());
            ((ToggleButton) inventoryGUI.getGadget(GLOBAL_BUTTON_SLOT)).setValue(((EnderTunable) item).isGlobal());
            return toInsert.getAmount();
        } else {
            return 0;
        }
    }

    @Override
    public boolean onShiftClickExtract(HumanEntity p, int slot, ItemStack toExtract) {
        if (tuningBlock != null) {
            if (p instanceof Player) {
                STBUtil.complain((Player) p);
            }
            return false;
        }

        if (slot == TUNED_ITEM_SLOT && inventoryGUI.getItem(slot) != null) {
            ((NumericGadget) inventoryGUI.getGadget(FREQUENCY_BUTTON_SLOT)).setValue(1);
            return true;
        }

        return slot == TUNED_ITEM_SLOT && inventoryGUI.getItem(slot) != null;
    }

    @Override
    public boolean onClickOutside(HumanEntity p) {
        return false;
    }

    @Override
    public void onGUIClosed(final HumanEntity p) {
        if (tuningBlock == null) {
            ItemStack s = inventoryGUI.getItem(TUNED_ITEM_SLOT);

            if (s != null) {
                STBUtil.giveItems(p, s);
            }
        }
    }

    @Override
    public boolean isStackable() {
        return false;
    }
}
