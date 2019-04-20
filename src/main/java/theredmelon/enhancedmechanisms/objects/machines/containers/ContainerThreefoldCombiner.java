package theredmelon.enhancedmechanisms.objects.machines.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import theredmelon.enhancedmechanisms.objects.machines.slots.SlotMachineInput;
import theredmelon.enhancedmechanisms.objects.machines.slots.SlotMachineOutput;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;

public class ContainerThreefoldCombiner extends Container {

	private final IInventory tileThreefoldCombiner;
	private int processTime;
	private int totalProcessTime;

	public ContainerThreefoldCombiner(InventoryPlayer playerInventory, IInventory machineInventory) {

		tileThreefoldCombiner = machineInventory;
		addSlot(new SlotMachineInput(machineInventory, 0, 40, 17, TileEntityThreefoldCombiner.class));
		addSlot(new SlotMachineInput(machineInventory, 1, 40, 35, TileEntityThreefoldCombiner.class));
		addSlot(new SlotMachineInput(machineInventory, 2, 40, 53, TileEntityThreefoldCombiner.class));
		addSlot(new SlotMachineOutput(playerInventory.player, machineInventory, 3, 116, 35));
		
		for(int i = 0; i < 3; ++i) {
	  
	      	for(int j = 0; j < 9; ++j) {
	    	  
	    	  addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	         
	      	}
	      
	   	}

	   	for(int k = 0; k < 9; ++k) {
	   		
	      addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
	      
	   	}
		
	}
	
	@Override
	public void addListener(IContainerListener listener) {

		super.addListener(listener);
		
		listener.sendAllWindowProperties(this, tileThreefoldCombiner);
		
	}
	
	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();
		
		for (IContainerListener icontainerlistener : listeners) {
			
			if (processTime != tileThreefoldCombiner.getField(0)) {
				
				icontainerlistener.sendWindowProperty(this, 0, tileThreefoldCombiner.getField(0));
				
			}
			
			if (totalProcessTime != tileThreefoldCombiner.getField(1)) {
				
				icontainerlistener.sendWindowProperty(this, 1, tileThreefoldCombiner.getField(1));
				
			}
			
		}
		
		processTime = tileThreefoldCombiner.getField(0);
		totalProcessTime = tileThreefoldCombiner.getField(1);
		
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(int id, int data) {

		tileThreefoldCombiner.setField(id, data);
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return tileThreefoldCombiner.isUsableByPlayer(playerIn);
		
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack()) {
			
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if (index == 3) {
				
				if (!mergeItemStack(stack1, 4, 40, true)) {
					
					return ItemStack.EMPTY;
					
				}
				
				slot.onSlotChange(stack1, stack);
				
			} else if (index > 3) {
				
				boolean moved = false;
				
				if (tileThreefoldCombiner.isItemValidForSlot(0, stack1)) {
					
					moved = true;
					
					if (!mergeItemStack(stack1, 0, 1, false)) {
						
						return ItemStack.EMPTY;
						
					}
					
				}
				
				if (tileThreefoldCombiner.isItemValidForSlot(1, stack1)) {
					
					moved = true;
					
					if (!mergeItemStack(stack1, 1, 2, false)) {
						
						return ItemStack.EMPTY;
						
					}
					
				}
				
				if (tileThreefoldCombiner.isItemValidForSlot(2, stack1)) {
					
					moved = true;
					
					if (!mergeItemStack(stack1, 2, 3, false)) {
						
						return ItemStack.EMPTY;
						
					}
					
				}
				
				if (!moved && index >= 4 && index < 31) {
					
					if (!mergeItemStack(stack1, 31, 40, false)) {
						
						return ItemStack.EMPTY;
						
					}
					
				} else if (!moved && index >= 31 && index < 40 && !mergeItemStack(stack1, 4, 31, false)) {
					
					return ItemStack.EMPTY;
					
				}
				
			} else if (!mergeItemStack(stack1, 4, 40, false)) {
				
				return ItemStack.EMPTY;
				
			}
			
			if (stack1.isEmpty()) {
				
				slot.putStack(ItemStack.EMPTY);
				
			} else {
				
				slot.onSlotChanged();
				
			}
			
			
			if (stack1.getCount() == stack.getCount()) {
				
				return ItemStack.EMPTY;
				
			}
			
			slot.onTake(playerIn, stack1);
			
		}
		
		return stack;
		
	}
	
}
