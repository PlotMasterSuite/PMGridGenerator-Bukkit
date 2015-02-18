package org.mcsg.plotmaster.generator

import groovy.transform.CompileStatic;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block
import org.bukkit.generator.BlockPopulator;
import org.mcsg.plotmaster.schematic.Border;
import org.mcsg.plotmaster.schematic.SchematicBlock;


class GridPopulator extends BlockPopulator{

	Map settings
	int top
	int h, w
	
	
	Border border
	
	def GridPopulator(Map settings, int top){
		this.settings = settings
		this.top = top
		
		h = settings.cellHeight
		w = settings.cellWidth
		
		border = Border.load(settings.border)
	}
	
	
	
	
	@CompileStatic
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
		if(!border){
			return
		}
		
		
		for(cx in 0..16){
			for(cz in 0..16){
				int x = cx * chunk.getX()
				int z = cz * chunk.getZ()
				
				SchematicBlock[] blocks = border.getColumnAt(x, z, w, h, top)
				
				blocks.eachWithIndex { SchematicBlock block, int i ->
					Block b = chunk.getBlock(cx, top + i, cz)
					
					b.setType(Material.valueOf(block.material))
					b.setData(block.getData())
				}
			}
		}
	}
	
	
	
	
	
	
}
