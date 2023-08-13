package dev.katcodes.mffs.api;

public enum UpgradeTypes {
    Capacity(new MachineType[]{MachineType.CAPACITOR}),
    Range(new MachineType[]{MachineType.CAPACITOR}),;

    private final MachineType[] allowedMachineTypes;

    public MachineType[] getAllowedMachineTypes() {
        return allowedMachineTypes;
    }

    UpgradeTypes(MachineType[] allowedTypes) {
        this.allowedMachineTypes=allowedTypes;
    }
}
