package io.github.thebusybiscuit.sensibletoolbox.api.gui.gadgets;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import io.github.thebusybiscuit.sensibletoolbox.api.AccessControl;
import io.github.thebusybiscuit.sensibletoolbox.api.gui.InventoryGUI;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

/**
 * A gadget which can display and update the access control for an STB block.
 * 
 * @author desht
 */
public class AccessControlGadget extends CyclerGadget<AccessControl> {

    /**
     * Construct an access control gadget.
     * <p/>
     * The <em>stb</em> parameter would normally refer to a different block
     * than the GUI's owner. This allows, for example, a GUI to be created on
     * a non-block item to be used to configure the access control settings
     * for another block.
     *
     * @param gui
     *            the GUI to add the gadget to
     * @param slot
     *            the GUI slot to display the gadget in
     * @param stb
     *            the STB block that the gadget controls
     */
    public AccessControlGadget(InventoryGUI gui, int slot, BaseSTBBlock stb) {
        super(gui, slot, "访问权限", stb);
        add(AccessControl.PUBLIC, ChatColor.GREEN, Material.GREEN_WOOL, "持有者: " + ChatColor.ITALIC + "<OWNER>", "公开");
        add(AccessControl.PRIVATE, ChatColor.RED, Material.RED_WOOL, "持有者: " + ChatColor.ITALIC + "<OWNER>", "仅自己可使用");
        add(AccessControl.RESTRICTED, ChatColor.YELLOW, Material.YELLOW_WOOL, "持有者: " + ChatColor.ITALIC + "<OWNER>", "仅自己和可信者可使用");
        setInitialValue(stb == null ? gui.getOwningBlock().getAccessControl() : stb.getAccessControl());
    }

    /**
     * Construct an access control gadget.
     *
     * @param gui
     *            the GUI to add the gadget to
     * @param slot
     *            the GUI slot to display the gadget in
     */
    public AccessControlGadget(InventoryGUI gui, int slot) {
        this(gui, slot, null);
    }

    @Override
    protected boolean ownerOnly() {
        return true;
    }

    @Override
    protected void apply(BaseSTBItem stbItem, AccessControl newValue) {
        ((BaseSTBBlock) stbItem).setAccessControl(newValue);
    }
}
