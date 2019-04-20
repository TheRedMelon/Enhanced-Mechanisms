package theredmelon.enhancedmechanisms.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import theredmelon.enhancedmechanisms.objects.ObjectEnums.IProcessableBlockEnum;
import theredmelon.enhancedmechanisms.utils.interfaces.IProcessTime;

public class BlockProcessable extends BlockBase implements IProcessTime {

	private final int ProcessTime;
	
	public BlockProcessable(String name, Material material, float hardness, float resistance, ItemGroup group, int processTime) {
		
		super(name, material, hardness, resistance, group);
		ProcessTime = processTime;

	}
	
	public BlockProcessable(IProcessableBlockEnum processableBlock) {

		this(processableBlock.getName(), processableBlock.getMaterial(), processableBlock.getHardness(), processableBlock.getResistance(), processableBlock.getGroup(), processableBlock.getProcessTime());
		
	}

	@Override
	public int getProcessTime(ItemStack stack) {

		return ProcessTime;
		
	}

}
