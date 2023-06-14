package org.rij.minecraft.bukkit.ametis;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private final AmeWL p;
    private static List<String> WhiteList = new ArrayList<>();
    private static FileConfiguration whitelistcfg;
    private static File whitelistFile;
    public Storage(AmeWL p) {
        this.p = p;
    }

    public void addToWhitelist(String name){
        if(isWhitelisted(name))
            return;

        WhiteList.add(name);
        p.getLogger().info(name + " was added to Whitelist");
        saveWhitelistFile();
    }
    public void removeFromWhitelist(String name){
        WhiteList.remove(name);
        saveWhitelistFile();
        p.getLogger().info(name + " was removed from whitelist");
    }
    public void reloadWhitelist(){
        loadWhitelist();
        p.reloadConfig();
    }
    public boolean isWhitelisted(String name){
        return WhiteList.contains(name);
    }

    public void loadWhitelist(){
        File f = new File(p.getDataFolder(), "whitelist.yml");
        whitelistFile = f;

        whitelistcfg = YamlConfiguration.loadConfiguration(f);
        if(whitelistFile.exists()){
            WhiteList = whitelistcfg.getStringList("whitelisted");
            return;
        }

        saveWhitelistFile();
        p.getLogger().config("Reloaded Config");
    }

    public void saveWhitelistFile(){
        whitelistcfg.set("whitelisted", WhiteList);
        try {
            whitelistcfg.save(whitelistFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
