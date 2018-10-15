package cursedflames.bedrockbreaker.item;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.lib.config.Config.EnumPropSide;
import cursedflames.lib.item.GenericItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	public static Item breakerBlade = null;
	public static Item breakerPick = null;

	public static void registerToRegistry() {
		BedrockBreaker.registryHelper.setAutoaddItemModels(true);

		BedrockBreaker.registryHelper.addItem(breakerBlade = new GenericItem(BedrockBreaker.MODID,
				"breakerBlade", CreativeTabs.MISC));

		BreakerPick.maxDurability = BedrockBreaker.config.addPropInt("breakerPickMaxDurability",
				"General", "Maximum durability of the breaker pick.", 12, EnumPropSide.SERVER, 1,
				Integer.MAX_VALUE);
		BreakerPick.maxDurability.setRequiresMcRestart(true);
		BreakerPick.material = EnumHelper.addToolMaterial("breakerpick", 0,
				BreakerPick.maxDurability.getInt(12), 1, 0, 0);
		BedrockBreaker.registryHelper.addItem(breakerPick = new BreakerPick());
	}
}
