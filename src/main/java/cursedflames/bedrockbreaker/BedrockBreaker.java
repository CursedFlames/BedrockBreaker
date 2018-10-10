package cursedflames.bedrockbreaker;

import org.apache.logging.log4j.Logger;

import cursedflames.bedrockbreaker.block.ModBlocks;
import cursedflames.bedrockbreaker.item.BreakerPick;
import cursedflames.bedrockbreaker.item.ModItems;
import cursedflames.bedrockbreaker.proxy.ISideProxy;
import cursedflames.lib.RegistryHelper;
import cursedflames.lib.config.Config;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = BedrockBreaker.MODID, name = BedrockBreaker.MODNAME, version = "", useMetadata = true)
@Mod.EventBusSubscriber
public class BedrockBreaker {
	@Mod.Instance
	public static BedrockBreaker instance;

	public static final String MODNAME = "Bedrock Breaker";
	public static final String MODID = "bedrockbreaker";

	public static final RegistryHelper registryHelper = new RegistryHelper(MODID);

	public static Config config;

	public static Logger logger;

	@SidedProxy(clientSide = "cursedflames.bedrockbreaker.proxy.ClientProxy", serverSide = "cursedflames.bedrockbreaker.proxy.ServerProxy")
	public static ISideProxy proxy;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		config = new Config(MODID, "1", logger);
		config.preInit(event);

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
}