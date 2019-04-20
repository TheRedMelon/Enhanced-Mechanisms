package theredmelon.enhancedmechanisms.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.RecipeSerializers;

public class MachineRecipeSerializers {

	public static final IRecipeSerializer<ThreefoldCombinerRecipe> THREEFOLD_COMBINER = RecipeSerializers.register(new ThreefoldCombinerRecipe.Serializer());
	
}
