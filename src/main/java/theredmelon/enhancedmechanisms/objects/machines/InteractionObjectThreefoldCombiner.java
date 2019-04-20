package theredmelon.enhancedmechanisms.objects.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import theredmelon.enhancedmechanisms.objects.machines.containers.ContainerThreefoldCombiner;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;
import theredmelon.enhancedmechanisms.utils.Reference;

public class InteractionObjectThreefoldCombiner implements IInteractionObject {

	private TileEntityThreefoldCombiner te;
	
	public InteractionObjectThreefoldCombiner(TileEntityThreefoldCombiner te) {

		this.te = te;
		
	}
	
	@Override
	public ITextComponent getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITextComponent getCustomName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {

		return new ContainerThreefoldCombiner(playerInventory, te);
		
	}

	@Override
	public String getGuiID() {

		return Reference.MOD_ID + ":threefold_combiner";
		
	}

}
