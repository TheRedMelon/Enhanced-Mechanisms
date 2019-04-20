package theredmelon.enhancedmechanisms.objects.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import theredmelon.enhancedmechanisms.utils.Reference;

public class ItemBase extends Item {
	
	public ItemBase(Properties properties, String name) {

		super(properties);
		setRegistryName(Reference.MOD_ID, name);
		
		ItemsList.getItemsList().add(this);
		
	}
	
	public ItemBase(String name) {
		
		this(new Properties(), name);
		
	}
	
	public ItemBase(String name, ItemGroup group) {
		
		this(new Properties().group(group), name);
		
	}
	
}
