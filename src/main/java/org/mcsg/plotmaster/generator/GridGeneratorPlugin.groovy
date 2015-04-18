package org.mcsg.plotmaster.generator

import groovy.transform.CompileStatic;

import java.util.concurrent.ConcurrentHashMap

import org.bukkit.Bukkit;
import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin
import org.mcsg.plotmaster.PlotMaster;
@CompileStatic

class GridGeneratorPlugin extends JavaPlugin implements Listener{

	static Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>())
	
	private Map<String, Map> settings = [:]
	private ChunkGenerator gen
	
	void onEnable(){
		List list = PlotMaster.getInstance().getConfigurationSelectionPerManagerType("grid")
		
		println list
		
		list.each { Map m ->
			println m
			settings.put(m.world.toString(), m)
		}
		
		Bukkit.getPluginManager().registerEvents(this,this)
		
	} 

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		gen = new GridGenerator(settings.get(worldName));
	}
		
	@EventHandler
	public void load(ChunkPopulateEvent e){
		Chunk chunk = e.getChunk()
		
		boolean b = set.remove("${chunk.getX()}:${chunk.getZ()}".toString())
		
		if(set.size() > 25)
			print set.size()
		
		if(!b){
			print "Failed to populate ${chunk.getX()}:${chunk.getZ()}.. Attempting to repopulate."
			for(BlockPopulator pop : gen.getDefaultPopulators(null)){
				pop.populate(e.getWorld(), null, e.getChunk())
			}
			set.remove("${chunk.getX()}:${chunk.getZ()}".toString())
			
		}
		
	}


}
