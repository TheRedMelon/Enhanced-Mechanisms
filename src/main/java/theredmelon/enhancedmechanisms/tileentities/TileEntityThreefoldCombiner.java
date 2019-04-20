package theredmelon.enhancedmechanisms.tileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import theredmelon.enhancedmechanisms.objects.ModBlocks;
import theredmelon.enhancedmechanisms.objects.machines.MachineThreefoldCombiner;
import theredmelon.enhancedmechanisms.objects.machines.containers.ContainerThreefoldCombiner;
import theredmelon.enhancedmechanisms.recipes.MachineRecipeTypes;
import theredmelon.enhancedmechanisms.utils.Reference;

public class TileEntityThreefoldCombiner extends TileEntityLockable implements ISidedInventory, IRecipeHolder, ITickable {

	private NonNullList<ItemStack> ItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
	
	private static final int[] SLOTS_TOP = new int[] {0, 1, 2};
	private static final int[] SLOTS_BOTTOM = new int[] {3};
	
	private int processTime;
	private int totalProcessTime;
	
	private ITextComponent CustomName;
	
	private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();
	
	public TileEntityThreefoldCombiner() {
		
		super(ModBlocks.tileEntityThreefoldCombiner);
		
	}

	@Override
	public int getSizeInventory() {

		return ItemStacks.size();
		
	}

