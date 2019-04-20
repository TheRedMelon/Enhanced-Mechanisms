package theredmelon.enhancedmechanisms.objects.machines.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMachineInput extends Slot {

	private int index;
	private IInventory inventory;
	private Class<? extends IInventory> machine;
	
	public SlotMachineInput(IInventory inventoryIn, int index, int xPosition, int yPosition, Class<? extends IInventory> machine) {
		
		super(inventoryIn, index, xPosition, yPosition);
		
		this.index = index;
		inventory = inventoryIn;
		this.machine = machine;
		
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		
		return (machine.cast(inventory)).isItemValidForSlot(index, stack);
		
	}
	
}
