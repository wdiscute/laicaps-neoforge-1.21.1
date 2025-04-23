package com.wdiscute.laicaps.block.telescope;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.List;

public class RevealRenderUtil
{

    public static void renderRevealingPanel(PoseStack matrices, float x, float y, float sizeX, float sizeY, List<Vector2f> points, List<Float> revealRadiuses) {
        RenderSystem.enableBlend();
        float time = 0;
        float[] arr = new float[256];
        float[] radiuses = new float[128];
        float[] noiseSpreadsArr = new float[128];

        for (int i = 0; i < arr.length; i += 2) {

            float lmx;
            float lmy;

            if (i / 2 < points.size()) {
                Vector2f v = points.get(i / 2);
                lmx = (v.x - x) / sizeX;
                lmy = (v.y - y) / sizeY;

                radiuses[i / 2] = revealRadiuses.get(i / 2);
            } else {
                radiuses[i / 2] = 0.0001f;
                lmx = -100;
                lmy = -100;
            }
            noiseSpreadsArr[i / 2] = 0.000001f;
            arr[i] = lmx;
            arr[i + 1] = lmy;
        }


        Matrix4f mat = matrices.last().pose();


        RenderSystem.setShader(() -> Shaders.REVEAL_SHADER);
        Shaders.REVEAL_SHADER.safeGetUniform("revealRadiuses").set(radiuses);
        Shaders.REVEAL_SHADER.safeGetUniform("noiseSpreads").set(noiseSpreadsArr);
        Shaders.REVEAL_SHADER.safeGetUniform("positions").set(arr);
        Shaders.REVEAL_SHADER.safeGetUniform("pixelCount").set(110F);


        Shaders.REVEAL_SHADER.safeGetUniform("greenRadius").set(0.035f);
        Shaders.REVEAL_SHADER.safeGetUniform("size").set(sizeX, sizeY);
        Shaders.REVEAL_SHADER.safeGetUniform("time").set(time);
        Shaders.REVEAL_SHADER.safeGetUniform("col1").set(0F, 0F, 0F);
        Shaders.REVEAL_SHADER.safeGetUniform("col2").set(0F, 0F, 0F);


        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        builder.addVertex(mat, x, y, 0).setUv(0, 0);
        builder.addVertex(mat, x + sizeX, y, 0).setUv(1, 0);
        builder.addVertex(mat, x + sizeX, y + sizeY, 0).setUv(1, 1);
        builder.addVertex(mat, x, y + sizeY, 0).setUv(0, 1);
        builder.addVertex(mat, x, y + sizeY, 0).setUv(0, 1);
        builder.addVertex(mat, x + sizeX, y + sizeY, 0).setUv(1, 1);
        builder.addVertex(mat, x + sizeX, y, 0).setUv(1, 0);
        builder.addVertex(mat, x, y, 0).setUv(0, 0);


        BufferUploader.drawWithShader(builder.buildOrThrow());

        RenderSystem.disableBlend();
    }
}
