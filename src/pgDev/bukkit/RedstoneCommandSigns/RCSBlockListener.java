package pgDev.bukkit.RedstoneCommandSigns;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;

public class RCSBlockListener extends BlockListener {
	private final RCSMain plugin;
	
	public RCSBlockListener(RCSMain pluginI) {
		plugin = pluginI;
	}
	
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    	if (isSign(event.getBlock()) && event.getBlock().isBlockPowered()) {
    		Sign theSign = (Sign) event.getBlock().getState();
    		if (isRCS(theSign)) {
    			String signText = theSign.getLine(1) + theSign.getLine(2) + theSign.getLine(3);
    			if (theSign.getLine(0).endsWith(ChatColor.BLUE.toString())) {
    				if (plugin.hcsDB != null) {
    					for (String cmd : plugin.hcsDB.get(signText).commands) {
    						runCommand(cmd);
    					}
    				}
    			} else {
    				runCommand(signText);
    			}
    		}
    	}
    }
	
	public void runCommand(String commandString) {
		if (commandString.startsWith("/")) {
			commandString = commandString.substring(1);
		}
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), commandString);
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		Player player = event.getPlayer();
    	String name = player.getName();
    	
    	if (plugin.signConverters.contains(name)) {
    		if (isSign(event.getBlock())) {
    			Sign theSign = (Sign)event.getBlock().getState();
    			if (isNonRCS(theSign)) {
    				theSign.setLine(0, theSign.getLine(0).replace(ChatColor.GREEN.toString(), ChatColor.DARK_RED.toString()));
					theSign.update();
					plugin.signConverters.remove(name);
					if (theSign.getLine(0).endsWith(ChatColor.BLUE.toString())) {
						String signText = theSign.getLine(1) + theSign.getLine(2) + theSign.getLine(3);
						player.sendMessage(ChatColor.GOLD + "RedstoneCommandSign created with " + plugin.hcsDB.get(signText).commands.length + " commands.");
					} else {
						player.sendMessage(ChatColor.GOLD + "RedstoneCommandSign created!");
					}
    			} else if (isRCS(theSign)) {
    				player.sendMessage(ChatColor.RED + "That sign is already a redstone commandsign.");
    			} else {
    				player.sendMessage(ChatColor.RED + "That is not a commandsign.");
    			}
    		} else {
    			player.sendMessage(ChatColor.RED + "That block is not a sign.");
    		}
    	}
	}
	
	public boolean isSign(Block theBlock) {
    	if (theBlock.getType() == Material.SIGN_POST || theBlock.getType() == Material.WALL_SIGN) {
    		return true;
    	} else {
    		return false;
    	}
    }
	
	
	public boolean isNonRCS(Sign mrSign) {
    	return mrSign.getLine(0).startsWith(ChatColor.GREEN + plugin.scsID);
    }
    
    public boolean isRCS(Sign mrSign) {
    	return mrSign.getLine(0).startsWith(ChatColor.DARK_RED + plugin.scsID);
    }
}
