package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class UpgradeSlot extends Slot {
    //private final Slot parentSlot;

    public UpgradeSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        //this.parentSlot = parentSlot;
    }

   /* @Override
    public boolean isEnabled() {
        return this.parentSlot.hasStack();
    }*/

    @Override
    public boolean canInsert(ItemStack stack) {
        return /*isEnabled() &&*/ stack.isOf(BeeIndustryItems.EFFICIENCY_UPGRADE) ||
                stack.isOf(BeeIndustryItems.QUANTITY_UPGRADE) ||
                stack.isOf(BeeIndustryItems.NIGHT_CHARM) ||
                stack.isOf(BeeIndustryItems.RANGE_UPGRADE);
    }
}
