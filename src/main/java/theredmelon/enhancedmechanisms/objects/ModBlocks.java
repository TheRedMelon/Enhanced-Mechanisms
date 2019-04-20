package theredmelon.enhancedmechanisms.objects;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import theredmelon.enhancedmechanisms.itemgroups.ItemGroups;
import theredmelon.enhancedmechanisms.objects.ObjectEnums.MetalBlocks;
import theredmelon.enhancedmechanisms.objects.blocks.BlockProcessable;
import theredmelon.enhancedmechanisms.objects.machines.MachineThreefoldCombiner;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;
import theredmelon.enhancedmechanisms.utils.Reference;

@EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	// Ordinary Metal Blocks
	public static final Block block_storage_copper = new BlockProcessable(MetalBlocks.COPPER);
	public static final Block block_storage_tin = new BlockProcessable(MetalBlocks.TIN);
	public static final Block block_storage_steel = new BlockProcessable(MetalBlocks.STEEL);
	public static final Block block_storage_aluminium = new BlockProcessable(MetalBlocks.ALUMINIUM);
	public static final Block block_storage_brass = new BlockProcessable(MetalBlocks.BRASS);
	public static final Block block_storage_silver = new BlockProcessable(MetalBlocks.SILVER);
	public static final Block block_storage_bronze = new BlockProcessable(MetalBlocks.BRONZE);
	public static final Block block_storage_zinc = new BlockProcessable(MetalBlocks.ZINC);
	public static final Block block_storage_lead = new BlockProcessable(MetalBlocks.LEAD);
	public static final Block block_storage_fluxate = new BlockProcessable(MetalBlocks.FLUXATE);
	
	// Mob Infused Blocks
	public static final Block block_storage_infused_witch = new BlockProcessable(MetalBlocks.INFUSED_WITCH);
	
	// Machines
	public static final Block block_machine_threefold_combiner = new MachineThreefoldCombiner("block_machine_threefold_combiner", Material.GROUND, 5, 30, ItemGroups.blocks);
	
	// TileEntities
	public static TileEntityType<TileEntityThreefoldCombiner> tileEntityThreefoldCombiner;
	
	@SubscribeEvent
	public static void OnRegisterBlocks (RegistryEvent.Register<Block> e) {
		
		e.getRegistry().registerAll(ModObjects.BLOCKS.toArray(new Block[0]));
		
	}
	
	@SubscribeEvent
	public static void OnRegisterTileEntities (RegistryEvent.Register<TileEntityType<?>> e) {
		
		tileEntityThreefoldCombiner = TileEntityType.register(Reference.MOD_ID + ":threefold_combiner", TileEntityType.Builder.create(TileEntityThreefoldCombiner::new));
		
	}
	
}
