package me.FreeSpace2.Technika;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import com.norcode.bukkit.autocrafter.CraftAttempt;

public class AutomaticCrafter implements Listener {
	private static EnumSet<BlockFace> sides = EnumSet.of(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH);
	Server server;
	JavaPlugin plugin;
	List<Integer> deniedMaterials;
	boolean allowedCrafting;
	public AutomaticCrafter(Server server, JavaPlugin plugin, List<Integer> deniedMaterials, boolean allowedCrafting) {
		this.server=server;
		this.plugin=plugin;
		this.deniedMaterials=deniedMaterials;
		this.allowedCrafting=allowedCrafting;
	}
	@EventHandler
	public void onDropperDispense(BlockDispenseEvent event){
		final Dropper dropper = (Dropper)event.getBlock().getState();
		final Inventory drCT=dropper.getInventory();
		ItemFrame frame=getAttachedFrame(event.getBlock());
		boolean craftSuccessful=true;
		if(frame==null){
			return;
		}
		if(frame.getItem()==null){
			return;
		}
		if(deniedMaterials.contains(frame.getItem())){
			return;
		}
		ArrayList<ItemStack> ingredients;
		Inventory clone = null;
		ItemStack cI = null;
		for(Recipe recipe:server.getRecipesFor(frame.getItem())){
			if(recipe instanceof ShapedRecipe & !allowedCrafting){
				return;
			}
			ingredients=(ArrayList<ItemStack>) CraftAttempt.getIngredients(recipe);
			clone = CraftAttempt.cloneInventory(plugin, dropper.getInventory());
			for(ItemStack is:ingredients){
				if(!CraftAttempt.removeItem(clone, is.getType(), is.getData().getData(), is.getAmount())){
					craftSuccessful=false;
					break;
				}
			}
			cI=recipe.getResult();
		}
		if(!craftSuccessful ){
			event.setCancelled(true);
			return;
		}else{
			drCT.setContents(clone.getContents());
			event.setItem(cI);
		}
	}
	@EventHandler
	public void onDropperPush(InventoryMoveItemEvent event){
		if(event.getSource().getHolder() instanceof Dropper){
			final Dropper dropper = (Dropper) event.getSource().getHolder();
			final Inventory drCT=event.getInitiator();
			ItemFrame frame=getAttachedFrame(dropper.getBlock());
			boolean craftSuccessful=true;
			if(frame==null){
				return;
			}
			if(frame.getItem()==null){
				return;
			}
			if(deniedMaterials.contains(frame.getItem())){
				return;
			}
			ArrayList<ItemStack> ingredients;
			Inventory clone = null;
			ItemStack cI = null;
			for(Recipe recipe:server.getRecipesFor(frame.getItem())){
				if(recipe instanceof ShapedRecipe & !allowedCrafting){
					return;
				}
				ingredients=(ArrayList<ItemStack>) CraftAttempt.getIngredients(recipe);
				clone = CraftAttempt.cloneInventory(plugin, dropper.getInventory());
				for(ItemStack is:ingredients){
					if(!CraftAttempt.removeItem(clone, is.getType(), is.getData().getData(), is.getAmount())){
						craftSuccessful=false;
						break;
					}
				}
				cI=recipe.getResult();
			}
			if(!craftSuccessful ){
				event.setCancelled(true);
				return;
			}else{
				drCT.setContents(clone.getContents());
				event.setItem(cI);
			}
		}
	}
	public ItemFrame getAttachedFrame(Block block){
	    Location dropperLoc = new Location(block.getWorld(), block.getX() + 0.5D, block.getY() + 0.5D, block.getZ() + 0.5D);
	    Chunk thisChunk = block.getChunk();
	    Chunk thatChunk = null;
	    HashSet<Chunk> chunksToCheck = new HashSet<Chunk>();
	    chunksToCheck.add(thisChunk);
	    for (BlockFace side:sides) {
	      thatChunk = block.getRelative(side).getChunk();
	      if (!thisChunk.equals(thatChunk)) {
	        chunksToCheck.add(thatChunk);
	      }
	    }
	    for (Chunk c : chunksToCheck) {
	      for (Entity e : c.getEntities()) {
	        if ((e.getType().getTypeId() == 18) && (e.getLocation().distanceSquared(dropperLoc) == 0.31640625D)) {
	          return (ItemFrame)e;
	        }
	      }
	    }
	    return null;
	  }
}
