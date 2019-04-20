package theredmelon.enhancedmechanisms.objects.machines.slots;

import java.util.Map.Entry;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import theredmelon.enhancedmechanisms.recipes.ThreefoldCombinerRecipe;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;

public class SlotMachineOutput extends Slot {

	private int removeCount;
	private final EntityPlayer player;
	
	public SlotMachineOutput(EntityPlayer player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		
		super(inventoryIn, index, xPosition, yPosition);
		
		this.player = player;
		
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		
		return false;
		
	}
	
	@Override
	public ItemStack decrStackSize(int amount) {
		
		if (getHasStack()) {
			
			removeCount += Math.min(amount, getStack().getCount());
			
		}
		
		return super.decrStackSize(amount);
		
	}
	
	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {

		onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
		
	}
	
	@Override
	protected void onCrafting(ItemStack stack, int amount) {

		removeCount += amount;
		onCrafting(stack);
		
	}
	
	@Override
	protected void onCrafting(ItemStack stack) {

		stack.onCrafting(player.world, player, removeCount);
		
		if (!player.world.isRemote) {
			
			for (Entry<ResourceLocation, Integer> entry : ((TileEntityThreefoldCombiner) inventory).getRecipeUseCounts().entrySet()) {
				
				ThreefoldCombinerRecipe machineRecipe = (ThreefoldCombinerRecipe) player.world.getRecipeManager().getRecipe(entry.getKey());
				float f;
				
				if (machineRecipe != null) {
					
					f = machineRecipe.getExperience();
					
				} else {
					
					f = 0.0f;
					
				}
				
				int i = entry.getValue();
				
				if (f == 0.0f) {
					
					i = 0;
					
				} else if (f < 1.0f) {
					
					int j = MathHelper.floor((float) i * f);
					
					if (j < MathHelper.ceil((float) i * f) && Math.random() < (double) ((float) i * f - (float) j)) {
						
						++j;
						
					}
					
					i = j;
					
				}
				
				while (i > 0) {
					
					int k = EntityXPOrb.getXPSplit(i);
					i -= k;
					player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5d, player.posZ, k));
					
				}
				
			}
			
			((IRecipeHolder) inventory).onCrafting(player);
			
		}
		
		removeCount = 0;
		
	}
	
}
