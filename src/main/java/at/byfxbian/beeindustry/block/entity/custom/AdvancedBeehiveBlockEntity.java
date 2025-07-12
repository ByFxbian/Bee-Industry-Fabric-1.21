package at.byfxbian.beeindustry.block.entity.custom;

import at.byfxbian.beeindustry.BeeIndustry;
import at.byfxbian.beeindustry.api.CustomBee;
import at.byfxbian.beeindustry.block.entity.BeeIndustryBlockEntities;
import at.byfxbian.beeindustry.components.BeeIndustryDataComponentTypes;
import at.byfxbian.beeindustry.datagen.custom.BeeIndustryDynamicRegistryProvider;
import at.byfxbian.beeindustry.entities.BeeIndustryEntities;
import at.byfxbian.beeindustry.entities.custom.CustomBeeEntity;
import at.byfxbian.beeindustry.entities.goal.GoToProductionBlockGoal;
import at.byfxbian.beeindustry.item.BeeIndustryItems;
import at.byfxbian.beeindustry.screen.AdvancedBeehiveScreenHandler;
import at.byfxbian.beeindustry.util.BeeRegistries;
import at.byfxbian.beeindustry.util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class AdvancedBeehiveBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {

    public enum BeeState {
        IDLE,
        WORKING,
        PRODUCING
    }

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    private BeeState currentState = BeeState.IDLE;
    @Nullable
    private UUID assignedBeeUuid = null;

    private int beeMissingTicks = 0;

    private int progress = 0;
    private int maxProgress = 200;

    public AdvancedBeehiveBlockEntity( BlockPos pos, BlockState state) {
        super(BeeIndustryBlockEntities.ADVANCED_BEEHIVE_BLOCK_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state, AdvancedBeehiveBlockEntity be) {
        if(world.isClient) {
            return;
        }

        switch (be.currentState) {
            case IDLE:
                if (be.hasIntactBeeContainer()) {
                    be.spawnWorkerBee();
                }
                break;
            case WORKING:
                be.validateWorkingBee();
                break;
            case PRODUCING:
                if (be.canProduce()) {
                    int calculedMaxProgress = be.maxProgress;

                    Optional<CustomBee> beeOptional = be.getContainedBeeData();
                    if(beeOptional.isPresent()) {
                        int productivity = beeOptional.get().attributes().productivity();
                        calculedMaxProgress = Math.max(20, be.maxProgress - (productivity * 10));
                    }

                    be.progress += (1 + be.getUpgradeCount(BeeIndustryItems.EFFICIENCY_UPGRADE));
                    markDirty(world, pos, state);

                    if (be.progress >= calculedMaxProgress) {
                        be.craftItem();
                        be.progress = 0;
                        be.currentState = BeeState.IDLE;
                        markDirty(world, pos, state);
                    }
                } else {

                }
                break;
        }
    }

    private void spawnWorkerBee() {
        if (world == null || world.isClient || !hasIntactBeeContainer()) return;

        Optional<CustomBee> beeDataOptional = getContainedBeeData();
        if (beeDataOptional.isEmpty()) return;

        CustomBeeEntity beeToSpawn = BeeIndustryEntities.CUSTOM_BEE_ENTITY.create(world);
        if (beeToSpawn != null) {
            beeToSpawn.setCustomBee(world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY).getEntry(beeDataOptional.get()));
            beeToSpawn.setHivePos(this.pos);
            beeToSpawn.initializeWorkerGoals();

            beeToSpawn.updatePosition(this.pos.getX() + 0.5, this.pos.getY() + 1.5, this.pos.getZ() + 0.5);
            world.spawnEntity(beeToSpawn);

            this.assignedBeeUuid = beeToSpawn.getUuid();
            this.currentState = BeeState.WORKING;
            this.getStack(0).set(BeeIndustryDataComponentTypes.IS_BEE_WORKING, true);
            markDirty();
            System.out.println("Spawned worker bee and set state to WORKING.");
        }

    }

    public boolean hasUpgrade(Item item) {
        return getStack(1).isOf(item) || getStack(2).isOf(item) || getStack(3).isOf(item);
    }

    public void onWorkedBeeReturned() {
        System.out.println("BEEHIVE: Worker has returned. Changing state to PRODUCING.");
        this.assignedBeeUuid = null;
        this.currentState = BeeState.PRODUCING;

        this.getStack(0).remove(BeeIndustryDataComponentTypes.IS_BEE_WORKING);
        markDirty();
    }

    private void craftItem() {
        Optional<CustomBee> beeDataOptional = getContainedBeeData();
        if (beeDataOptional.isEmpty()) return;
        CustomBee beeData = beeDataOptional.get();

        Item resultItem = Registries.ITEM.get(Identifier.of(beeData.productionItem()));
        int amountToProduce = 1 + getBonusAmount();

        ItemStack outputStack = this.getStack(4);
        if (outputStack.isEmpty()) {
            this.setStack(4, new ItemStack(resultItem, amountToProduce));
        } else if (outputStack.isOf(resultItem)) {
            int newCount = Math.min(outputStack.getCount() + amountToProduce, outputStack.getMaxCount());
            outputStack.setCount(newCount);
        }
        System.out.println("Crafted " + amountToProduce + " of " + beeData.productionItem());
    }

    private void validateWorkingBee() {
        if (this.assignedBeeUuid == null) {
            System.out.println("In WORKING state but no bee assigned. Resetting.");
            resetToIdle();
            return;
        }

        if (world.getTime() % 60 != 0) return; // Prüfe nur alle 3 Sekunden

        boolean beeExists = ((ServerWorld) world).getEntity(this.assignedBeeUuid) != null;
        if (!beeExists) {
            this.beeMissingTicks++;
            if (this.beeMissingTicks > 3) { // ca. 9 Sekunden Geduld
                System.out.println("Worker bee has been missing for too long, resetting.");
                resetToIdle();
            }
        } else {
            this.beeMissingTicks = 0;
        }
    }

    private void resetToIdle() {
        this.getStack(0).remove(BeeIndustryDataComponentTypes.IS_BEE_WORKING); // Wichtig: Container entsperren
        this.assignedBeeUuid = null;
        this.currentState = BeeState.IDLE;
        this.beeMissingTicks = 0;
        this.progress = 0;
        markDirty();
    }

    private boolean hasIntactBeeContainer() {
        ItemStack stack = this.getStack(0);
        return !stack.isEmpty()
                && stack.isOf(BeeIndustryItems.BEE_CONTAINER)
                && stack.contains(BeeIndustryDataComponentTypes.STORED_BEE_TYPE)
                && !stack.contains(BeeIndustryDataComponentTypes.IS_BEE_WORKING);
    }

    private Optional<CustomBee> getContainedBeeData() {
        ItemStack container = this.getStack(0);
        if (container.isEmpty()) return Optional.empty();
        String beeIdString = container.get(BeeIndustryDataComponentTypes.STORED_BEE_TYPE);
        if (beeIdString == null || this.world == null) return Optional.empty();
        return Optional.ofNullable(this.world.getRegistryManager().get(BeeRegistries.BEE_REGISTRY_KEY).get(Identifier.of(beeIdString)));
    }

    private boolean canProduce() {
        Optional<CustomBee> beeDataOptional = getContainedBeeData();
        if(beeDataOptional.isEmpty()) return false;
        Item resultItem = Registries.ITEM.get(Identifier.of(beeDataOptional.get().productionItem()));
        ItemStack outputStack = this.getStack(4);
        return outputStack.isEmpty() || (outputStack.isOf(resultItem) && outputStack.getCount() < outputStack.getMaxCount());
    }

    private int getBonusAmount() {
        int bonus = 0;
        int quantityLevel = getUpgradeCount(BeeIndustryItems.QUANTITY_UPGRADE);
        for (int i = 0; i < quantityLevel; i++) {
            if (world != null && world.random.nextFloat() < 0.33f) {
                bonus++;
            }
        }
        return bonus;
    }

    private int getUpgradeCount(Item upgradeItem) {
        int count = 0;
        if (this.getStack(1).isOf(upgradeItem)) count++;
        if (this.getStack(2).isOf(upgradeItem)) count++;
        if (this.getStack(3).isOf(upgradeItem)) count++;
        return count;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putString("currentState", this.currentState.name());
        if (this.assignedBeeUuid != null) nbt.putUuid("assignedBeeUuid", this.assignedBeeUuid);
        nbt.putInt("beeMissingTicks", this.beeMissingTicks);
        nbt.putInt("progress", this.progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        this.currentState = BeeState.valueOf(nbt.getString("currentState"));
        if (nbt.containsUuid("assignedBeeUuid")) this.assignedBeeUuid = nbt.getUuid("assignedBeeUuid");
        this.beeMissingTicks = nbt.getInt("beeMissingTicks");
        this.progress = nbt.getInt("progress");
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.beeindustry.advanced_beehive");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AdvancedBeehiveScreenHandler(syncId, playerInventory, this);
    }
}
