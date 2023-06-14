package org.rij.minecraft.bukkit.ametis.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.rij.minecraft.bukkit.ametis.AmeWL;
import org.rij.minecraft.bukkit.ametis.Storage;
import org.rij.minecraft.bukkit.ametisapi.Events.RegisterNewPlayerEvent;

public class RegisterUserListener implements Listener {
    private final AmeWL p;
    private final Storage storage;

    public RegisterUserListener(AmeWL p, Storage storage) {
        this.p = p;
        this.storage = storage;
    }

    @EventHandler
    public void RegisterEvent(RegisterNewPlayerEvent e){
        String Name = e.getPlayerName();

        if(!p.storage.isWhitelisted(Name)) {
            storage.addToWhitelist(Name);
        }
    }
}
