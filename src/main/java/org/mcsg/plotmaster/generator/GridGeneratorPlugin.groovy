package org.mcsg.plotmaster.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

class GridGeneratorPlugin extends JavaPlugin{

	def settings = [:]
	
	
	
	void onEnable(){
		
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return null;
	}


}
