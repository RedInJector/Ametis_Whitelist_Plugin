package org.rij.minecraft.bukkit.ametis;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.rij.minecraft.bukkit.ametis.Commands.AmeWhitelist;
import org.rij.minecraft.bukkit.ametis.EventListeners.EventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AmeWL extends JavaPlugin {
    public static Plugin plugin;
    private static List<String> WhiteList = new ArrayList<>();
    private static FileConfiguration whitelistcfg;
    private static File whitelistFile;
    private SparkWebServer spark;
    private String sparkAPIkey;
    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        AmeWhitelist cmd1 = new AmeWhitelist(this);
        Objects.requireNonNull(getCommand("amewhitelist")).setExecutor(cmd1);
        getCommand("amewhitelist").setTabCompleter(cmd1);

        loadWhitelist();


        //spark = new SparkWebServer(2000, this);
        //spark.start();
    }

    @Override
    public void onDisable() {
        saveWhitelistFile();
        //spark.stop();
    }
    public String getConfigString(String Key){
        return plugin.getConfig().getString(Key);
    }

    public void addToWhitelist(String name){
        WhiteList.add(name);
        saveWhitelistFile();
        plugin.getLogger().info(name + " Was added to whitelist");
    }
    public void removeFromWhitelist(String name){
        WhiteList.remove(name);
        saveWhitelistFile();
        plugin.getLogger().info(name + " Was removed from whitelist");
    }
    public void loadWhitelist(){
        File f = new File(plugin.getDataFolder(), "whitelist.yml");
        whitelistFile = f;

        whitelistcfg = YamlConfiguration.loadConfiguration(f);
        if(whitelistFile.exists()){
            WhiteList = whitelistcfg.getStringList("whitelisted");
            return;
        }

        saveWhitelistFile();
        plugin.getLogger().config("Reloaded Config");
    }
    private void saveWhitelistFile(){
        whitelistcfg.set("whitelisted", WhiteList);
        try {
            whitelistcfg.save(whitelistFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reloadWhitelist(){
        loadWhitelist();
        plugin.reloadConfig();
    }
    public boolean isWhitelisted(String name){
        if(WhiteList.contains(name))
            return true;

        return false;
    }
}
