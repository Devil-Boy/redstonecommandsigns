package pgDev.bukkit.RedstoneCommandSigns;

import org.bukkit.command.*;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

public class RCSMain extends JavaPlugin {
	private final RCSBlockListener blockListener = new RCSBlockListener(this);
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.REDSTONE_CHANGE, blockListener, Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	public void onDisable() {
		System.out.println("RedstoneCommandSigns disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  {
		return true;
	}
}