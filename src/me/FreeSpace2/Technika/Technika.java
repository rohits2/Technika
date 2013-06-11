package me.FreeSpace2.Technika;


import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


import net.oicp.wzypublic.itemmenu.ItemMenuService;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class Technika extends JavaPlugin{
	Logger out;
	Configuration config;
	@SuppressWarnings("unchecked")
	public void onEnable(){
		initConfig();
		out=this.getLogger();
		//A bunch of code to allow turning options on/off
		if(config.getBoolean("enableNuke")){
			out.info("Enabling nukes!");
			this.getServer().getPluginManager().registerEvents(new NukeListener(this.getLogger()), this);
		};
		if(config.getBoolean("pistons.pistonStorage.enabled")){
			this.getServer().getPluginManager().registerEvents(new PistonListener(this, (List<Integer>) config.getList("pistons.pistonStorage.disabledBlocks")), this);
			out.info("Enabling piston storage!");
		};
		if(config.getBoolean("enableFireBlock")){
			this.getServer().getPluginManager().registerEvents(new FireBlock(), this);
			out.info("Enabling fire blocks!");
		};
		if(config.getBoolean("enableHealBlock")){
			this.getServer().getPluginManager().registerEvents(new HealthBlock(), this);
			out.info("Enabling healing blocks!");
		};
		if(config.getBoolean("pistons.pistonPushers.enabled")){
			this.getServer().getPluginManager().registerEvents(new ForcePiston(config.getDouble("pistons.pistonPushers.pushForce")), this);
			out.info("Enabling pushing pistons!");
		};
		if(config.getBoolean("pistons.pistonPushers.enabled")){
			this.getServer().getPluginManager().registerEvents(new ForcePiston(config.getDouble("pistons.pistonPushers.pushForce")), this);
			out.info("Enabling pushing pistons!");
		};
		if(config.getBoolean("autoCrafters.enabled")){
			this.getServer().getPluginManager().registerEvents(new AutomaticCrafter(getServer(), this, (List<Integer>) config.getList("autoCrafters.disabledBlocks"), config.getBoolean("autoCrafters.shapedCraftingAllowed")), this);
			out.info("Enabling shapeless crafters!");
		};
		if(config.getBoolean("electricFence")){
			out.info("Enabling electric fences!");
			out.info("\nWARNING! BETA FEATURE\n");
			this.getServer().getPluginManager().registerEvents(new ElectricFence(), this);
		};
		if(config.getBoolean("comboLock")){
			out.info("Enabling combo blocks!");
			out.severe("\nWARNING! LETHAL FEATURE\n");
			this.getServer().getPluginManager().registerEvents(new ComboLock(getServer().getServicesManager().getRegistration(ItemMenuService.class)), this);
		};
		/*if(config.getBoolean("allowForceBlock")){
			switch (config.getString("forceBlockMode")){
				case "cannon":
					this.getServer().getPluginManager().registerEvents(new ForceCannon(), this);
					break;
				case "beam":
					this.getServer().getPluginManager().registerEvents(new ForceBeam(), this);
					break;
			}
		}*/
		
	}
	public void onDisable(){
		out.info("Closing...");
	}

	private void initConfig(){
		config=this.getConfig();
		Integer[] list0 = {Material.BEDROCK.getId(), Material.OBSIDIAN.getId(), Material.MOB_SPAWNER.getId()};
		Integer[] list1 = {Material.BEACON.getId()};
		config.addDefault("enableNuke", false);
		config.addDefault("enableFireBlock", true);
		config.addDefault("enableHealBlock", true);
		config.addDefault("pistons.pistonStorage.enabled", true);
		config.addDefault("pistons.pistonStorage.disabledBlocks", Arrays.asList(list0));
		config.addDefault("pistons.pistonPushers.enabled", true);
		config.addDefault("pistons.pistonPushers.pushForce", 1.2);
		config.addDefault("autoCrafters.enabled",true);
		config.addDefault("autoCrafters.shapedCraftingAllowed",true);
		config.addDefault("autoCrafters.disabledBlocks",Arrays.asList(list1));
		/*config.addDefault("allowForceBlock", false);
		config.addDefault("forceBlockMode", "beam");*/
		config.options().copyDefaults(true);
		this.saveConfig();
	}
}
