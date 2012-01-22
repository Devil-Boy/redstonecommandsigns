package pgDev.bukkit.RedstoneCommandSigns;

import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RCSBlockListener extends BlockListener {
	private final RCSMain plugin;
	
	public RCSBlockListener(RCSMain pluginI) {
		plugin = pluginI;
	}
	
	public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    	
    }
	
	public void onBlockDamage(BlockDamageEvent event) {
		
	}
}
