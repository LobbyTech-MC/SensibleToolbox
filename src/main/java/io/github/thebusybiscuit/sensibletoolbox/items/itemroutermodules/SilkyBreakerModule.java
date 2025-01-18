package io.github.thebusybiscuit.sensibletoolbox.items.itemroutermodules;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class SilkyBreakerModule extends BreakerModule {

    private static final ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);

    static {
        pick.addEnchantment(Enchantment.SILK_TOUCH, 1);
    }

    public SilkyBreakerModule() {
        super();
    }

    public SilkyBreakerModule(ConfigurationSection conf) {
        super(conf);
    }

    @Override
    public String getItemName() {
        return "精准破坏升级";
    }

    @Override
    public String[] getLore() {
        return new String[] { "可放入物品运输器", "使用精准采集破坏指向的方块并输出至机器", "需要使用精准采集附魔书以制作!" };
    }

    @Override
    public Recipe getMainRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), toItemStack());
        BreakerModule b = new BreakerModule();
        registerCustomIngredients(b);
        recipe.addIngredient(b.getMaterial());
        recipe.addIngredient(Material.ENCHANTED_BOOK);
        return recipe;
    }

    @Override
    public boolean validateCrafting(CraftingInventory inventory) {
        for (ItemStack s : inventory.getMatrix()) {
            if (s != null && s.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) s.getItemMeta();
                if (meta.getStoredEnchantLevel(Enchantment.SILK_TOUCH) < 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected ItemStack getBreakerTool() {
        return pick;
    }
}
