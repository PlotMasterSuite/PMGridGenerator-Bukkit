package org.mcsg.plotmaster.generator

import groovy.transform.CompileStatic;

import java.util.Random;

import org.bukkit.Bukkit
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
	int w2

	def GridPopulator(Map settings, int top){
		this.settings = settings
		this.top = top

		h = settings.grid.height
		w = settings.grid.width

		border = Border.load(settings.grid.border)
		print "$w:$h"

		w2 = 2 * border.getWidth()

	}




	@CompileStatic
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
		if(!border){
			return
		}

		GridGeneratorPlugin.set.add("${chunk.getX()}:${chunk.getZ()}".toString())


		for (int cx = 0; cx < 16; cx++){
			for (int cz = 0; cz < 16; cz++){
				int x = cx + ( 16 * chunk.getX())
				int z = cz + ( 16 *  chunk.getZ())

				//	print "${cx} * ${chunk.getX()} = ${x} : ${cz} * ${chunk.getZ()} =${z}"

				SchematicBlock[] blocks = border.getColumnAt(x, z, w + w2 , h + w2)

				blocks.eachWithIndex { SchematicBlock block, int i ->
					if(block) {
						Block b = chunk.getWorld().getBlockAt(x, top + i, z)

						b.setType(Material.valueOf(block.material))
						b.setData(block.getData())
					}
				}
			}
		}
	}






}
