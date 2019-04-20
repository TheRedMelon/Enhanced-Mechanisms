package theredmelon.enhancedmechanisms.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.crafting.RecipeType;

public class ThreefoldCombinerRecipe implements IRecipe, IShapedRecipe {

	static int MAX_HEIGHT = 3;
	static int MAX_WIDTH = 1;
	
	private final int recipeHeight;
	private final int recipeWidth;
	private final ResourceLocation id;
	private final String group;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final float experience;
	private final int processingTime;
	
	@Override
	public RecipeType<? extends IRecipe> getType() {

		return MachineRecipeTypes.THREEFOLD_COMBINER;
		
	}
	
	public ThreefoldCombinerRecipe(ResourceLocation id, int recipeHeight, int recipeWidth, String group, NonNullList<Ingredient> inputs, ItemStack output, float experience, int processingTime) {
		
		this.id = id;
		this.group = group;
		this.output = output;
		this.experience = experience;
		this.processingTime = processingTime;
		this.recipeHeight = recipeHeight;
		this.recipeWidth = recipeWidth;
		this.inputs = inputs;
		
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {

		for (int i = 0; i <= inv.getWidth() - recipeWidth; ++i) {
			
			for (int j = 0; j <= inv.getHeight() - recipeHeight; ++j) {
				
				if (checkMatch(inv, i, j, true)) {
					
					return true;
					
				}
				
				if (checkMatch(inv, i, j, false)) {
					
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}

	private boolean checkMatch (IInventory craftingInventory, int width, int height, boolean flag) {
		
		for (int i = 0; i < craftingInventory.getWidth(); ++i) {
			
			for (int j = 0; j < craftingInventory.getHeight(); ++j) {
				
				int k = i - width;
				int l = j - height;
				Ingredient ingredient = Ingredient.EMPTY;
				
				if (k >= 0 && l >= 0 && k < recipeWidth && l < recipeHeight) {
					
					if (flag) {
						
						ingredient = inputs.get(recipeWidth - k - 1 + l * recipeWidth);
						
					} else {
						
						ingredient = inputs.get(k + l * recipeWidth);
						
					}
					
				}
				
				if (!ingredient.test(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()))) {
					
					return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {

		return getRecipeOutput().copy();
		
	}


	@Override
	public boolean canFit(int width, int height) {

		return width == recipeWidth && height >= recipeHeight;
		
	}


	@Override
	public ItemStack getRecipeOutput() {

		return output;
		
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {

		return inputs;
		
	}
	
	public float getExperience() {
		
		return experience;
		
	}

	@Override
	public ResourceLocation getId() {

		return id;
		
	}

	public int getProcessingTime() {
		
		return processingTime;
		
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {

		return MachineRecipeSerializers.THREEFOLD_COMBINER;
		
	}
	
	@Override
	public String getGroup() {

		return group;
		
	}

	@Override
	public int getRecipeWidth() {

		return getWidth();
		
	}

	@Override
	public int getRecipeHeight() {

		return getHeight();
		
	}
	
	public int getWidth() {
		
		return recipeWidth;
		
	}
	
	public int getHeight() {
		
		return recipeHeight;
		
	}
	
	private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
		
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(keys.keySet());
		
		set.remove(" ");
		
		for (int i = 0; i < pattern.length; ++i) {
			
			for (int j = 0; j < pattern[i].length(); ++j) {
				
				String s = pattern[i].substring(j, j + 1);
				Ingredient ingredient = keys.get(s);
				
				if (ingredient == null) {
					
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
					
				}
				
				set.remove(s);
				nonnulllist.set(j + patternWidth * i, ingredient);
				
			}
			
		}
		
		if (!set.isEmpty()) {
			
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
			
		} else {
			
			return nonnulllist;
			
		}
		
	}
	
	@VisibleForTesting
	static String[] shrink(String... toShrink) {
		
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		
		for (int i1 = 0; i1 < toShrink.length; ++i1) {
			
			String s = toShrink[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			
			if (j1 < 0) {
				
				if (k == i1) {
					
					++k;
					
				}
				
				++l;
				
			} else {
				
				l = 0;
				
			}
			
		}
		
		if (toShrink.length == 1) {
			
			return new String[0];
			
		} else {
			
			String[] astring = new String[toShrink.length - l - k];
			
			for (int k1 = 0; k1 < astring.length; ++k1) {
				
				astring[k1] = toShrink[k1 + k].substring(i, j + 1);
				
			}
			
			return astring;
			
		}
		
	}
	
	private static int firstNonSpace(String str) {
		
		int i;
		
		for (i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
			;
		}
		
		return i;
		
	}
	
	private static int lastNonSpace(String str) {
		
		int i;
		
		for (i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
			
			;
			
		}
		
		return i;
		
	}
	
	private static String[] patternFromJson(JsonArray jsonArr) {
		
		String[] astring = new String[jsonArr.size()];
		
		if (astring.length > MAX_HEIGHT) {
			
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
			
		} else if (astring.length == 0) {
			
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
			
		} else {
			
			for (int i = 0; i < astring.length; ++i) {
				
				String s = JsonUtils.getString(jsonArr.get(i), "pattern[" + i + "]");
				
				if (s.length() > MAX_WIDTH) {
					
					throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
					
				}
				
				if (i > 0 && astring[0].length() != s.length()) {
					
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
					
				}
				
				astring[i] = s;
				
			}
			
			return astring;
			
		}
		
	}
	
	private static Map<String, Ingredient> deserializeKey(JsonObject json) {
		
		Map<String, Ingredient> map = Maps.newHashMap();
		
		for (Entry<String, JsonElement> entry : json.entrySet()) {
			
			if (entry.getKey().length() != 1) {
				
				throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
				
			}
			
			if (" ".equals(entry.getKey())) {
				
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
				
			}
			
			map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
			
		}
		
		map.put(" ", Ingredient.EMPTY);
		return map;
		
	}
	
	public static class Serializer implements IRecipeSerializer<ThreefoldCombinerRecipe> {

		private static final ResourceLocation NAME = new ResourceLocation("enhancedmechanisms", "threefold_combiner");
		
		@Override
		public ThreefoldCombinerRecipe read(ResourceLocation recipeId, JsonObject json) {

			String s = JsonUtils.getString(json, "group", "");
			Map<String, Ingredient> map = ThreefoldCombinerRecipe.deserializeKey(JsonUtils.getJsonObject(json, "pattern"));
			String[] astring = ThreefoldCombinerRecipe.shrink(ThreefoldCombinerRecipe.patternFromJson(JsonUtils.getJsonArray(json, "pattern")));
			int i = astring[0].length();
			int j = astring.length;
			NonNullList<Ingredient> nonnulllist = ThreefoldCombinerRecipe.deserializeIngredients(astring, map, i, j);
			ItemStack itemstack = ShapedRecipe.deserializeItem(JsonUtils.getJsonObject(json, "result"));
			float experience = JsonUtils.getFloat(json, "experience", 0.0f);
			int processingtime = JsonUtils.getInt(json, "processingtime", 200);
			return new ThreefoldCombinerRecipe(recipeId, j, i, s, nonnulllist, itemstack, experience, processingtime);
			
		}

		@Override
		public ThreefoldCombinerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {

			int i = buffer.readVarInt();
			int j = buffer.readVarInt();
			String s = buffer.readString(32767);
			NonNullList<Ingredient> nonnullist = NonNullList.withSize(i * j, Ingredient.EMPTY);
			
			for (int k = 0; k < nonnullist.size(); ++k) {
				
				nonnullist.set(k,  Ingredient.fromBuffer(buffer));
				
			}
			
			ItemStack itemstack = buffer.readItemStack();
			float exp = buffer.readFloat();
			int process = buffer.readInt();
			return new ThreefoldCombinerRecipe(recipeId, j, i, s, nonnullist, itemstack, exp, process);
			
		}

		@Override
		public void write(PacketBuffer buffer, ThreefoldCombinerRecipe recipe) {
			
			buffer.writeVarInt(recipe.recipeWidth);
			buffer.writeVarInt(recipe.recipeHeight);
			buffer.writeString(recipe.group);
			
			for (Ingredient ingredient : recipe.inputs) {
				
				ingredient.writeToBuffer(buffer);
				
			}
			
			buffer.writeItemStack(recipe.output);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.processingTime);
			
		}

		@Override
		public ResourceLocation getName() {

			return NAME;
			
		}
		
	}
	
}
