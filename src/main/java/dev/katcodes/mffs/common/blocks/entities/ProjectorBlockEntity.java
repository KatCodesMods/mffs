package dev.katcodes.mffs.common.blocks.entities;

import dev.katcodes.mffs.MFFSMod;
import dev.katcodes.mffs.api.*;
import dev.katcodes.mffs.common.blocks.ForceFieldBlock;
import dev.katcodes.mffs.common.blocks.ModBlocks;
import dev.katcodes.mffs.common.blocks.ProjectorBlock;
import dev.katcodes.mffs.common.configs.MFFSConfigs;
import dev.katcodes.mffs.common.inventory.ProjectorMenu;
import dev.katcodes.mffs.common.items.ModItems;
import dev.katcodes.mffs.common.items.modules.Module3DBase;
import dev.katcodes.mffs.common.items.modules.ModuleBase;
import dev.katcodes.mffs.common.items.modules.ProjectorModule;
import dev.katcodes.mffs.common.items.options.*;
import dev.katcodes.mffs.common.world.ForceFieldBlockStack;
import dev.katcodes.mffs.common.world.LevelMap;
import dev.katcodes.mffs.common.world.NetworkWorldData;
import dev.katcodes.mffs.common.world.data.NetworkData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ProjectorBlockEntity extends PoweredBlockEntity implements IModularProjector, MenuProvider {

    private int ProjektorTyp;
    private int linkPower;
    private int blockcounter;
    private boolean burnout;
    private int accesstyp;
    private int capacity;

    private int switchDelay;
    private boolean active;
    private BlockState camoState;

    private ForcefieldTypes forcefieldType=ForcefieldTypes.Default;
    private final int[] focusmatrix = { 0, 0, 0, 0 }; // up , down, right, left

    protected Stack<Integer> field_queue = new Stack<Integer>();
    protected Set<GlobalPos> field_interior = new HashSet<GlobalPos>();
    protected Set<GlobalPos> field_def = new HashSet<GlobalPos>();

    protected IItemHandlerModifiable typeHandler = createTypeSlot(this);

    protected IItemHandlerModifiable optionsHandler = createOptionsHandler(this);

    protected IItemHandlerModifiable distanceHandler = createDistanceHandler(this);
    protected IItemHandlerModifiable strengthHandler = createStrengthHandler(this);

    protected IItemHandlerModifiable fociHandler = createFociHandler(this);

    protected IItemHandlerModifiable centerHandler = createCenterHandler(this);
    protected IItemHandlerModifiable securityHandler = createSecurityHandler(this);

    public static IItemHandlerModifiable createSecurityHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(1) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                //return stack.getItem() instanceof ModuleBase;
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    public static IItemHandlerModifiable createCenterHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(1) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                //return stack.getItem() instanceof ModuleBase;
                return ProjectorBlockEntity.validCamouflageBlock(stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    public static IItemHandlerModifiable createFociHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(4) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
               return stack.getItem() == ModItems.FOCUS_MATRIX.get();

            }

        };
    }

    public static IItemHandlerModifiable createDistanceHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(1) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                //return stack.getItem() instanceof ModuleBase;
                return true;
            }

        };
    }
    public static IItemHandlerModifiable createStrengthHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(1) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                //return stack.getItem() instanceof ModuleBase;
                return true;
            }

        };
    }

    public static IItemHandlerModifiable createOptionsHandler(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(3) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof ProjectorOptionBaseItem;
                //return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    public static IItemHandlerModifiable createTypeSlot(ProjectorBlockEntity projectorBlockEntity) {
        return new ItemStackHandler(1) {

            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if(projectorBlockEntity!=null)
                    projectorBlockEntity.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof ModuleBase;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

        };
    }

    public ProjectorBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
        linkPower = 0;

        ProjektorTyp = 0;
        switchDelay = 0;
        burnout = false;
        accesstyp = 0;
        capacity = 0;
    }


    public IItemHandlerModifiable getLinkCardSlot() {
        return linkCardSlot;
    }
    public IItemHandlerModifiable getOptionsHandler() {
        return optionsHandler;
    }
    public IItemHandlerModifiable getDistanceHandler() {
        return distanceHandler;
    }
    public IItemHandlerModifiable getStrengthHandler() {
        return strengthHandler;
    }
    public IItemHandlerModifiable getFociHandler() {
        return fociHandler;
    }
    public IItemHandlerModifiable getCenterHandler() {
        return centerHandler;
    }

    public IItemHandlerModifiable getSecurityHandler() {
        return securityHandler;
    }


    @Override
    public MachineType getMachineType() {
        return MachineType.PROJECTOR;
    }
    public ForcefieldTypes getForcefieldType() {
        return forcefieldType;
    }

    public ProjectorBlockEntity setForcefieldType(ForcefieldTypes forcefieldType) {
        this.forcefieldType = forcefieldType;
        return this;
    }

    public boolean hasOption(Item item, boolean includeCheckAll) {
        for(ProjectorOptionBaseItem opt:getOptions(includeCheckAll))
            if(opt == item)
                return true;
        return false;
    }

    public List<ProjectorOptionBaseItem> getOptions(boolean includeCheckAll) {
        List<ProjectorOptionBaseItem> retList = new ArrayList<>();
        for(int i=0;i<2;i++) {
            if(optionsHandler.getStackInSlot(i)!=ItemStack.EMPTY) {
                if(optionsHandler.getStackInSlot(i).getItem() instanceof ProjectorOptionBaseItem)
                    retList.add((ProjectorOptionBaseItem) optionsHandler.getStackInSlot(i).getItem());
            }
            if(includeCheckAll) {
                for(ProjectorOptionBaseItem opt: ProjectorOptionBaseItem.get_instances())
                    if(opt instanceof IChecksOnAll && !retList.contains(opt))
                        retList.add(opt);
            }
        }
        return retList;
    }

    @Override
    protected void checkSlots() {
        if(!hasValidTypeModule() && this.getBlockState().getValue(ProjectorBlock.MODULE) != ProjectorModule.EMPTY) {
            this.level.setBlock(getBlockPos(),this.getBlockState().setValue(ProjectorBlock.MODULE,ProjectorModule.EMPTY),2);
        }
        if(hasValidTypeModule()) {
            if(getBlockState().getValue(ProjectorBlock.MODULE)!=((ModuleBase)typeHandler.getStackInSlot(0).getItem()).getModuleEnum())
            {this.level.setBlock(getBlockPos(),this.getBlockState().setValue(ProjectorBlock.MODULE,((ModuleBase)typeHandler.getStackInSlot(0).getItem()).getModuleEnum()),2);

            }
        }

        if(hasValidTypeModule()) {
            for(int i=0;i<4;i++) {
                if(fociHandler.getStackInSlot(i)!=ItemStack.EMPTY) {
                    //
                    if(fociHandler.getStackInSlot(i).getItem() == ModItems.FOCUS_MATRIX.get()) {
                        // Containment and Advance cube special casing here
                        switch (getModuleType().getModuleEnum()) {

                            default -> focusmatrix[i]=fociHandler.getStackInSlot(i).getCount();
                        }
                    } else {
                        Containers.dropItemStack(level,getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ(),fociHandler.getStackInSlot(i));
                        fociHandler.getStackInSlot(i).setCount(0);
                    }
                }
            }
        }

        if(centerHandler.getStackInSlot(0)!=ItemStack.EMPTY) {
            if(!validCamouflageBlock(centerHandler.getStackInSlot(0))) {
                Containers.dropItemStack(level,getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ(),centerHandler.getStackInSlot(0));
                centerHandler.getStackInSlot(0).setCount(0);
            }
            BlockState state = getCamoBlockState(centerHandler.getStackInSlot(0));
            if(this.camoState==null || !this.camoState.equals(state)) {
                this.camoState=state;
                this.updateForceFieldTexture();
            }
        } else {
            if(this.camoState!=null) {
                this.camoState=null;
                this.updateForceFieldTexture();
            }
        }

        if(hasOption(ModItems.OptionItems.CAMOUFLAGE.get(),true))
            if(getForcefieldType()!=ForcefieldTypes.Camo)
                setForcefieldType(ForcefieldTypes.Camo);


        if(hasOption(ModItems.OptionItems.TOUCH_DAMAGE.get(),true))
            if(getForcefieldType()!=ForcefieldTypes.Zapper)
                setForcefieldType(ForcefieldTypes.Zapper);

        if(hasValidTypeModule()) {
            ModuleBase module_type = getModuleType();
            if(!module_type.supportsStrength())
                dropPlugins(strengthHandler,0);
            if(!module_type.supportsDistance())
                dropPlugins(distanceHandler,0);
            if(!module_type.supportsMatrix()) {
                dropPlugins(fociHandler,0);
                dropPlugins(fociHandler,1);
                dropPlugins(fociHandler,2);
                dropPlugins(fociHandler,3);
            }
            for(int i=0;i<optionsHandler.getSlots();i++) {
                if(optionsHandler.getStackInSlot(i)!=ItemStack.EMPTY) {
                    if(!module_type.supportsOption(optionsHandler.getStackInSlot(i).getItem()))
                        dropPlugins(optionsHandler,i);

                    // todo: add rest in once understand
                }
            }
            if(!hasOption(ModItems.OptionItems.CAMOUFLAGE.get(),true))
                dropPlugins(centerHandler,0);

        } else {
            dropPlugins(strengthHandler,0);
            dropPlugins(distanceHandler,0);
            dropPlugins(fociHandler,0);
            dropPlugins(fociHandler,1);
            dropPlugins(fociHandler,2);
            dropPlugins(fociHandler,3);
            dropPlugins(centerHandler,0);
            dropPlugins(optionsHandler,0);
            dropPlugins(optionsHandler,1);
            dropPlugins(optionsHandler,2);
        }


    }

    private BlockState getCamoBlockState(ItemStack stackInSlot) {
        return null;
    }

    private void updateForceFieldTexture() {
        if(this.isActive() && this.hasOption(ModItems.OptionItems.CAMOUFLAGE.get(),true)) {
            for(GlobalPos pos: this.field_def) {
                BlockEntity blockEntity = level.getBlockEntity(pos.pos());
                if(blockEntity!=null && blockEntity instanceof ForceFieldBlockEntity) {
                    ((ForceFieldBlockEntity)blockEntity).updateTexture();
                }
            }
        }
    }
    public  void dropPlugins(IItemHandlerModifiable handler, int stack) {
        Containers.dropItemStack(getLevel(),getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ(),handler.getStackInSlot(stack));
        handler.getStackInSlot(stack).setCount(0);
    }

    public static boolean validCamouflageBlock(ItemStack stack) {

        // TODO: Remake
        // @KatGaea the check for block item is fine, but for water and lava, I'd actually recommend using the fluidhandler of the item and get the fluid, then check to see if there is an inworld block of it 🙂 That way you can have a chocolate forcefield with create
        //Instead of hardcoding water + lava
        //Then finally a "mffs:valid_camo" tag
        //  - Soaryn
        if(!(stack.getItem() instanceof BlockItem) && (stack.getItem()!= Items.LAVA_BUCKET) && (stack.getItem()!= Items.WATER_BUCKET))
            return false;
        if(stack.getItem() instanceof BlockItem blockItem) {
            return !(blockItem.getBlock() instanceof EntityBlock);
        }

        return true;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        getNetworkData().ifPresent(data -> {
            setLinkPower(data.getFEStored());
        });
        if(!getNetworkData().isPresent())
            setLinkPower(0);

        boolean active=getBlockState().getValue(ProjectorBlock.ACTIVATED);
        if((active && (switchDelay >= 40 ))
                && hasValidTypeModule()
                && getLinkPower() > forcePowerDraw(5)) {
            if(isActive()!=true) {
                setActive(true);
                switchDelay=0;
                try {
                    if(calculateField(true))
                        fieldGenerate(true);
                } catch(ArrayIndexOutOfBoundsException ex) {
                    MFFSMod.LOGGER.error("Old bug found here",ex);
                }
                this.setChanged();
            }
        }
        if((!active && (switchDelay>=40))
        || hasValidTypeModule()
        || !getNetworkData().isPresent()
        || burnout
        || getLinkPower() < forcePowerDraw(1)) {
            if(isActive() != false){
                setActive(false);
                switchDelay=0;
                destroyField();
                this.setChanged();
            }
        }
        if(this.tickCount % 20 == 0) {
            if(isActive()) {
                fieldGenerate(false);
                if(hasOption(ModItems.OptionItems.MOB_DEFENCE.get(),true))
                    MobDefenceOptionItem.ProjectorNPCDefence(this,level);
                if(hasOption(ModItems.OptionItems.DEFENSE_STATION.get(), true))
                    DefenseStationOptionItem.ProjectorPlayerDefence(this,level);
            }
        }
        this.switchDelay++;

    }

    public void destroyField() {
        while(!field_queue.isEmpty()) {
            ForceFieldBlockStack ffLevelMap = LevelMap.getForceFieldLevel(level).getForceFieldStackMap(field_queue.pop());
            if(!ffLevelMap.isEmpty()) {
                if(ffLevelMap.getNetwork() == getNetworkId()) {
                    if(ffLevelMap.isSync())
                    {
                        level.setBlock(ffLevelMap.getPoint().pos(),Blocks.AIR.defaultBlockState(), 2);
                    }
                    ffLevelMap.setSync(false);
                } else {
                    ffLevelMap.removeByNetwork(getNetworkId());
                }
            }
        }

        // linkgrid
        getNetworkData().ifPresent(data -> {
            if(data instanceof NetworkData networkWorldData) {
                List<GlobalPos> fieldFusion = networkWorldData.getNetworkList(MachineType.FIELD_FUSION);
                for(GlobalPos pos:fieldFusion) {
                    if(pos.pos().distSqr(getBlockPos()) < networkWorldData.getDistance()*networkWorldData.getDistance()) {
                        BlockEntity blockEntity=level.getBlockEntity(pos.pos());
                        if(blockEntity instanceof ProjectorBlockEntity projectorBlockEntity) {
                            if(projectorBlockEntity.getNetworkId()==getNetworkId()) {
                                if(projectorBlockEntity.isActive())
                                    projectorBlockEntity.calculateField(false);
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void setRemoved() {
        getNetworkData().ifPresent(data -> {
            if (data instanceof NetworkData networkWorldData) {
                networkWorldData.getNetworkMachines().get(MachineType.PROJECTOR).remove(GlobalPos.of(level.dimension(), getBlockPos()));

            }

        });
        destroyField();
        super.setRemoved();
    }

    private int forcePowerDraw(int factor) {
        if(!field_def.isEmpty()) {
            return field_def.size() * MFFSConfigs.PROJECTOR_BLOCK_COST.get();
        }
        int forcePower = 0;
        int blocks = 0;
        int tmpLength=0;
        if(this.strengthHandler.getStackInSlot(0).getCount()!=0) {
            tmpLength = this.strengthHandler.getStackInSlot(0).getCount();
        }
        switch (getModuleType().getModuleEnum()) {

            case EMPTY -> {
            }
            case WALL_MODULE -> {

            }
        }
        return  forcePower;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public int countItemsInSlot(ProjectorSlots slt) {
        switch (slt) {
            case Card -> {
                return linkCardSlot.getStackInSlot(0).getCount();
            }
            case Type -> {
                return typeHandler.getStackInSlot(0).getCount();
            }
            case Option1 -> {
                return optionsHandler.getStackInSlot(0).getCount();
            }
            case Option2 -> {
                return optionsHandler.getStackInSlot(1).getCount();
            }
            case Option3 -> {
                return optionsHandler.getStackInSlot(2).getCount();
            }
            case Distance -> {
                return distanceHandler.getStackInSlot(0).getCount();
            }
            case Strength -> {
                return strengthHandler.getStackInSlot(0).getCount();
            }
            case FocusUp -> {
                return fociHandler.getStackInSlot(0).getCount();
            }
            case FocusDown -> {
                return fociHandler.getStackInSlot(1).getCount();
            }
            case FocusLeft -> {
                return fociHandler.getStackInSlot(3).getCount();
            }
            case FocusRight -> {
                return fociHandler.getStackInSlot(2).getCount();
            }
            case Center -> {
                return centerHandler.getStackInSlot(0).getCount();
            }
            case Security -> {
                return securityHandler.getStackInSlot(0).getCount();
            }

            default -> throw new IllegalStateException("Unexpected value: " + slt);
        }
    }

    @Override
    public UUID getNetworkId() {
        return null;
    }

    @Override
    public Set<GlobalPos> getInteriorPoints() {
        return null;
    }

    @Override
    protected boolean firstInit() {
        checkSlots();
        if(isActive())
            calculateField(true);
        return true;
    }

    private boolean calculateField(boolean addToMap) {

        long time = System.currentTimeMillis();

        field_def.clear();
        field_interior.clear();
        if (hasValidTypeModule()) {
            Set<GlobalPos> tField = new HashSet<GlobalPos>();
            Set<GlobalPos> tFieldInt = new HashSet<GlobalPos>();

            if (getModuleType() instanceof Module3DBase) {
                ((Module3DBase) getModuleType()).calculateField(this, tField,
                        tFieldInt);
            } else {
                getModuleType().calculateField(this, tField);
            }

            for (GlobalPos pnt : tField) {

                if (pnt.pos().getY() + this.getBlockPos().getY() < 255) {
                    GlobalPos tp = GlobalPos.of(level.dimension(),new BlockPos(
                            pnt.pos().getX()+this.getBlockPos().getX(),
                            pnt.pos().getY()+this.getBlockPos().getY(),
                            pnt.pos().getZ()+this.getBlockPos().getZ()));

                    if (forceFieldDefine(tp, addToMap)) {
                        field_def.add(tp);
                    } else {
                        return false;
                    }
                }
            }
			/*for (PointXYZ pnt : tFieldInt) {

				if (pnt.Y + this.yCoord < 255) {
					PointXYZ tp = new PointXYZ(pnt.X + this.xCoord, pnt.Y
							+ this.yCoord, pnt.Z + this.zCoord, worldObj);

					if (calculateBlock(tp)) {
						field_interior.add(tp);
					} else {
						return false;
					}
				}
			}*/

            return true;
        }
        return false;
    }

    public boolean calculateBlock(GlobalPos pos) {
        for(ProjectorOptionBaseItem opt: getOptions(true)) {
            if(opt instanceof IInteriorCheck) {
                ((IInteriorCheck)opt).checkInteriorBlock(pos,level,this);
            }
        }
        return true;
    }

    public boolean forceFieldDefine(GlobalPos pos, boolean addToMap) {
        for(ProjectorOptionBaseItem opt:getOptions(true)) {
            if(opt ==ModItems.OptionItems.FIELD_JAMMER.get())
                if(((FieldJammerOptionItem)opt).CheckJammerinfluence(pos,level,this))
                    return false;
            if(opt == ModItems.OptionItems.FIELD_FUSION.get())
                if(((FieldFusionOptionItem)opt).checkFieldFusioninfluence(pos,level,this))
                    return true;
        }
        ForceFieldBlockStack ffworldmap = LevelMap.getForceFieldLevel(level).getOrCreate(pos,level);
        if(!ffworldmap.isEmpty()) {
            if(ffworldmap.getNetwork() != getNetworkId()) {
                ffworldmap.removeByNetwork(getNetworkId());
                ffworldmap.add(getNetworkId(),getForcefieldType());
            }
        } else {
            ffworldmap.add(getNetworkId(),getForcefieldType());
            ffworldmap.setSync(false);
        }

        field_queue.push(pos.hashCode());
        return true;
    }

    public void fieldGenerate(boolean init) {
        int cost=0;
        if(init)
            cost = MFFSConfigs.PROJECTOR_BLOCK_COST.get() * MFFSConfigs.PROJECTOR_BLOCK_CREATE.get();
        else
            cost = MFFSConfigs.PROJECTOR_BLOCK_COST.get();
        if(getForcefieldType() == ForcefieldTypes.Zapper) {
            cost *=MFFSConfigs.PROJECTOR_BLOCK_ZAPPER.get();
        }
        int finalCost = cost;
        getNetworkData().ifPresent(data -> {
            data.extractFE(finalCost * field_def.size(), false);
        });
        blockcounter = 0;
        for(GlobalPos pnt:field_def) {
            if(blockcounter >= MFFSConfigs.PROJECTOR_MAX_TICK.get())
                break;
            ForceFieldBlockStack ffb = LevelMap.getForceFieldLevel(level).getForceFieldStackMap(pnt.hashCode());
            if(ffb!=null) {
                if(ffb.isSync())
                    continue;
                GlobalPos pos = ffb.getPoint();
                if(level.isLoaded(pos.pos())) {
                    if(!ffb.isEmpty()) {
                        if(ffb.getNetwork() == getNetworkId()) {
                            if(hasOption(ModItems.OptionItems.BLOCK_BREAKER.get(), true)) {
                                BlockState blkState = level.getBlockState(pos.pos());
                                if(blkState.getBlock() != ModBlocks.FORCE_FIELD.get()
                                && !blkState.isAir()
                                && blkState.getBlock()!= Blocks.BEDROCK
                                && level.getBlockEntity(pos.pos()) == null) {
                                    var context = new LootParams.Builder((ServerLevel) level)
                                            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos.pos()))
                                            .withParameter(LootContextParams.TOOL, new ItemStack(Items.NETHERITE_PICKAXE))
                                            .withParameter(LootContextParams.BLOCK_STATE, blkState)
                                            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos.pos()))
                                            ;
                                    List<ItemStack> drops = blkState.getDrops(context);
                                    if(!drops.isEmpty()) {
                                        for(ItemStack stack:drops) {
                                            //TODO:  adddrops to inventory;
                                        }
                                    }

                                }
                            }

                            if(level.getBlockState(pos.pos()).liquid()
                            || level.getBlockState(pos.pos()).isAir()
                            || level.getBlockState(pos.pos()).getBlock() == ModBlocks.FORCE_FIELD.get()) {
                                if(level.getBlockState(pos.pos()).getBlock()!=ModBlocks.FORCE_FIELD.get()) {
                                    level.setBlock(pos.pos(), ModBlocks.FORCE_FIELD.get().defaultBlockState().setValue(ForceFieldBlock.BLOCK_TYPE,getForcefieldType()),2);
                                    blockcounter++;
                                }
                                ffb.setSync(true);
                            }
                        }
                    }
                }

            }
        }
    }

    @Override
    public void setBurnedOut(boolean burnOut) {
        this.burnout=burnOut;
        this.setChanged();
    }

    public ModuleBase getModuleType() {
        if(hasValidTypeModule()) {
            return (ModuleBase) typeHandler.getStackInSlot(0).getItem();
        }
        return null;
    }
    @Override
    public boolean isActive() {
        return active;
    }

    public ProjectorBlockEntity setActive(boolean active)
    {
        this.active=active;
        this.setChanged();
        return this;
    }

    @Override
    public Direction getSide() {
        return getBlockState().getValue(ProjectorBlock.FACING);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ProjectorMenu(pContainerId,pPlayerInventory,new SimpleContainerData(6), this);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("linkCard", ((ItemStackHandler) linkCardSlot).serializeNBT());
        pTag.put("type", ((ItemStackHandler) typeHandler).serializeNBT());
        pTag.put("options",((ItemStackHandler)optionsHandler).serializeNBT());
        pTag.put("distance",((ItemStackHandler)distanceHandler).serializeNBT());
        pTag.put("strength",((ItemStackHandler)strengthHandler).serializeNBT());
        pTag.put("foci",((ItemStackHandler)fociHandler).serializeNBT());
        pTag.put("center",((ItemStackHandler)centerHandler).serializeNBT());
        pTag.put("security",((ItemStackHandler)securityHandler).serializeNBT());
        pTag.putBoolean("active",active);

    }


    @Override
    public void load(CompoundTag pTag) {
        ((ItemStackHandler) linkCardSlot).deserializeNBT(pTag.getCompound("linkCard"));
        ((ItemStackHandler)typeHandler).deserializeNBT(pTag.getCompound("type"));
        if(pTag.contains("options"))
            ((ItemStackHandler)optionsHandler).deserializeNBT(pTag.getCompound("options"));
        if(pTag.contains("distance"))
            ((ItemStackHandler)distanceHandler).deserializeNBT(pTag.getCompound("distance"));
        if(pTag.contains("strength"))
            ((ItemStackHandler)strengthHandler).deserializeNBT(pTag.getCompound("strength"));
        if(pTag.contains("foci"))
            ((ItemStackHandler)fociHandler).deserializeNBT(pTag.getCompound("foci"));
        if(pTag.contains("center"))
            ((ItemStackHandler)centerHandler).deserializeNBT(pTag.getCompound("center"));
        if(pTag.contains("security"))
            ((ItemStackHandler)securityHandler).deserializeNBT(pTag.getCompound("security"));
        if(pTag.contains("active"))
            active=pTag.getBoolean("active");

    }

    public IItemHandlerModifiable getModuleTypeHandler() {
        return typeHandler;
    }

    public boolean hasValidTypeModule() {
        return typeHandler.getStackInSlot(0).getCount()>0 && typeHandler.getStackInSlot(0).getItem() instanceof ModuleBase;
    }

    public int getLinkPower() {
        return linkPower;
    }

    public ProjectorBlockEntity setLinkPower(int linkPower) {
        this.linkPower = linkPower;
        this.setChanged();
        return this;
    }
}
