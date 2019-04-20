package theredmelon.enhancedmechanisms.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import theredmelon.enhancedmechanisms.objects.ModItems;
import theredmelon.enhancedmechanisms.utils.Reference;

public class BlockBase extends Block {

	public BlockBase(Properties properties, String name, ItemGroup group) {
		
		super(properties);
		setRegistryName(Reference.MOD_ID, name);
		
		try {
			
			ModItems.class.getField(name).set(null, new ItemBlock(this, new Item.Properties().group(group)).setRegistryName(Reference.MOD_ID, name));
			ObjectList.getItemList().add((Item) ModItems.class.getField(name).get(null));
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			
			e.printStackTrace();
			
		}
		
		ObjectList.getBlocksList().add(this);
		
	}
	
	public BlockBase(String name, Material material, ItemGroup group) {
		
		this(Block.Properties.create(material), name, group);
		
	}
	
	public BlockBase(String name, Material material, float hardness, float resistance, ItemGroup group) {
		
		this(Block.Properties.create(material).hardnessAndResistance(hardness, resistance), name, group);
		
	}
	
}
