package com.marc.world;

public class Camera {

    public static int cameraOffsetX = 0;
    public static int cameraOffsetY = 0;

    public static int clamp(int currentOffset, int minOffset, int maxOffset) {
        if(currentOffset < minOffset) {
            currentOffset = minOffset;
        }
        if(currentOffset > maxOffset) {
            currentOffset = maxOffset;
        }
        return currentOffset;
    }

}
