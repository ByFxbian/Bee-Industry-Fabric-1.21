package at.byfxbian.beeindustry.screen;

import at.byfxbian.beeindustry.item.BeeIndustryItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FuelSlot extends Slot {

    public FuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(BeeIndustryItems.SWEET_HONEY);
    }
}
