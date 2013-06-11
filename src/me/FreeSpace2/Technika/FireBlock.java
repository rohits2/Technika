package me.FreeSpace2.Technika;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class FireBlock implements Listener {
	
	//When redstone changes...
	@EventHandler
	public void onRedstoneChange(BlockPhysicsEvent event){
		//On netherrack...
		if (event.getBlock().getType()==Material.NETHERRACK){
			Block block=event.getBlock();
			if (block.isBlockPowered()){
				//And it can catch fire...
				if(block.getRelative(BlockFace.UP).getType()==Material.AIR){
					//Ignite it.
					block.getRelative(BlockFace.UP).setType(Material.FIRE);
				}
			}
		}
	}
}
