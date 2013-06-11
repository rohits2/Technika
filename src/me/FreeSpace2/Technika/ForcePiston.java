package me.FreeSpace2.Technika;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.util.Vector;

public class ForcePiston implements Listener{
	private double pForce=1.2;
	ForcePiston(double force){
		pForce=force;
	}
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event) {
		BlockFace direction=event.getDirection();
		//For every entity in the chunk of the piston:
		for(Entity entity:event.getBlock().getChunk().getEntities()){
			//If the entity is facing the piston exactly opposite the way the piston is facing the entity
			if(entity.getLocation().getBlock().getRelative(direction.getOppositeFace()).equals(event.getBlock())){
				//Shove the entity away.
				entity.setVelocity(entity.getVelocity().add(computeVector(direction.getOppositeFace())));
			}
		}
	}
	//get the vector the entity should be shoved along
	private Vector computeVector(BlockFace direction){
		switch (direction){
		case NORTH:
			return new Vector(0,0,-pForce);
		case SOUTH:
			return new Vector(0,0,pForce);
		case EAST:
			return new Vector(pForce,0,0);
		case WEST:
			return new Vector(-pForce,0,0);
		case UP:
			return new Vector(0,-pForce,0);
		case DOWN:
			return new Vector(0,pForce,0);
		default:
			return null;
			
		}	
	}
}
