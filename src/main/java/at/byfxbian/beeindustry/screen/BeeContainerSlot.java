package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class BeeContainerSlot extends Slot {

    public BeeContainerSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(BeeIndustryItems.BEE_CONTAINER);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return !this.getStack().contains(BeeIndustryDataComponentTypes.IS_BEE_WORKING);
    }
}
