package cursedflames.bedrockbreaker;

import org.apache.logging.log4j.Logger;

import cursedflames.bedrockbreaker.block.ModBlocks;
import cursedflames.bedrockbreaker.item.BreakerPick;
import cursedflames.bedrockbreaker.item.ModItems;
import cursedflames.bedrockbreaker.proxy.ISideProxy;
import cursedflames.lib.RegistryHelper;
import cursedflames.lib.config.Config;
import cursedflames.lib.config.Config.EnumPropSide;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = BedrockBreaker.MODID, name = BedrockBreaker.MODNAME, version = "", useMetadata = true)
@Mod.EventBusSubscriber
public class BedrockBreaker {
	@Mod.Instance
	public static BedrockBreaker instance;

	public static final String MODNAME = "Bedrock Breaker";
	public static final String MODID = "bedrockbreaker";

	public static final RegistryHelper registryHelper = new RegistryHelper(MODID);

	public static Config config;
	public static Property enablePick;

	public static Logger logger;

	@SidedProxy(clientSide = "cursedflames.bedrockbreaker.proxy.ClientProxy", serverSide = "cursedflames.bedrockbreaker.proxy.ServerProxy")
	public static ISideProxy proxy;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		config = new Config(MODID, "1", logger);
		config.preInit(event);
		enablePick = config.addPropBoolean("enablePick", "General",
				"Is the pickaxe recipe enabled?", true, EnumPropSide.SERVER);

		MinecraftForge.EVENT_BUS.register(BreakerPick.class);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerToRegistry();
		registryHelper.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.registerToRegistry();
		registryHelper.registerItems(event);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registryHelper.registerModels();
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
//		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
//		PacketHandler.registerMessages();
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		config.postInit(event);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		if (!enablePick.getBoolean(true))
			return;
		//@formatter:off
		event.getRegistry().register(
				new ShapedOreRecipe(new ResourceLocation(MODID, "breaker_pick"),
				ModItems.breakerPick, new String[] {
				"bSb",
				" s ",
				" s "},
				'b', ModItems.breakerBlade,
				'S', Items.NETHER_STAR,
				's', Items.STICK)
				.setRegistryName(new ResourceLocation(MODID, "breaker_pick")));
		event.getRegistry().register(
				new ShapedOreRecipe(new ResourceLocation(MODID, "breaker_blade"),
				ModItems.breakerBlade, new String[] {
				"edl",
				"llc",
				"edl"},
				'e', Items.EMERALD,
				'd', Items.DIAMOND,
				'l', Item.getItemFromBlock(Blocks.LAPIS_BLOCK),
				'c', Item.getItemFromBlock(ModBlocks.compressedLapis))
				.setRegistryName(new ResourceLocation(MODID, "breaker_blade")));
	}
}