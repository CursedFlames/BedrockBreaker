package cursedflames.bedrockbreaker.block;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.block.GenericBlock;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

//TODO oredictionary?
public class BlockCompressedLapis extends GenericBlock {
	public BlockCompressedLapis() {
		super(BedrockBreaker.MODID, "compressedLapis", CreativeTabs.BUILDING_BLOCKS, Material.ROCK,
				6, 30);
	}
}
