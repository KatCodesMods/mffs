package dev.katcodes.mffs.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelMap {
    private static final Map<ResourceKey<Level>, ForceFieldLevel> forceFieldLevels =  new HashMap<ResourceKey<Level>,ForceFieldLevel>();
    public static class ForceFieldLevel {
        private static final Map<Integer, ForceFieldBlockStack> forceFieldBlockStackMap = new HashMap<>();
        public ForceFieldBlockStack getOrCreate(GlobalPos pos, Level level) {
            if(forceFieldBlockStackMap.get(pos.hashCode()) == null)
                forceFieldBlockStackMap.put(pos.hashCode(),new ForceFieldBlockStack(pos));
            return forceFieldBlockStackMap.get(pos.hashCode());
        }
        public ForceFieldBlockStack getForceFieldStackMap(Integer hasher) {
            return forceFieldBlockStackMap.get(hasher);
        }

        public ForceFieldBlockStack getForceFieldStackMap(GlobalPos pos) {
            return forceFieldBlockStackMap.get(pos.hashCode());
        }

        public UUID existingForceFieldStackMap(int x, int y, int z, int counter,
                                              int facing, Level world) {
            switch (facing) {
                case 0:
                    y += counter;
                    break;
                case 1:
                    y -= counter;
                    break;
                case 2:
                    z += counter;
                    break;
                case 3:
                    z -= counter;
                    break;
                case 4:
                    x += counter;
                    break;
                case 5:
                    x -= counter;
                    break;
            }

            ForceFieldBlockStack Map = forceFieldBlockStackMap.get(GlobalPos.of(world.dimension(),new BlockPos(x,y,z)).hashCode());

            if (Map == null) {
                return null;
            } else {
                if (Map.isEmpty()) {
                    return null;
                }

                return Map.getNetwork();
            }
        }
    }

    public static ForceFieldLevel getForceFieldLevel(Level level) {
        if(level!=null) {
            if(!forceFieldLevels.containsKey(level.dimension())) {
                forceFieldLevels.put(level.dimension(),new ForceFieldLevel());
            }
            return forceFieldLevels.get(level.dimension());
        }
        return null;
    }
}
