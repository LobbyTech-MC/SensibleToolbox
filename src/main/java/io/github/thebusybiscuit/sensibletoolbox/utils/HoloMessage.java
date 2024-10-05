package io.github.thebusybiscuit.sensibletoolbox.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.thebusybiscuit.sensibletoolbox.SensibleToolboxPlugin;
import me.desht.dhutils.MiscUtil;

import java.util.Arrays;

/**
 * This utility class is used to display a holographic pop up using the {@link DHAPI}.
 * 
 * @author desht
 * @author TheBusyBiscuit
 *
 */
public final class HoloMessage {

    private HoloMessage() {}

    @ParametersAreNonnullByDefault
    public static void popup(Player p, Location l, int durationInSeconds, String... message) {
        if (!SensibleToolboxPlugin.getInstance().isDecentGologramsEnabled() || !SensibleToolboxPlugin.getInstance().getConfig().getBoolean("holograms.enabled")) {
            for (String line : message) {
                MiscUtil.statusMessage(p, line);
            }

            return;
        }

        Location hologramLocation = l.clone().add(0.5, 2.25, 0.5);

        String hologramName = "holo_" + p.getName() + "_" + System.currentTimeMillis();
        Hologram h = DHAPI.createHologram(hologramName, hologramLocation);
        DHAPI.setHologramLines(h, Arrays.asList(message));
        SensibleToolboxPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(SensibleToolboxPlugin.getInstance(), h::delete, durationInSeconds * 10L);
    }
}
