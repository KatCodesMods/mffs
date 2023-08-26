package dev.katcodes.mffs.common.world;

import dev.katcodes.mffs.api.ForcefieldTypes;

import java.util.UUID;

public class ForceFieldBlockInfo {
    public ForcefieldTypes type;
    public UUID network;

    public ForceFieldBlockInfo(ForcefieldTypes type, UUID network) {
        this.type=type;
        this.network=network;
    }
}
