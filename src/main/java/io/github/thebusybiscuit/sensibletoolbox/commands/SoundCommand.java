package io.github.thebusybiscuit.sensibletoolbox.commands;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.desht.dhutils.DHUtilsException;
import me.desht.dhutils.MiscUtil;
import me.desht.dhutils.commands.AbstractCommand;

public class SoundCommand extends AbstractCommand {

    public SoundCommand() {
        super("stb sound", 1, 3);
        setUsage("/<command> sound <sound-name> [<volume>] [<pitch>]");
        setPermissionNode("stb.commands.sound");
    }

    @Override
    public boolean execute(Plugin plugin, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            MiscUtil.errorMessage(sender, "仅玩家可执行此命令！");
            return true;
        }

        try {
            Sound sound = Sound.valueOf(args[0].toUpperCase());
            float volume = args.length > 1 ? Float.parseFloat(args[1]) : 1.0F;
            float pitch = args.length > 2 ? Float.parseFloat(args[2]) : 1.0F;
            ((Player) sender).playSound(((Player) sender).getLocation(), sound, volume, pitch);
        } catch (IllegalArgumentException e) {
            throw new DHUtilsException(e.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(Plugin plugin, CommandSender sender, String[] args) {
        if (args.length == 1) {
            return getEnumCompletions(sender, Sound.class, args[0]);
        } else {
            return noCompletions(sender);
        }
    }
}
