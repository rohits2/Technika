package me.FreeSpace2.Technika;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealthBlock implements Listener {
	//If a player moves
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player=event.getPlayer();
		Block block=player.getWorld().getBlockAt(player.getLocation().add(new Location(player.getWorld(), 0,-1,0)));
		//onto a powered Emerald Block
		if(block.getType()==Material.EMERALD_BLOCK && block.isBlockPowered()){
			//Then let him/her know about it...
			player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
			//And heal them.
			player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20,5));
		}
		
	}
}
