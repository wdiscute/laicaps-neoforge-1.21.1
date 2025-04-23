package com.wdiscute.laicaps.block.telescope;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.List;

public class RevealRenderUtil
{

    public static void renderWithOcclusion(PoseStack matrices, float startPosX, float startPosY, float sizeX, float sizeY, List<Vector2f> pointsToOcclude, List<Float> radiusesOfEachOcclusion) {

        //most of the code shamelessly stolen from RELICS
        //https://github.com/Octo-Studios/relics
        //I wish I were smart enough to understand it

        RenderSystem.enableBlend();
        float time = 0;
        float[] arr = new float[256];
        float[] radiuses = new float[128];
        float[] noiseSpreadsArr = new float[128];

        for (int i = 0; i < arr.length; i += 2) {

            float lmx;
            float lmy;

            if (i / 2 < pointsToOcclude.size()) {
                Vector2f v = pointsToOcclude.get(i / 2);
                lmx = (v.x - startPosX) / sizeX;
                lmy = (v.y - startPosY) / sizeY;

                radiuses[i / 2] = radiusesOfEachOcclusion.get(i / 2);
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

        builder.addVertex(mat, startPosX, startPosY, 0).setUv(0, 0);
        builder.addVertex(mat, startPosX + sizeX, startPosY, 0).setUv(1, 0);
        builder.addVertex(mat, startPosX + sizeX, startPosY + sizeY, 0).setUv(1, 1);
        builder.addVertex(mat, startPosX, startPosY + sizeY, 0).setUv(0, 1);
        builder.addVertex(mat, startPosX, startPosY + sizeY, 0).setUv(0, 1);
        builder.addVertex(mat, startPosX + sizeX, startPosY + sizeY, 0).setUv(1, 1);
        builder.addVertex(mat, startPosX + sizeX, startPosY, 0).setUv(1, 0);
        builder.addVertex(mat, startPosX, startPosY, 0).setUv(0, 0);


        BufferUploader.drawWithShader(builder.buildOrThrow());

        RenderSystem.disableBlend();
    }
}
