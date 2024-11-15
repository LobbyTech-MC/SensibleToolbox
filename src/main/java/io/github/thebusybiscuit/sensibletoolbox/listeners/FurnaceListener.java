package io.github.thebusybiscuit.sensibletoolbox.listeners;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import io.github.thebusybiscuit.sensibletoolbox.SensibleToolboxPlugin;
import io.github.thebusybiscuit.sensibletoolbox.api.SensibleToolbox;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBItem;

/**
 * Contains event handlers to ensure vanilla and STB items behave correctly
 * in furnaces. Many STB items are smeltable, but use vanilla materials which
 * are not; we must ensure that those vanilla items can't be smelted.
 */
public class FurnaceListener extends STBBaseListener {

    public FurnaceListener(SensibleToolboxPlugin plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceInsert(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.FURNACE) {
            return;
        }

        if (event.getRawSlot() == 0 && event.getCursor().getType() != Material.AIR) {
            if (!validateSmeltingIngredient(event.getCursor())) {
                event.setCancelled(true);
            }
        } else if (event.getRawSlot() >= event.getView().getTopInventory().getSize()) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                if (!validateSmeltingIngredient(event.getCurrentItem()) && !event.getCurrentItem().getType().isFuel()) {
                    event.setCancelled(true);
                    int newSlot = findNewSlot(event);

                    if (newSlot >= 0) {
                        event.getWhoClicked().getInventory().setItem(newSlot, event.getCurrentItem());
                        event.setCurrentItem(null);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceInsert(InventoryDragEvent event) {
        if (event.getInventory().getType() != InventoryType.FURNACE) {
            return;
        }

        if (event.getOldCursor().getType() != Material.AIR) {
            for (int slot : event.getRawSlots()) {
                if (slot == 0 && !validateSmeltingIngredient(event.getOldCursor())) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onFurnaceInsertHopper(InventoryMoveItemEvent event) {
        if (event.getDestination().getType() != InventoryType.FURNACE) {
            return;
        }

        if (event.getSource().getHolder() instanceof Hopper) {
            Block b1 = ((BlockState) event.getSource().getHolder()).getBlock();
            Block b2 = ((BlockState) event.getDestination().getHolder()).getBlock();

            if (b1.getY() == b2.getY() + 1) {
                // hopper above the furnace - trying to insert items to be smelted
                if (!validateSmeltingIngredient(event.getItem())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent event) {
        // this ensures that an STB item smelted in a furnace leaves the
        // correct result in the furnace's output slot
        BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(event.getSource());

        if (item != null) {
            event.setResult(item.getSmeltingResult());
        }
    }

    private boolean validateSmeltingIngredient(@Nullable ItemStack stack) {
        if (stack == null) {
            return false;
        }

        BaseSTBItem item = SensibleToolbox.getItemRegistry().fromItemStack(stack);

        if (item != null) {
            return item.getSmeltingResult() != null;
        } else {
            // vanilla item - need to ensure it's actually smeltable (i.e. wasn't added
            // as a furnace recipe because it's the material for some custom STB item)
            // return RecipeCalculator.getSmeltedOutput(stack.getType()) != null;
            // TODO: Improve this
            Iterator<Recipe> recipes = Bukkit.recipeIterator();

            while (recipes.hasNext()) {
                Recipe recipe = recipes.next();

                if (recipe instanceof FurnaceRecipe && vanillaRecipeCheck((FurnaceRecipe) recipe, stack)) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean vanillaRecipeCheck(@Nonnull FurnaceRecipe recipe, @Nonnull ItemStack stack) {
        return recipe.getInputChoice().test(stack) && !recipe.getKey().getNamespace().equals("sensibletoolbox");
    }

    private int findNewSlot(InventoryClickEvent event) {
        int from = -1;
        int to = -2;

        switch (event.getSlotType()) {
            case QUICKBAR:
                from = 9;
                to = 35;
                break;
            case CONTAINER:
                from = 0;
                to = 8;
                break;
            default:
                break;
        }

        for (int i = from; i <= to; i++) {
            if (event.getWhoClicked().getInventory().getItem(i) == null) {
                return i;
            }
        }

        return -1;
    }
}
