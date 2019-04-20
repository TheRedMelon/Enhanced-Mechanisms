package theredmelon.enhancedmechanisms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import theredmelon.enhancedmechanisms.gui.GUIThreefoldCombiner;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;
import theredmelon.enhancedmechanisms.utils.Reference;

@Mod(Reference.MOD_ID)
public class EnhancedMechanisms {

	public EnhancedMechanisms() {
		
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> {
			
			return (OpenContainer) -> {
				
				ResourceLocation loc = OpenContainer.getId();
				
				if (loc.toString().equals(Reference.MOD_ID + ":threefold_combiner")) {
					
					EntityPlayerSP player = Minecraft.getInstance().player;
					BlockPos pos = OpenContainer.getAdditionalData().readBlockPos();
					TileEntity te = player.world.getTileEntity(pos);
					
					if (te instanceof TileEntityThreefoldCombiner) {
						
						return new GUIThreefoldCombiner(player.inventory, (TileEntityThreefoldCombiner) te);
						
					}
					
				}
				
				return null;
				
			};
			
		});
		
	}
	
}
