package org.rij.minecraft.bukkit.ametis.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.rij.minecraft.bukkit.ametis.AmeWL;


public class EventListener implements Listener {

    private final AmeWL p;

    public EventListener(AmeWL plugin) {
        this.p = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        String playerName = e.getPlayer().getName();
        if(p.isWhitelisted(playerName)){
            return;
        }
        e.disallow(Result.KICK_WHITELIST, p.getConfigString("kick_message"));
    }
}
