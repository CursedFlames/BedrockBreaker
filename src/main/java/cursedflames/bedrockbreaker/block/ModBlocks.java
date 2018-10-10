package cursedflames.bedrockbreaker.block;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.block.GenericBlock;

public class ModBlocks {
	public static GenericBlock compressedLapis = null;
	public static GenericBlock unbreakium = null;

	public static void registerToRegistry() {
		compressedLapis = new BlockCompressedLapis();
		unbreakium = new BlockUnbreakium();

		BedrockBreaker.registryHelper.addBlock(compressedLapis).addItemBlock(compressedLapis)
				.addItemBlockModel(compressedLapis);
		BedrockBreaker.registryHelper.addBlock(unbreakium).addItemBlock(unbreakium)
				.addItemBlockModel(unbreakium);
	}
}
