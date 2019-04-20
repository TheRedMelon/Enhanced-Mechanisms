package theredmelon.enhancedmechanisms.objects.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import theredmelon.enhancedmechanisms.objects.ModObjects;

public class ObjectList extends ModObjects {

	static List<Block> getBlocksList() {
		
		return ModObjects.BLOCKS;
		
	}
	
	static List<Item> getItemList() {
		
		return ModObjects.ITEMS;
		
	}
	
}
