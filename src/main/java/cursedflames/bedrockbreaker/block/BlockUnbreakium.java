package cursedflames.bedrockbreaker.block;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.block.GenericBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockUnbreakium extends GenericBlock {
	public BlockUnbreakium() {
		super(BedrockBreaker.MODID, "unbreakium", CreativeTabs.BUILDING_BLOCKS, Material.ROCK,
				Integer.MAX_VALUE, Integer.MAX_VALUE);
		setBlockUnbreakable();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos,
			Entity entity) {
		return false;
	}
}
