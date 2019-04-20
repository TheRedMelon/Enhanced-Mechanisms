package theredmelon.enhancedmechanisms.objects;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.IStringSerializable;
import theredmelon.enhancedmechanisms.itemgroups.ItemGroups;

public class ObjectEnums {

	public static enum Ingots implements IStringSerializable, IProcessableItemEnum {
		COPPER("ingot_copper", ItemGroups.items, 200),
		TIN("ingot_tin", ItemGroups.items, 200),
		STEEL("ingot_steel", ItemGroups.items, 200),
		ALUMINIUM("ingot_aluminium", ItemGroups.items, 200),
		BRASS("ingot_brass", ItemGroups.items, 200),
		SILVER("ingot_silver", ItemGroups.items, 200),
		BRONZE("ingot_bronze", ItemGroups.items, 200),
		ZINC("ingot_zinc", ItemGroups.items, 200),
		LEAD("ingot_lead", ItemGroups.items, 200),
		FLUXATE("ingot_fluxate", ItemGroups.items, 200),
		INFUSED_WITCH("ingot_infused_witch", ItemGroups.items, 200);
		
		private String Name;
		private ItemGroup Group;
		private int ProcessTime;
		
		private Ingots(String name, ItemGroup group, int processTime) {
			
			Name = name;
			Group = group;
			ProcessTime = processTime;
			
		}

		@Override
		public ItemGroup getGroup() {

			return Group;
			
		}

		@Override
		public int getProcessTime() {

			return ProcessTime;
			
		}

		@Override
		public String getName() {

			return Name;
			
		}
		
		@Override
		public String toString() {

			return getName();
			
		}
		
	}
	
	public static enum CraftingMaterials implements IStringSerializable, IProcessableItemEnum {
		ASSEMBLY_FRAGMENT("assembly_fragment", ItemGroups.items, 200),
		POWDER_CHARCOAL("dust_charcoal", ItemGroups.items, 200),
		CRUDE_PROCESSOR("processor_crude", ItemGroups.items, 200),
		CRUDE_HEATSINK("heatsink_crude", ItemGroups.items, 200),
		THERMAL_PASTE("thermal_paste", ItemGroups.items, 200),
		CRUDE_DYE("dye_crude", ItemGroups.items, 200),
		SILICON("silicon", ItemGroups.items, 200),
		CRUDE_PCB("pcb_crude", ItemGroups.items, 200),
		CRUDE_CONTACTS("contacts_crude", ItemGroups.items, 200),
		CRUDE_CIRCUIT("circuit_crude", ItemGroups.items, 200),
		ETCHING_LASER("etching_laser", ItemGroups.items, 200),
		BASIC_MACHINE_CASING("casing_machine_basic", ItemGroups.items, 200),
		MAGICAL_MACHINE_CASING("casing_machine_magical", ItemGroups.items, 200),
		ITEM_INPUT("item_input", ItemGroups.items, 200),
		ITEM_OUTPUT("item_output", ItemGroups.items, 200),
		CRUDE_GEAR("gear_crude", ItemGroups.items, 200);

		private String Name;
		private ItemGroup Group;
		private int ProcessTime;
		
		private CraftingMaterials(String name, ItemGroup group, int processTime) {
			
			Name = name;
			Group = group;
			ProcessTime = processTime;
			
		}

		@Override
		public ItemGroup getGroup() {

			return Group;
			
		}

		@Override
		public int getProcessTime() {

			return ProcessTime;
			
		}

		@Override
		public String getName() {

			return Name;
			
		}
		
		@Override
		public String toString() {

			return getName();
			
		}
		
	}
	
	public static enum MetalBlocks implements IStringSerializable, IProcessableBlockEnum {
		COPPER("block_storage_copper", Material.IRON, 4f, 30f, ItemGroups.blocks, 200),
		TIN("block_storage_tin", Material.IRON, 1.5f, 15f, ItemGroups.blocks, 200),
		STEEL("block_storage_steel", Material.IRON, 6.5f, 32f, ItemGroups.blocks, 200),
		ALUMINIUM("block_storage_aluminium", Material.IRON, 1.9f, 20f, ItemGroups.blocks, 200),
		BRASS("block_storage_brass", Material.IRON, 5.9f, 32f, ItemGroups.blocks, 200),
		SILVER("block_storage_silver", Material.IRON, 3.7f, 30f, ItemGroups.blocks, 200),
		BRONZE("block_storage_bronze", Material.IRON, 6.5f, 33f, ItemGroups.blocks, 200),
		ZINC("block_storage_zinc", Material.IRON, 1.7f, 17f, ItemGroups.blocks, 200),
		LEAD("block_storage_lead", Material.IRON, 4f, 30f, ItemGroups.blocks, 200),
		FLUXATE("block_storage_fluxate", Material.IRON, 6.6f, 34f, ItemGroups.blocks, 200),
		INFUSED_WITCH("block_storage_infused_witch", Material.IRON, 6.8f, 35f, ItemGroups.blocks, 200);

		private String Name;
		private Material Mat;
		private float Hardness;
		private float Resistance;
		private ItemGroup Group;
		private int ProcessTime;
		
		private MetalBlocks(String name, Material material, float hardness, float resistance, ItemGroup group, int processTime) {
			
			Name = name;
			Mat = material;
			Hardness = hardness;
			Resistance = resistance;
			Group = group;
			ProcessTime = processTime;
			
		}
		
		@Override
		public Material getMaterial() {

			return Mat;

		}

		@Override
		public float getHardness() {

			return Hardness;
			
		}

		@Override
		public float getResistance() {

			return Resistance;
			
		}

		@Override
		public ItemGroup getGroup() {

			return Group;
			
		}

		@Override
		public int getProcessTime() {

			return ProcessTime;
			
		}

		@Override
		public String getName() {

			return Name;
			
		}
		
		@Override
		public String toString() {
			
			return getName();
			
		}
		
	}
	
	public interface IProcessableItemEnum {

		public String getName();
		public ItemGroup getGroup();
		public int getProcessTime();
		
	}
	
	public interface IProcessableBlockEnum {

		public String getName();
		public Material getMaterial();
		public float getHardness();
		public float getResistance();
		public ItemGroup getGroup();
		public int getProcessTime();
		
	}
	
}
