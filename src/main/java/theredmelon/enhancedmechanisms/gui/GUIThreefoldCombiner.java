package theredmelon.enhancedmechanisms.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import theredmelon.enhancedmechanisms.objects.machines.containers.ContainerThreefoldCombiner;
import theredmelon.enhancedmechanisms.tileentities.TileEntityThreefoldCombiner;
import theredmelon.enhancedmechanisms.utils.Reference;

public class GUIThreefoldCombiner extends GuiContainer {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/threefold_combiner.png");
	
	public GUIThreefoldCombiner(InventoryPlayer inventory, TileEntityThreefoldCombiner te) {
		
		super(new ContainerThreefoldCombiner(inventory, te));
		
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {

		drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		mc.getTextureManager().bindTexture(TEXTURE);
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
		
	}
	
}
