package me.FreeSpace2.Technika;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NukeListener implements Listener{
	Logger out;
	Boolean enabled=true;
	NukeListener(Logger log){
		out=log;
	}
	//If TNT goes off
	@EventHandler
	public void onTNTExplode(EntityExplodeEvent event){
		if(event.getEntity()!=null & enabled){
			if(event.getEntityType()==EntityType.PRIMED_TNT){
				Entity entity=event.getEntity();
				//If it is encased in diamond...
				if(isDiamondNuke(entity)){
					//Let the console know about it
					out.info("Diamond nuke detonation at "+entity.getLocation().getBlockX()+", "+entity.getLocation().getBlockZ()+"!");
					entity.getWorld().createExplosion(entity.getLocation(), 75, true);
					entity.getWorld().createExplosion(entity.getLocation(), 50, true);
					//And cancel the TNT.
					event.setCancelled(true);
				}
				//Is hellishly laggy, so disabled.
				/*if(isEmeraldNuke(entity)){
					out.info("Emerald nuke detonation at "+entity.getLocation().getBlockX()+", "+entity.getLocation().getBlockZ()+"!");
					entity.getWorld().createExplosion(entity.getLocation(), 150, true);
					entity.getWorld().createExplosion(entity.getLocation(), 75, true);
					event.setCancelled(true);
				}*/
			}
		}
	}
	public void disableNuke(){
		enabled=false;
	}
	private boolean isDiamondNuke(Entity entity){
		World world=entity.getWorld();
		Location loc=entity.getLocation();
		byte state = 0;
		for(byte x=-1; x<2; x++){
			for(byte y=-1; y<2; y++){
				for(byte z=-1; z<2; z++){
					 if (world.getBlockAt(new Location(world, loc.getX()+x, loc.getY()+y, loc.getZ()+z)).getType()==Material.DIAMOND_BLOCK){
						 state++;
					 }
				} 
			}
		}
		return state>=26;
		
	}
	@SuppressWarnings("unused")
	private boolean isEmeraldNuke(Entity entity){
		World world=entity.getWorld();
		Location loc=entity.getLocation();
		byte state = 0;
		for(byte x=-1; x<2; x++){
			for(byte y=-1; y<2; y++){
				for(byte z=-1; z<2; z++){
					 if (world.getBlockAt(new Location(world, loc.getX()+x, loc.getY()+y, loc.getZ()+z)).getType()==Material.EMERALD_BLOCK){
						 state++;
					 }
				} 
			}
		}
		return state>=26;
		
	}

}
