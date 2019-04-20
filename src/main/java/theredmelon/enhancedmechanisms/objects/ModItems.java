package theredmelon.enhancedmechanisms.objects;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;
import theredmelon.enhancedmechanisms.itemgroups.ItemGroups;
import theredmelon.enhancedmechanisms.objects.ObjectEnums.CraftingMaterials;
import theredmelon.enhancedmechanisms.objects.ObjectEnums.Ingots;
import theredmelon.enhancedmechanisms.objects.items.ItemProcessable;
import theredmelon.enhancedmechanisms.objects.tools.ToolSaw;
import theredmelon.enhancedmechanisms.utils.Reference;

@EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class ModItems {
	
	// Ordinary Metal Ingots
	public static final Item ingot_copper = new ItemProcessable(Ingots.COPPER);
	public static final Item ingot_tin = new ItemProcessable(Ingots.TIN);
	public static final Item ingot_steel = new ItemProcessable(Ingots.STEEL);
	public static final Item ingot_aluminium = new ItemProcessable(Ingots.ALUMINIUM);
	public static final Item ingot_brass = new ItemProcessable(Ingots.BRASS);
	public static final Item ingot_silver = new ItemProcessable(Ingots.SILVER);
	public static final Item ingot_bronze = new ItemProcessable(Ingots.BRONZE);
	public static final Item ingot_zinc = new ItemProcessable(Ingots.ZINC);
	public static final Item ingot_lead = new ItemProcessable(Ingots.LEAD);
	public static final Item ingot_fluxate = new ItemProcessable(Ingots.FLUXATE);
	
	// Mob Infused Ingots
	public static final Item ingot_infused_witch = new ItemProcessable(Ingots.INFUSED_WITCH);
	
	public static final Item assembly_fragment = new ItemProcessable(CraftingMaterials.ASSEMBLY_FRAGMENT);
	public static final Item dust_charcoal = new ItemProcessable(CraftingMaterials.POWDER_CHARCOAL);
	public static final Item processor_crude = new ItemProcessable(CraftingMaterials.CRUDE_PROCESSOR);
	public static final Item heatsink_crude = new ItemProcessable(CraftingMaterials.CRUDE_HEATSINK);
	public static final Item thermal_paste = new ItemProcessable(CraftingMaterials.THERMAL_PASTE);
	public static final Item dye_crude = new ItemProcessable(CraftingMaterials.CRUDE_DYE);
	public static final Item silicon = new ItemProcessable(CraftingMaterials.SILICON);
	public static final Item pcb_crude = new ItemProcessable(CraftingMaterials.CRUDE_PCB);
	public static final Item contacts_crude = new ItemProcessable(CraftingMaterials.CRUDE_CONTACTS);
	public static final Item circuit_crude = new ItemProcessable(CraftingMaterials.CRUDE_CIRCUIT);
	public static final Item etching_laser = new ItemProcessable(CraftingMaterials.ETCHING_LASER);
	public static final Item casing_machine_basic = new ItemProcessable(CraftingMaterials.BASIC_MACHINE_CASING);
	public static final Item casing_machine_magical = new ItemProcessable(CraftingMaterials.MAGICAL_MACHINE_CASING);
	public static final Item item_input = new ItemProcessable(CraftingMaterials.ITEM_INPUT);
	public static final Item item_output = new ItemProcessable(CraftingMaterials.ITEM_OUTPUT);
	public static final Item gear_crude = new ItemProcessable(CraftingMaterials.CRUDE_GEAR);
	
	public static final Item saw = new ToolSaw("saw", ItemGroups.items);
	
	// Block Items
	
	// Metal Storage Blocks
	public static Item block_storage_copper;
	public static Item block_storage_tin;
	public static Item block_storage_steel;
	public static Item block_storage_aluminium;
	public static Item block_storage_brass;
	public static Item block_storage_silver;
	public static Item block_storage_bronze;
	public static Item block_storage_zinc;
	public static Item block_storage_lead;
	public static Item block_storage_fluxate;
	
	// Mob Infused Metal Storage Blocks
	public static Item block_storage_infused_witch;
	
	// Machine Blocks
	public static Item block_machine_threefold_combiner;
	
	@SubscribeEvent
	public static void OnRegisterItems (RegistryEvent.Register<Item> e) {
		
		e.getRegistry().registerAll(ModObjects.ITEMS.toArray(new Item[0]));
		
	}
	
}