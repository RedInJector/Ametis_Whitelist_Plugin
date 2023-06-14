package org.rij.minecraft.bukkit.ametis.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.rij.minecraft.bukkit.ametis.AmeWL;
import org.rij.minecraft.bukkit.ametis.Storage;


public class EventListener implements Listener {

    private final AmeWL p;
    private final Storage storage;

    public EventListener(AmeWL plugin, Storage storage) {
        this.p = plugin;
        this.storage = storage;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        String playerName = e.getPlayer().getName();
        if(storage.isWhitelisted(playerName)){
            return;
        }
        e.disallow(Result.KICK_WHITELIST, p.getConfigString("kick_message"));
    }
}
