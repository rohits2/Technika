package me.FreeSpace2.Technika;

import java.util.ArrayList;

import net.oicp.wzypublic.itemmenu.ItemMenuService;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class ComboLock implements Listener {
	ArrayList<Object> comboList=new ArrayList<Object>();
	ItemMenuService ISP;
	public ComboLock(RegisteredServiceProvider<ItemMenuService> rsp){
		ISP=rsp.getProvider();
		ItemStack is=new ItemStack(Material.WOOL);
		is.setDurability((short) 0);
		comboList.add(ISP.makeOption("White", true, is, 0, 0));
		is.setDurability((short) 14);
		comboList.add(ISP.makeOption("Red", true, is, 1, 0));
		is.setDurability((short) 13);
		comboList.add(ISP.makeOption("Green", true, is, 2, 0));
		is.setDurability((short) 11);
		comboList.add(ISP.makeOption("Blue", true, is, 3, 0));
		is.setDurability((short) 4);
		comboList.add(ISP.makeOption("Yellow", true, is, 4, 0));
		is.setDurability((short) 1);
		comboList.add(ISP.makeOption("Orange", true, is, 5, 0));
		is.setDurability((short) 10);
		comboList.add(ISP.makeOption("Purple", true, is, 6, 0));
		is.setDurability((short) 15);
		comboList.add(ISP.makeOption("Black", true, is, 7, 0));
		is.setDurability((short) 7);
		comboList.add(ISP.makeOption("Gray", true, is, 8, 0));
		is.setDurability((short) 6);
		comboList.add(ISP.makeOption("Pink", true, is, 9, 0));
	}
	//When redstone changes...
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event){
		try{
			if(event.getClickedBlock().getType()==Material.IRON_BLOCK && event.getClickedBlock().isBlockPowered()){
				ISP.createMenu(event.getPlayer(),"Combination Lock", comboList, true);
			}
		}catch (Exception e){};
	}
}
