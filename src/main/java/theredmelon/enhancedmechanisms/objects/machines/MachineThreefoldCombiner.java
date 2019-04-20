package theredmelon.enhancedmechanisms.objects.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;

public class MachineThreefoldCombiner extends MachineBase {

	public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BlockRedstoneTorch.LIT;
	
	public MachineThreefoldCombiner (String name, Material material, float hardness, float resistance, ItemGroup group) {
		
		super(name, material, hardness, resistance, group);
		setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(LIT, false));
		
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		if (!worldIn.isRemote) {
			
			TileEntity tEntity = worldIn.getTileEntity(pos);
			NetworkHooks.openGui((EntityPlayerMP) player, (TileEntityThreefoldCombiner) tEntity, pos);
			
		}
		
		return true;
		
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {

		return new TileEntityThreefoldCombiner();
		
	}
	
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {

		return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		if (stack.hasDisplayName()) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			
			if (tile instanceof TileEntityThreefoldCombiner) {
				
				((TileEntityThreefoldCombiner) tile).setCustomName(stack.getDisplayName());
				
			}
			
		}
		
	}
	
	@Override
	public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {

		if (state.getBlock() != newState.getBlock()) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			
			if (tile instanceof TileEntityThreefoldCombiner) {
				
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityThreefoldCombiner) tile);
				worldIn.updateComparatorOutputLevel(pos, this);
				
			}
			
			super.onReplaced(state, worldIn, pos, newState, isMoving);
			
		}
		
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {

		return true;
		
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {

		TileEntity tile = worldIn.getTileEntity(pos);
		
		if (tile instanceof IInventory) {
			
			int i = 0;
			float f = 0.0f;
			
			ItemStack stack = ((IInventory) tile).getStackInSlot(3);
			
			if (!stack.isEmpty()) {
				
				f += (float) stack.getCount() / (float) Math.min(((IInventory) tile).getInventoryStackLimit(), stack.getMaxStackSize());
				++i;
				
			}
			
			f = f / (float) ((IInventory) tile).getSizeInventory();
			
			if (i > 0) {
				
				return (int) (Math.floor(f * 14.0f)) + 1;
				
			}
			
			return (int) (Math.floor(f * 14.0f));
			
		}
		
		return Container.calcRedstone(tile);
		
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {

		return EnumBlockRenderType.MODEL;
		
	}
	
	@Override
	public IBlockState rotate(IBlockState state, Rotation rot) {

		return state.with(FACING, rot.rotate(state.get(FACING)));
		
	}
	
	@Deprecated
	@Override
	public IBlockState mirror(IBlockState state, Mirror mirrorIn) {

		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
		
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder) {

		builder.add(FACING, LIT);
		
	}
	
}
