package dev.katcodes.mffs.api;

public enum UpgradeTypes {
    Capacity(new MachineType[]{MachineType.CAPACITOR, MachineType.EXTRACTOR}),
    Range(new MachineType[]{MachineType.CAPACITOR,}),

    Booster(new MachineType[]{MachineType.EXTRACTOR}),
    ;


    private final MachineType[] allowedMachineTypes;

    public MachineType[] getAllowedMachineTypes() {
        return allowedMachineTypes;
    }

    UpgradeTypes(MachineType[] allowedTypes) {
        this.allowedMachineTypes=allowedTypes;
    }
}