	@Override
	public boolean isEmpty() {

		for (ItemStack stack : ItemStacks) {
			
			if (!stack.isEmpty()) {
				
				return false;
				
			}
			
		}
		
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return ItemStacks.get(index);
		
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

		return ItemStackHelper.getAndSplit(ItemStacks, index, count);
		
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(ItemStacks, index);
		
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		ItemStack iStack = ItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(iStack) && ItemStack.areItemsEqual(stack, iStack);
		
		if (stack.getCount() > getInventoryStackLimit()) {
			
			stack.setCount(getInventoryStackLimit());
			
		}
		
		if (index < 3 && !flag) {
			
			totalProcessTime = getProcessTime();
			processTime = 0;
			markDirty();
			
		}
		
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		if (world.getTileEntity(pos) != this) {
			
			return false;
			
		} else {
			
			return !(player.getDistanceSq((double) pos.getX() + 0.5d, (double) pos.getX() + 0.5d, (double) pos.getZ() + 0.5d) > 64.0d);
			
		}
		
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		if (index == 3) {
			
			return false;
			
		}
		
		boolean emptyInputs = true;
		
		for (ItemStack stack1 : ItemStacks.subList(0, 3)) {
			
			if (!stack1.isEmpty()) {
				
				emptyInputs = false;
				break;
				
			}
			
		}
		
		if (emptyInputs) {
			
			for (IRecipe recipe : world.getRecipeManager().getRecipes(MachineRecipeTypes.THREEFOLD_COMBINER)) {
				
				if (recipe.getIngredients().get(index).test(stack)) {
					
					return true;
					
				}
				
			}
			
		} else {
			
			for (IRecipe recipe : world.getRecipeManager().getRecipes(MachineRecipeTypes.THREEFOLD_COMBINER)) {
				
				boolean works = true;
				List<ItemStack> added = new ArrayList<ItemStack>(ItemStacks);
				
				added.set(index, stack);
				
				for (int i = 0; i < 3; i++) {
					
					if (added.get(i).isEmpty()) {
						
						continue;
						
					} else {
						
						if (!recipe.getIngredients().get(i).test(added.get(i))) {
							
							works = false;
							break;
							
						}
						
					}
					
				}
				
				if (!works) {
					
					continue;
					
				}
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

	@Override
	public int getField(int id) {

		switch (id) {
		
			case 0:
			
				return processTime;
				
			case 1:
				
				return totalProcessTime;
				
			default:
				
				return 0;
		
		}
		
	}

	@Override
	public void setField(int id, int value) {

		switch (id) {
		
			case 0:
				
				processTime = value;
				
			case 1:
				
				totalProcessTime = value;
		
		}
		
	}

	@Override
	public int getFieldCount() {

		return 2;
		
	}

	@Override
	public void clear() {

		ItemStacks.clear();
		
	}

	@Override
	public ITextComponent getName() {

		return (ITextComponent) (CustomName != null ? CustomName : new TextComponentTranslation("container.threefold_combiner"));
		
	}

	@Override
	public boolean hasCustomName() {

		return CustomName != null;
		
	}

	@Override
	public ITextComponent getCustomName() {

		return CustomName;
		
	}

	public void setCustomName(@Nullable ITextComponent customName) {
		
		CustomName = customName;
		
	}
	
	@Override
	public void read(NBTTagCompound compound) {

		super.read(compound);
		
		ItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		
		ItemStackHelper.loadAllItems(compound, ItemStacks);
		
		processTime = compound.getInt("ProcessTime");
		totalProcessTime = compound.getInt("ProcessTimeTotal");
		
		if (compound.contains("CustomName", 8)) {
			
			CustomName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
			
		}
		
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		
		super.write(compound);
		
		compound.setInt("ProcessTime", processTime);
		compound.setInt("ProcessTimeTotal", totalProcessTime);
		ItemStackHelper.saveAllItems(compound, ItemStacks);

		if (CustomName != null) {
			
			compound.setString("CustomName", ITextComponent.Serializer.toJson(CustomName));
			
		}
		
		return compound;
		
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {

		return new ContainerThreefoldCombiner(playerInventory, this);
		
	}

	@Override
	public String getGuiID() {

		return Reference.MOD_ID + ":threefold_combiner";
		
	}
	
	private boolean isProcessing() {
		
		return processTime > 0;
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public static boolean isProcessing(IInventory inv) {
		
		return inv.getField(0) > 0;
		
	}

	@Override
	public void tick() {

		boolean flag = isProcessing();
		boolean flag1 = false;
		
		if (!world.isRemote) {
			
			if (isProcessing() || (!ItemStacks.get(0).isEmpty() && !ItemStacks.get(1).isEmpty() && !ItemStacks.get(2).isEmpty())) {
				
				IRecipe irecipe = world.getRecipeManager().getRecipe(this, world, MachineRecipeTypes.THREEFOLD_COMBINER);
				
				if (isProcessing() && canProcess(irecipe)) {
					
					++processTime;
					
					if (processTime == totalProcessTime) {
						
						processTime = 0;
						totalProcessTime = getProcessTime();
						processItem(irecipe);
						flag1 = true;
						
					}
					
				} else {
					
					processTime = 0;
					
				}
				
			} else if (!isProcessing() && processTime > 0) {
				
				processTime = MathHelper.clamp(processTime - 2, 0, totalProcessTime);
				
			}
			
			if (flag != isProcessing()) {
				
				flag1 = true;
				world.setBlockState(pos, world.getBlockState(pos).with(MachineThreefoldCombiner.LIT, isProcessing()), 3);
				
			}
			
		}
		
		if (flag1) {
			
			markDirty();
			
		}
		
	}
	
	private void processItem(@Nullable IRecipe recipe) {
		
		if (recipe != null && canProcess(recipe)) {
			
			ItemStack stacki1 = ItemStacks.get(0);
			ItemStack stacki2 = ItemStacks.get(1);
			ItemStack stacki3 = ItemStacks.get(2);
			ItemStack stack1 = recipe.getRecipeOutput();
			ItemStack stack2 = ItemStacks.get(3);
			
			if (stack2.isEmpty()) {
				
				ItemStacks.set(3, stack1.copy());
				
			} else if (stack2.getItem() == stack1.getItem()) {
				
				stack2.grow(stack1.getCount());
				
			}
			
			if (!world.isRemote) {
				
				canUseRecipe(world, (EntityPlayerMP) null, recipe);
				
			}
			
			stacki1.shrink(1);
			stacki2.shrink(1);
			stacki3.shrink(1);
			
		}
		
	}
	
	private int getProcessTime() {
		
		return 200;
		
	}
	
	@Override
	public boolean canUseRecipe(World worldIn, EntityPlayerMP player, IRecipe recipe) {

		if (recipe != null) {
			
			setRecipeUsed(recipe);
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	@Override
	public void onCrafting(EntityPlayer player) {

		if (!world.getGameRules().getBoolean("doLimitedCrafting")) {
			
			List<IRecipe> list = Lists.newArrayList();
			
			for (ResourceLocation resourcelocation : recipeUseCounts.keySet()) {
				
				IRecipe irecipe = player.world.getRecipeManager().getRecipe(resourcelocation);
				
				if (irecipe != null) {
					
					list.add(irecipe);
					
				}
				
			}
			
			player.unlockRecipes(list);
			
		}
		
		recipeUseCounts.clear();
		
	}

	private boolean canProcess(@Nullable IRecipe recipe) {
		
		if (!ItemStacks.get(0).isEmpty() && !ItemStacks.get(1).isEmpty() && !ItemStacks.get(2).isEmpty() && recipe != null) {
			
			ItemStack stack = recipe.getRecipeOutput();
			
			if (stack.isEmpty()) {
				
				return false;
				
			} else {
				
				ItemStack stack1 = ItemStacks.get(3);
				
				if (stack1.isEmpty()) {
					
					return true;
					
				} else if (!stack1.isItemEqual(stack)) {
					
					return false;
					
				} else if (stack1.getCount() + stack.getCount() <= getInventoryStackLimit() && stack1.getCount() < stack1.getMaxStackSize()) {
					
					return true;
					
				} else {
					
					return stack1.getCount() + stack.getCount() <= stack.getMaxStackSize();
					
				}
				
			}
			
		} else {
			
			return false;
			
		}
		
	}

	@Override
	public void setRecipeUsed(IRecipe recipe) {

		if (recipeUseCounts.containsKey(recipe.getId())) {
			
			recipeUseCounts.put(recipe.getId(), recipeUseCounts.get(recipe.getId()));
			
		} else {
			
			recipeUseCounts.put(recipe.getId(), 1);
			
		}
		
	}

	@Override
	public IRecipe getRecipeUsed() {

		return null;
		
	}
	
	public Map<ResourceLocation, Integer> getRecipeUseCounts() {
		
		return recipeUseCounts;
		
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		
		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : SLOTS_TOP;
		
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

		return isItemValidForSlot(index, itemStackIn);
		
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		
		return index == 3;
		
	}
	
	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, EnumFacing side) {

		if (!removed && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			
			if (side == EnumFacing.UP) {
				
				return handlers[0].cast();
				
			} else if (side == EnumFacing.DOWN) {
				
				return handlers[1].cast();
				
			} else {
				
				return handlers[2].cast();
				
			}
			
		}
		
		return super.getCapability(cap, side);
		
	}
	
	@Override
	public void remove() {

		super.remove();
		
		for (int x = 0; x < handlers.length; x++) {
			
			handlers[x].invalidate();
			
		}
		
	}
	
}
