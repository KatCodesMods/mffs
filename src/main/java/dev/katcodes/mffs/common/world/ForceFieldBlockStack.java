package dev.katcodes.mffs.common.world;

import dev.katcodes.mffs.api.ForcefieldTypes;
import net.minecraft.core.GlobalPos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class ForceFieldBlockStack {
    private GlobalPos pos;
    private boolean sync;

    protected Queue<ForceFieldBlockInfo> blocks = new LinkedList<ForceFieldBlockInfo>();

    public Queue<ForceFieldBlockInfo> getBlocks() {
        return blocks;
    }

    public ForceFieldBlockStack(GlobalPos pos) {
        this.pos=pos;
        sync=false;
    }

    public int getSize() {
        return blocks.size();
    }

    public void removeBlock() {
        blocks.poll();
    }

    public synchronized void removeByNetwork(UUID network) {
        ArrayList<ForceFieldBlockInfo> tempblock = new ArrayList<ForceFieldBlockInfo>();

        for (ForceFieldBlockInfo ffblock : blocks) {
            if (ffblock.network == network) {
                tempblock.add(ffblock);
            }
        }
        if (!tempblock.isEmpty())
            blocks.removeAll(tempblock);
    }

    public UUID getNetwork() {
        ForceFieldBlockInfo ffblock = blocks.peek();
        if (ffblock != null) {
            return ffblock.network;
        }
        return null;
    }


    public ForcefieldTypes getTyp() {
        ForceFieldBlockInfo ffblock = blocks.peek();
        if (ffblock != null) {
            return ffblock.type;
        }
        return ForcefieldTypes.Default;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public boolean isSync() {
        return sync;
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public ForceFieldBlockInfo get() {
        return blocks.peek();
    }
    public void add(UUID network, ForcefieldTypes type) {
        blocks.offer(new ForceFieldBlockInfo(type,network));
    }

    public GlobalPos getPoint() {
        return pos;
    }
}
