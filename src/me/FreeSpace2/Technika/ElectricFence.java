package me.FreeSpace2.Technika;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ElectricFence implements Listener {
	ArrayList<Fence> fences=new ArrayList<Fence>();
	//Wait for a player to move.
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		//If the player is literally pressed up against an Iron Fence...
		if(event.getPlayer().getLocation().getBlock().getType()==Material.IRON_FENCE){
			Player player=event.getPlayer();
			Block block=player.getLocation().getBlock();
			//Get the location of the center of the block to get the throwback vector.
			Location blockLoc=block.getLocation().add(new Vector(0.5,0.5,0.5));
			//Check if the fence is powered.
			if(isPoweredUp(block)){
				//Compute the vector to throw the player back on.
				Vector bounceVec=player.getLocation().subtract(blockLoc).toVector().normalize();
				//Throw back the player.
				player.setVelocity(player.getVelocity().add(bounceVec.multiply(1.2).add(new Vector(0,0.3,0))));
				player.damage(2);
			}
		}
	}
	//If an iron fence powers...
	@EventHandler
	public void onRedstoneChange(BlockPhysicsEvent event){
		if (event.getBlock().getType()==Material.IRON_FENCE){
			Block block=event.getBlock();
			if (block.isBlockIndirectlyPowered()){
				//Let the player know that it is powered.
				block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
			}
		}
	}

	private boolean isPoweredUp(Block block) {
		return true;
		
		//A bunch of commented code because it doesn't work.
		/*World world=block.getWorld();
		Block powerBlock;
	    for(int x=0;x<6;x++){
	    	for(int y=-2;y<2;y++){
	    		powerBlock=(block.getLocation().add(new Vector(x,y,0))).getBlock();
	    		if(powerBlock.getType()==Material.IRON_FENCE){
	    			if(powerBlock.isBlockPowered()){
	    				return true;
	    			} 
	    		}
	    		powerBlock=(block.getLocation().add(new Vector(-x,y,0))).getBlock();
	    		if(powerBlock.getType()==Material.IRON_FENCE){
	    			if(powerBlock.isBlockPowered()){
	    				return true;
	    			} 
	    		}
		    }
	    }
	    for(int z=0;z<6;z++){
	    	for(int y=-2;y<2;y++){
	    		powerBlock=(block.getLocation().add(new Vector(0,y,z))).getBlock();
	    		if(powerBlock.getType()==Material.IRON_FENCE){
	    			if(powerBlock.isBlockPowered()){
	    				return true;
	    			} 
	    		}
	    		powerBlock=(block.getLocation().add(new Vector(0,y,z))).getBlock();
	    		if(powerBlock.getType()==Material.IRON_FENCE){
	    			if(powerBlock.isBlockPowered()){
	    				return true;
	    			}
	    		}
		    }
	    }
		return false;*/
	}
}
class Fence{
	
}
