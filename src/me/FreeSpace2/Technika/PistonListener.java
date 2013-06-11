package me.FreeSpace2.Technika;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
//import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
//import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PistonListener implements Listener{
	Technika main;
	List<Integer> disabledBlocks;
	PistonListener(Technika main, List<Integer> list){
		this.main=main;
		this.disabledBlocks=list;
	}
	//A bunch of debug code that is useless now.
	/*@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getMaterial() == Material.ENDER_PORTAL){
			Player player = event.getPlayer();
			Material material=event.getClickedBlock().getType();
			player.sendMessage("Block: "+material.isBlock());
			player.sendMessage("Burnable: "+material.isBurnable());
			player.sendMessage("Edible: "+material.isEdible());
			player.sendMessage("Flammable: "+material.isFlammable());
			player.sendMessage("Occluding: "+material.isOccluding());
			player.sendMessage("Record: "+material.isRecord());
			player.sendMessage("Transparent: "+material.isTransparent());
			player.sendMessage("Solid: "+material.isSolid());
			event.setCancelled(true);
		}
	}*/
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonPush(BlockPhysicsEvent event){
		Block piston = event.getBlock();
		if(piston.getType()==Material.PISTON_BASE | piston.getType()==Material.PISTON_STICKY_BASE && (piston.isBlockPowered() | piston.isBlockIndirectlyPowered()) && (true | piston.hasMetadata("isExtended"))){
			BlockFace Direction = getDirection(piston);
			Block block = event.getBlock().getRelative(Direction, 1);
			Block chestBlock = event.getBlock().getRelative(Direction, 2);
		    if(isValidBlock(block.getType()) && (chestBlock.getType()==Material.CHEST | chestBlock.getType()==Material.HOPPER | chestBlock.getType()==Material.DROPPER | chestBlock.getType()==Material.DISPENSER)){
		    	ItemStack is=new ItemStack(block.getType(), 1);
		    	is.setDurability(block.getData());
		    	ItemStack[] iStack={is};
		    	@SuppressWarnings("rawtypes")
		    	HashMap failList;
		    	switch (chestBlock.getType()){
		    		case CHEST:
		    			Chest chest=((Chest) chestBlock.getState());
		    			failList = chest.getBlockInventory().addItem(iStack);
		    			break;
		    		case DISPENSER:
		    			Dispenser dispenser=((Dispenser) chestBlock.getState());
		    			failList = dispenser.getInventory().addItem(iStack);
		    			break;
		    		case DROPPER:
		    		  	Dropper dropper=((Dropper) chestBlock.getState());
		    		  	failList = dropper.getInventory().addItem(iStack);
		    		  	break;
		    		case HOPPER:
		    			Hopper hopper=((Hopper) chestBlock.getState());
		    			failList = hopper.getInventory().addItem(iStack);
		    			break;
		    		default:
		    			failList = null;
		    			break;
			      }
		    	if (failList.isEmpty()){
		    		block.setTypeId(0);
		    	}else{
		    		event.setCancelled(true);
		    	}
		    }
		}
	}
	private boolean isValidBlock(Material blockMaterial){
		return (blockMaterial != Material.BEDROCK) && (blockMaterial != Material.AIR) && (blockMaterial != Material.WATER) && (blockMaterial != Material.LAVA && (blockMaterial != Material.STATIONARY_LAVA) && (blockMaterial != Material.STATIONARY_WATER) && (blockMaterial != Material.PISTON_EXTENSION) && (blockMaterial.isOccluding() | blockMaterial == Material.GLASS));// && !disabledBlocks.contains(blockMaterial.getId()));
	}
	private BlockFace getDirection(Block block){
		BlockFace face = BlockFace.DOWN;
	    int data = block.getData();
	    if (data >= 8) {
	      data -= 8;
	    }
	    switch (data) {
	    case 0:
	    	face = BlockFace.DOWN;
	    	break;
	    case 1:
	    	face = BlockFace.UP;
	    	break;
	    case 2:
	    	face = BlockFace.NORTH;
	    	break;
	    case 3:
	    	face = BlockFace.SOUTH;
	    	break;
	    case 4:
	    	face = BlockFace.WEST;
	    	break;
	    case 5:
	    	face = BlockFace.EAST;
	    }
	    return face;
	}
}
