package me.FreeSpace2.Technika;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.util.Vector;

public class ForceCannon implements Listener {
	//Dangerous thing to implement.
	@EventHandler
	public void onRedstoneChange(BlockPhysicsEvent event){
		if (event.getBlock().getType()==Material.IRON_BLOCK){
			Block block=event.getBlock();
			if (block.isBlockPowered()){
				for (Entity entity:block.getWorld().spawnArrow(block.getLocation(), new Vector(0,0,0), 0, 0).getNearbyEntities(10, 10, 10)){
					entity.setVelocity(entity.getVelocity().add(genVector(entity, block)));
				}
			}
		}
	}
	private Vector genVector(Entity entity,Block block){
		Location eLoc=entity.getLocation();
		Location bLoc=block.getLocation();
		double s=3/eLoc.distance(bLoc);
		return new Vector(s*(eLoc.getX()-bLoc.getX()), s*(eLoc.getY()-bLoc.getY()), s*(eLoc.getZ()-bLoc.getZ()));
	}
}
