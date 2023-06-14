package org.rij.minecraft.bukkit.ametis.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.rij.minecraft.bukkit.ametis.AmeWL;
import org.rij.minecraft.bukkit.ametis.Storage;

import java.util.ArrayList;
import java.util.List;

public class AmeWhitelist implements CommandExecutor, TabCompleter {

    private final AmeWL p;
    private final Storage storage;

    public AmeWhitelist(AmeWL plugin, Storage storage) {
        this.p = plugin;
        this.storage = storage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!command.getName().equalsIgnoreCase("amewhitelist"))
            return false;

        if(args.length == 1 && args[0].equals("reload")){
            storage.reloadWhitelist();
            sender.sendMessage(p.getConfigString("success_message!"));
            return true;
        }

        if(args.length == 2){
            String playerName = args[1];
            if(storage.isWhitelisted(playerName)){
                sender.sendMessage(p.getConfigString("already_whitelisted"));
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "add":
                    storage.addToWhitelist(playerName);
                    break;
                case "remove":
                    storage.removeFromWhitelist(playerName);
                    break;
                default:
                    sender.sendMessage(p.getConfigString("no_arguments_error"));
                    return false;
            }
            sender.sendMessage(p.getConfigString("success_message"));
            return true;
        }


        else if(args.length < 1)
            sender.sendMessage(p.getConfigString("no_arguments_error"));
        else
            sender.sendMessage(p.getConfigString("too_many_error"));

        return false;
    }



    public List<String> onTabComplete(CommandSender sender, Command cmd, String str, String[] args)
    {
        ArrayList<String> list = new ArrayList<>();
        if(cmd.getName().equalsIgnoreCase("amewhitelist") && args.length == 1 )
        {
            list.add("add");
            list.add("remove");
            list.add("reload");
        }
        return list;
    }
}
