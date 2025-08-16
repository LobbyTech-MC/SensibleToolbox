package io.github.thebusybiscuit.sensibletoolbox.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;

import io.github.thebusybiscuit.sensibletoolbox.SensibleToolboxPlugin;
import io.github.thebusybiscuit.sensibletoolbox.api.items.BaseSTBBlock;
import io.github.thebusybiscuit.sensibletoolbox.core.storage.LocationManager;
import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;

public class ExplosiveToolListener extends STBBaseListener{

    public ExplosiveToolListener(SensibleToolboxPlugin plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onExplosiveToolBreakBlocks(ExplosiveToolBreakBlocksEvent event) {
        Block mainBlock = event.getPrimaryBlock();
        List<Block> blocksToBreak = new ArrayList<>();

        BaseSTBBlock mainSTBBlock = LocationManager.getManager().get(mainBlock.getLocation());
        if (mainSTBBlock == null) {
            blocksToBreak.add(mainBlock);
        }

        for (Block additionalBlock : event.getAdditionalBlocks()) {
            BaseSTBBlock additionalSTBBlock = LocationManager.getManager().get(additionalBlock.getLocation());
            if (additionalSTBBlock == null) {
                blocksToBreak.add(additionalBlock);
            }
        }

        event.setCancelled(true);

        for (Block block : blocksToBreak) {
            block.breakNaturally(event.getItemInHand());
        }
    }
}
