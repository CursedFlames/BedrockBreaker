package cursedflames.bedrockbreaker.item;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bedrockbreaker.BedrockBreaker;
import cursedflames.bedrockbreaker.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BreakerPick extends ItemPickaxe {
	public static ToolMaterial material;
	public static Property maxCharge;
	public static Property chargePerTick;
	public static Property maxDurability;

	public BreakerPick() {
		super(material);
		setUnlocalizedName(BedrockBreaker.MODID+".breakerPick");
		setRegistryName(new ResourceLocation(BedrockBreaker.MODID, "breakerpick"));
		setCreativeTab(CreativeTabs.TOOLS);
		maxCharge = BedrockBreaker.config.get("breakerPickMaxCharge", "General", 9,
				"Number of lapis pieces that can be stored in the breaker pick. This is also the number of pieces needed to break one block of bedrock."
				);
		chargePerTick = BedrockBreaker.config.get("breakerPickChargePerTick", "General", 0.1125,
				"Number of lapis pieces charged per usage of the breaker pick. This isn't every tick, might be based on the swing speed?"
				);
		material = EnumHelper.addToolMaterial("breakerpick", 0, 12, 1, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		// TODO use proxies instead of being lazy and using deprecated I18n
		String base = getUnlocalizedName()+".tooltip";
		boolean shift = GuiScreen.isShiftKeyDown();
		tooltip.add(BedrockBreaker.proxy.translate(base));
		NBTTagCompound tag = stack.getTagCompound();
		if (tag==null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		double charge = tag.getDouble("lapisCharge");
		int stored = tag.getInteger("lapisStored");
		int max = maxCharge.getInt(9);
		tooltip.add(BedrockBreaker.proxy.translateWithArgs(base+".charge",
				String.format("%.2f", charge)+"/"+max));
		tooltip.add(BedrockBreaker.proxy.translateWithArgs(base+".stored", String.valueOf(stored)));
		if (!shift) {
			tooltip.add(BedrockBreaker.proxy.translate(base+".shift"));
		} else {
			tooltip.add(BedrockBreaker.proxy.translate(base+".1"));
			tooltip.add(BedrockBreaker.proxy.translate(base+".2"));
			tooltip.add(BedrockBreaker.proxy.translate(base+".3"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
			EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound tag = stack.getTagCompound();
		if (tag==null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		double charge = tag.getDouble("lapisCharge");
		int stored = tag.getInteger("lapisStored");
		if (charge>=maxCharge.getInt(9)) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
		charge += chargePerTick.getDouble(0.1125); // TODO testing
		if (charge>maxCharge.getInt(9)) {
			charge = maxCharge.getInt(9);
		}
		if (charge>stored) {
			IInventory inv = player.inventory;
			for (int i = 0; i<inv.getSizeInventory(); i++) {
				ItemStack invStack = inv.getStackInSlot(i);
				if (invStack.isEmpty()||invStack.getCount()==0)
					continue;
				if (invStack.getItem()==Items.DYE&&invStack.getItemDamage()==4) {
					invStack.splitStack(1);
					stored++;
					break;
				} else if (invStack.getItem()==Item.getItemFromBlock(Blocks.LAPIS_BLOCK)) {
					invStack.splitStack(1);
					stored += 9;
					break;
				} else if (invStack.getItem()==Item.getItemFromBlock(ModBlocks.compressedLapis)) {
					invStack.splitStack(1);
					stored += 81;
					break;
				}
			}
		}
		if (charge>stored) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
		tag.setDouble("lapisCharge", charge);
		tag.setInteger("lapisStored", stored);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag==null)
			return false;
		return tag.getDouble("lapisCharge")>=maxCharge.getInt(9);
	}

//	@SubscribeEvent
//	public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
//		EntityPlayer player = event.getEntityPlayer();
//		EnumHand hand = player.getActiveHand();
//		if (hand==null)
//			hand = EnumHand.MAIN_HAND;
//		ItemStack stack = player.getHeldItem(hand);
//		BedrockBreaker.logger.info("harvestCheck");
//		if (stack.isEmpty()||stack.getItem()!=ModItems.breakerPick)
//			return;
//		BedrockBreaker.logger.info("pick");
//		IBlockState state = event.getTargetBlock();
//		if (!state.getBlock().equals(Blocks.BEDROCK))
//			return;
//		BedrockBreaker.logger.info("bedrock");
//		event.setCanHarvest(true);
//	}
//
//	@SubscribeEvent
//	public static void onHarvestAttempt(PlayerEvent.BreakSpeed event) {
//		EntityPlayer player = event.getEntityPlayer();
//		EnumHand hand = player.getActiveHand();
//		if (hand==null)
//			hand = EnumHand.MAIN_HAND;
//		ItemStack stack = player.getHeldItem(hand);
//		BedrockBreaker.logger.info("breakspeed "+stack.getUnlocalizedName());
//		if (stack.isEmpty()||stack.getItem()!=ModItems.breakerPick)
//			return;
//		BedrockBreaker.logger.info("pick");
//		IBlockState state = event.getState();
//		if (!state.getBlock().equals(Blocks.BEDROCK))
//			return;
//		BedrockBreaker.logger.info("bedrock");
//		event.setNewSpeed(10F);
//	}
//
	@SubscribeEvent
	public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (stack.isEmpty()||stack.getItem()!=ModItems.breakerPick)
			return;
		IBlockState state = event.getWorld().getBlockState(event.getPos());
		if (!state.getBlock().equals(Blocks.BEDROCK))
			return;
		NBTTagCompound tag = stack.getTagCompound();
		if (tag==null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		long lastMineTick = tag.getLong("lastMineTick");
		// TODO better way of preventing instamine
		// abs in case world time is reset
		BedrockBreaker.logger.info(lastMineTick+" "+event.getWorld().getTotalWorldTime());
		if (Math.abs(event.getWorld().getTotalWorldTime()-lastMineTick)<=10)
			return;
		double charge = tag.getDouble("lapisCharge");
		int stored = tag.getInteger("lapisStored");
		int max = maxCharge.getInt(9);
		event.getWorld().destroyBlock(event.getPos(), false);
		if (charge<max||stored<max) {
			stack.damageItem(1, event.getEntityPlayer());
		} else {
			charge -= max;
			stored -= max;
			tag.setDouble("lapisCharge", charge);
			tag.setInteger("lapisStored", stored);
		}
		tag.setLong("lastMineTick", event.getWorld().getTotalWorldTime());
		// this doesn't work
//		event.getEntityPlayer().getCooldownTracker().setCooldown(ModItems.breakerPick, 10);
	}

//	@SubscribeEvent
//	@SideOnly(Side.CLIENT)
//	public static void onTick(TickEvent.ClientTickEvent event) {
//		if (event.phase!=Phase.END)
//			return;
//		PlayerControllerMP controller = Minecraft.getMinecraft().playerController;
//		EntityPlayer player = Minecraft.getMinecraft().player;
//		if (!controller.getIsHittingBlock())
//			return;
//		EnumHand hand = player.getActiveHand();
//		if (hand==null)
//			hand = EnumHand.MAIN_HAND;
//		ItemStack stack = player.getHeldItem(hand);
//		if (stack.getItem()!=ModItems.breakerPick)
//			return;
//		// TODO check block being broken
//		
//	}

//	@SubscribeEvent
//	public static void onBlockBreak(BlockEvent.BreakEvent event) {
//		if (event.getExpToDrop()<0) {
//			BedrockBreaker.logger.info("xp < 0");
//			event.setExpToDrop(0); // TODO
//		}
//	}

	@SubscribeEvent
	public static void onAnvil(AnvilUpdateEvent event) {
		ItemStack stack1 = event.getLeft();
//		ItemStack stack2 = event.getRight();
		if (stack1.isEmpty() /* || stack2.isEmpty() */)
			return;
		if (stack1.getItem()==ModItems.breakerPick) {
			event.setCanceled(true);
		}
	}
}
