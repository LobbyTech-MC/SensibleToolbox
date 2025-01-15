package io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import io.github.thebusybiscuit.sensibletoolbox.api.RedstoneBehaviour;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.InventoryGUI;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

/**
 * A GUI gadget which allows a STB block's redstone behaviour to be
 * displayed and changed.
 * 
 * @author desht
 */
public class RedstoneBehaviourGadget extends CyclerGadget<RedstoneBehaviour> {

    /**
     * Constructs a redstone behaviour gadget.
     *
     * @param gui
     *            the GUI to add the gadget to
     * @param slot
     *            the GUI slot to display the gadget in
     */
    public RedstoneBehaviourGadget(InventoryGUI gui, int slot) {
        super(gui, slot, "红石信号");

        add(RedstoneBehaviour.IGNORE, ChatColor.GRAY, Material.GUNPOWDER, "忽略红石信号");
        add(RedstoneBehaviour.HIGH, ChatColor.RED, Material.REDSTONE, "需要高红石信号");
        add(RedstoneBehaviour.LOW, ChatColor.YELLOW, Material.GLOWSTONE_DUST, "需要低红石信号");
        add(RedstoneBehaviour.PULSED, ChatColor.DARK_AQUA, Material.LAPIS_LAZULI, "需要持续高低红石信号");
        setInitialValue(gui.getOwningBlock().getRedstoneBehaviour());
    }

    @Override
    protected boolean ownerOnly() {
        return false;
    }

    @Override
    protected boolean supported(BaseSTBItem stbItem, RedstoneBehaviour what) {
        return ((BaseSTBBlock) stbItem).supportsRedstoneBehaviour(what);
    }

    @Override
    protected void apply(BaseSTBItem stbItem, RedstoneBehaviour newValue) {
        ((BaseSTBBlock) stbItem).setRedstoneBehaviour(newValue);
    }
}
