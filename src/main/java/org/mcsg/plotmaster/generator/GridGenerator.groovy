package org.mcsg.plotmaster.generator

import groovy.transform.CompileStatic;

import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.ChunkGenerator.BiomeGrid

class GridGenerator extends ChunkGenerator{

	Map settings
	TreeMap<Integer, Integer> blocks = [:]
	int top
	
	def GridGenerator(Map settings){
		this.settings = settings
		
		List levels = settings.levels;
		
		levels.forEach {
			blocks.put(it.y, it.block)
		}
		
		top = blocks.getLastEntry().getKey()
	}

	@CompileStatic
	@Override
	byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		byte[][] result = new byte[world.getMaxHeight() / 16][];

		def top = world.getMaxHeight()
		def cur = 0
		for(x in 0..16){
			for(z in 0..16){
				for(y in top..0){
					def get = blocks.get(y)
					if(get)
						cur = get
						
					setBlock(result, x, y, z, (byte)cur)
				}
			}
		}
	}

	@CompileStatic
	void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}


	@Override
	List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(new GridPopulator(settings, top));
	}





}
