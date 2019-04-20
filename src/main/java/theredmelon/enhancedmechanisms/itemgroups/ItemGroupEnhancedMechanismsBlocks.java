package theredmelon.enhancedmechanisms.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import theredmelon.enhancedmechanisms.objects.ModBlocks;
import theredmelon.enhancedmechanisms.utils.Reference;

public class ItemGroupEnhancedMechanismsBlocks extends ItemGroup {

	public ItemGroupEnhancedMechanismsBlocks() {
		
		super(Reference.MOD_ID + "_blocks");

	}

	@Override
	public ItemStack createIcon() {

		return new ItemStack(ModBlocks.BLOCK_STORAGE_COPPER);
		
	}

}
