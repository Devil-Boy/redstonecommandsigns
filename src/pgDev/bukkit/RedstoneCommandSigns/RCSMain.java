package pgDev.bukkit.RedstoneCommandSigns;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import pgDev.bukkit.HiddenCommandSigns.*;
import pgDev.bukkit.SimpleCommandSigns.SimpleCommandSigns;

public class RCSMain extends JavaPlugin {
	private final RCSBlockListener blockListener = new RCSBlockListener(this);
	
    String scsID;
    
    static PermissionHandler Permissions;
    
    LinkedList<String> signConverters = new LinkedList<String>();
    
    HashMap<String, HiddenCommand> hcsDB;
	
	public void onEnable() {
		if (!setupSCS()) {
    		System.out.println("SimpleCommandSigns was not found on this server!");
    		getPluginLoader().disablePlugin(this);
    	} else {
			setupPermissions();
			
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvents(blockListener, this);
			
			PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    	}
	}
	
	public void onDisable() {
		System.out.println("RedstoneCommandSigns disabled!");
	}
	
	private void setupPermissions() {
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
            }
        }
    }
	
    public boolean setupSCS() {
    	Plugin scs = this.getServer().getPluginManager().getPlugin("SimpleCommandSigns");
    	
    	Plugin hcs = this.getServer().getPluginManager().getPlugin("HiddenCommandSigns");
    	if (hcs != null) {
    		hcsDB = ((HiddenCommandSigns) hcs).commandLinks;
    	}
    	
        if (scs != null) {
        	scsID = ((SimpleCommandSigns) scs).pluginSettings.commandSignIdentifier;
            return true;
        } else {
        	return false;
        }
    }
    
    public static boolean hasPermissions(Player player, String node) {
        if (Permissions != null) { // .addUserPermission and .removeUserPermission: world, user, node
        	return Permissions.has(player, node);
        } else {
            return player.hasPermission(node);
        }
    }
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (signConverters.contains(player.getName())) {
				signConverters.remove(player.getName());
				player.sendMessage(ChatColor.BLUE + "Redstone commandsign creation cancelled.");
			} else {
				if (hasPermissions(player, "rcs.create")) {
					signConverters.add(player.getName());
					player.sendMessage(ChatColor.BLUE + "Left-click the sign you wish to convert.");
				} else {
					player.sendMessage(ChatColor.RED + "You do not have the permissions to create a redstone commandsign.");
				}
			}
		} else {
			sender.sendMessage("This command is only to be run by a player.");
		}
		return true;
	}
}