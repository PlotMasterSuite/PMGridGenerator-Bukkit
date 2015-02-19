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

		h = settings.grid.height
		w = settings.grid.width

		border = Border.load(settings.grid.border)
		print "$w:$h"
	}




	@CompileStatic
	@Override
	public void populate(World world, Random rand, Chunk chunk) {
		if(!border){
			return
		}


		for (int cx = 0; cx < 16; cx++){
			for (int cz = 0; cz < 16; cz++){
				int x = cx + ( 16 * chunk.getX())
				int z = cz + ( 16 *  chunk.getZ())

			//	print "${cx} * ${chunk.getX()} = ${x} : ${cz} * ${chunk.getZ()} =${z}"
				
				SchematicBlock[] blocks = border.getColumnAt(x, z, w, h)

				blocks.eachWithIndex { SchematicBlock block, int i ->
					Block b = chunk.getBlock(cx, top + 1 + i, cz)

					b.setType(Material.valueOf(block.material))
					b.setData(block.getData())
				}
			}
		}
	}






}
