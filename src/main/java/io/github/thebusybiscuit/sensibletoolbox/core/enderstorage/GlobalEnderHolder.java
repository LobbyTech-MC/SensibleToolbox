package io.github.thebusybiscuit.sensibletoolbox.core.enderstorage;

import org.bukkit.ChatColor;

import io.github.thebusybiscuit.sensibletoolbox.utils.UnicodeSymbol;

import java.io.File;

public class GlobalEnderHolder extends STBEnderStorageHolder {

    public GlobalEnderHolder(EnderStorageManager manager, int frequency) {
        super(manager, frequency);
    }

    @Override
    public String getInventoryTitle() {
        return ChatColor.DARK_PURPLE + "电子存储 " + ChatColor.DARK_RED + "[公开 " + UnicodeSymbol.NUMBER.toUnicode() + getFrequency() + "]";
    }

    @Override
    public File getSaveFile() {
        File global = new File(getManager().getStorageDir(), "global");
        return new File(global, Integer.toString(getFrequency()));
    }

    @Override
    public String toString() {
        return "公开电子存储 #" + getFrequency();
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}
