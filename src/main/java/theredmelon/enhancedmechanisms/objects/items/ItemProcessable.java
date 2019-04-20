package theredmelon.enhancedmechanisms.objects.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import theredmelon.enhancedmechanisms.objects.ObjectEnums.IProcessableItemEnum;
import theredmelon.enhancedmechanisms.utils.interfaces.IProcessTime;

public class ItemProcessable extends ItemBase implements IProcessTime {

	private final int ProcessTime;
	
	private ItemProcessable(String name, ItemGroup group, int processTime) {
		
		super(name, group);
		ProcessTime = processTime;
		
	}

	public ItemProcessable(IProcessableItemEnum processableItem) {

		this(processableItem.getName(), processableItem.getGroup(), processableItem.getProcessTime());
		
	}

	@Override
	public int getProcessTime(ItemStack stack) {

		return ProcessTime;
		
	}

}
