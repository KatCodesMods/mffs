package dev.katcodes.mffs.common.items.options;

import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;

public enum Options {
    BlockBreaker("block_breaker", "Block Breaker Option"),
    Camouflage,
    DefenseStation("defense_station", "Defense Station Option", DefenseStationOptionItem::new),
    FieldFusion("field_fusion", "Field Fusion Option", FieldFusionOptionItem::new),
    FieldManipulator("field_manipulator", "Field Manipulator Option"),
    FieldJammer("field_jammer", "Field Jammer Option", FieldJammerOptionItem::new),
    MobDefence("mob_defence", "Mob Defence Option", MobDefenceOptionItem::new),
    Sponge(SpongeOptionItem::new),
    TouchDamage("touch_damage", "Touch Damage Option");
    private final String resourceName;
    private final String englishName;
    private final NonNullFunction<Item.Properties, ProjectorOptionBaseItem> base;

    Options(String resourceName, String englishName) {
        this.resourceName=resourceName;
        this.englishName=englishName;
        this.base=ProjectorOptionBaseItem::new;
    }
    Options() {
        this.resourceName=this.name().toLowerCase();
        this.englishName=this.name()+" Option";
        this.base=ProjectorOptionBaseItem::new;
    }
    Options(String resourceName, String englishName, NonNullFunction<Item.Properties, ProjectorOptionBaseItem> base) {
        this.resourceName=resourceName;
        this.englishName=englishName;
        this.base=base;
    }
    Options(NonNullFunction<Item.Properties, ProjectorOptionBaseItem> base) {
        this.resourceName=this.name().toLowerCase();
        this.englishName=this.name()+" Option";
        this.base=base;
    }
    public String getResourceName() {
        return this.resourceName;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public NonNullFunction<Item.Properties, ProjectorOptionBaseItem> getBase() {
        return base;
    }
}
