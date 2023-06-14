package org.rij.minecraft.bukkit.ametis;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.rij.minecraft.bukkit.ametis.Commands.AmeWhitelist;
import org.rij.minecraft.bukkit.ametis.EventListeners.EventListener;
import org.rij.minecraft.bukkit.ametis.EventListeners.RegisterUserListener;

import java.util.Objects;

public final class AmeWL extends JavaPlugin {
    public static Plugin plugin;
    public Storage storage;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();

        storage = new Storage(this);
        storage.loadWhitelist();

        getServer().getPluginManager().registerEvents(new EventListener(this, storage), this);

        if(getServer().getPluginManager().getPlugin("AmetisAPI") != null)
            getServer().getPluginManager().registerEvents(new RegisterUserListener(this, storage), this);


        AmeWhitelist cmd1 = new AmeWhitelist(this, storage);
        Objects.requireNonNull(getCommand("amewhitelist")).setExecutor(cmd1);
        Objects.requireNonNull(getCommand("amewhitelist")).setTabCompleter(cmd1);



    }

    @Override
    public void onDisable() {
        storage.saveWhitelistFile();
    }
    public String getConfigString(String Key){
        return plugin.getConfig().getString(Key);
    }







}
