package cursedflames.bedrockbreaker.item;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.item.GenericItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {
	public static Item breakerBlade = null;
	public static Item breakerPick = null;

	public static void registerToRegistry() {
		BedrockBreaker.registryHelper.setAutoaddItemModels(true);

		BedrockBreaker.registryHelper.addItem(breakerBlade = new GenericItem(BedrockBreaker.MODID,
				"breakerBlade", CreativeTabs.MISC));

		BedrockBreaker.registryHelper.addItem(breakerPick = new BreakerPick());
	}
}
