package theredmelon.enhancedmechanisms.objects.tools;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import theredmelon.enhancedmechanisms.objects.ModItems;
import theredmelon.enhancedmechanisms.objects.items.ItemBase;

public class ToolSaw extends ItemBase {

	public ToolSaw(String name, ItemGroup group) {
		
		super(new Properties().maxStackSize(1).defaultMaxDamage(11).containerItem(ModItems.saw).setNoRepair().group(group), name);
		
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		
		if (itemStack.getDamage() == 0) {
			
			ItemStack stack = itemStack.copy();
			
			stack.setDamage(1);
			return stack;
			
		} else if (itemStack.getDamage() == itemStack.getMaxDamage()) {
			
			return ItemStack.EMPTY;
			
		} else {
			
			ItemStack stack = itemStack.copy();
			
			stack.setDamage(itemStack.getDamage() + 1);
			return stack;
			
		}
		
	}
	
}
