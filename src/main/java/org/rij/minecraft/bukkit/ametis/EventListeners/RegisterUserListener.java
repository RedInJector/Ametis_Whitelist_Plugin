package org.rij.minecraft.bukkit.ametis.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.rij.minecraft.bukkit.ametis.AmeWL;
import org.rij.minecraft.bukkit.ametisapi.Events.RegisterNewPlayerEvent;

public class RegisterUserListener implements Listener {
    private final AmeWL p;

    public RegisterUserListener(AmeWL p) {
        this.p = p;
    }

    @EventHandler
    public void onLogin(RegisterNewPlayerEvent e){
        String Name = e.getPlayerName();

        if(!p.isWhitelisted(Name)) {
            p.addToWhitelist(Name);
        }
    }
}
