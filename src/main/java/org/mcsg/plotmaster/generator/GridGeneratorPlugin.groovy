package org.mcsg.plotmaster.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin
import org.mcsg.plotmaster.PlotMaster;

class GridGeneratorPlugin extends JavaPlugin{

	Map<String, Map> settings = [:]
	
	
	
	void onEnable(){
		List list = PlotMaster.getInstance().getConfigurationSelectionPerManagerType("grid")
		
		list.forEach {
			settings.put(it.world, it)
		}
		
		
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new GridGenerator(settings.get(worldName));
	}


}
