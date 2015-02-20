package org.mcsg.plotmaster.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import static java.lang.Math.*;

class GridGenerator extends ChunkGenerator{

	private Map<String, Object> settings;
	private TreeMap<Integer, Material> blocks = new TreeMap<>();
	private int top;
	private List<BlockPopulator> pops;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })

	//Messy stuff to load settings from groovy
	//But hey, it works!
	public GridGenerator(Map<String, Object> settings){
		this.settings = settings;

		List<Map<String, Object>> levels = (List) ((Map)settings.get("generator")).get("levels");

		for(Map<String, Object> m : levels){ 
			blocks.put((int)Double.parseDouble(m.get("y").toString()), Material.valueOf(m.get("block").toString()));

		}

		top = blocks.lastKey().intValue();
		System.out.println(top);
		
		pops = Arrays.asList((BlockPopulator)new GridPopulator(settings, top));
	}

	
	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		
		byte[][] result = new byte[world.getMaxHeight() / 16][];

		Material cur = Material.AIR;
		for(int y = top; y > 0; y--){
			Material get = blocks.get(y);
			if(get != null)
				cur = get;
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					setBlock(result, x, y, z, (byte)cur.getId());
					biomeGrid.setBiome(x, z , Biome.PLAINS);
				}
			}
		}
		
		return result;
	}

	public void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}


	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return pops;
	}





}
