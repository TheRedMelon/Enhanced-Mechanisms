package theredmelon.enhancedmechanisms.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import theredmelon.enhancedmechanisms.objects.ModItems;
import theredmelon.enhancedmechanisms.utils.Reference;

public class ItemGroupEnhancedMechanismsItems extends ItemGroup {

	public ItemGroupEnhancedMechanismsItems() {
		
		super(Reference.MOD_ID + "_items");

	}

	@Override
	public ItemStack createIcon() {

		return new ItemStack(ModItems.saw);
		
	}

}
