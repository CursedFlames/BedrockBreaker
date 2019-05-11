package cursedflames.bedrockbreaker.block;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.block.GenericBlock;

public class ModBlocks {
	public static GenericBlock compressedLapis = null;
	public static GenericBlock unbreakium = null;
	public static GenericBlock activatedUnbreakium = null;
	public static GenericBlock activatedUnbreakiumPink = null;
	public static GenericBlock activatedUnbreakiumWhite = null;

	public static void registerToRegistry() {
		compressedLapis = new BlockCompressedLapis();
		unbreakium = new BlockUnbreakium("unbreakium");
		activatedUnbreakium = new BlockActivatedUnbreakium("activatedUnbreakium");
		activatedUnbreakiumPink = new BlockActivatedUnbreakium("activatedUnbreakiumPink");
		activatedUnbreakiumWhite = new BlockActivatedUnbreakium("activatedUnbreakiumWhite");

		BedrockBreaker.registryHelper.addBlock(compressedLapis).addItemBlock(compressedLapis)
				.addItemBlockModel(compressedLapis);
		BedrockBreaker.registryHelper.addBlock(unbreakium).addItemBlock(unbreakium)
				.addItemBlockModel(unbreakium);
		BedrockBreaker.registryHelper.addBlock(activatedUnbreakium)
				.addItemBlock(activatedUnbreakium).addItemBlockModel(activatedUnbreakium);
		BedrockBreaker.registryHelper.addBlock(activatedUnbreakiumPink)
				.addItemBlock(activatedUnbreakiumPink).addItemBlockModel(activatedUnbreakiumPink);
		BedrockBreaker.registryHelper.addBlock(activatedUnbreakiumWhite)
				.addItemBlock(activatedUnbreakiumWhite).addItemBlockModel(activatedUnbreakiumWhite);
	}
}
