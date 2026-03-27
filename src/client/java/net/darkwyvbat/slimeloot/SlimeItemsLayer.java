package net.darkwyvbat.slimeloot;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import org.joml.Quaternionf;

import java.util.List;

public class SlimeItemsLayer extends RenderLayer<SlimeRenderState, SlimeModel> {

    public SlimeItemsLayer(RenderLayerParent<SlimeRenderState, SlimeModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, SlimeRenderState state, float yRot, float xRot) {
        if (!(state instanceof SlimeRenderStateExtension extension)) return;
        List<ItemStackRenderState> items = extension.slimeloot_getItemStates();
        if (items.isEmpty()) return;

        long seed = extension.slimeloot_getItemSeed();
        int[] faces = {0, 1, 2, 3, 4, 5};
        RandomSource sideRandom = RandomSource.create(seed);
        for (int i = 5; i > 0; i--) {
            int j = sideRandom.nextInt(i + 1);
            int temp = faces[i];
            faces[i] = faces[j];
            faces[j] = temp;
        }
        int size = Math.max(1, state.size);
        float scale = (0.9F + (size - 1) * 0.2F) / size, r = 0.24F;
        for (int i = 0; i < items.size(); i++) {
            ItemStackRenderState itemState = items.get(i);
            if (itemState.isEmpty()) continue;
            RandomSource random = RandomSource.create(RandomSupport.mixStafford13(seed + i));
            float u = (random.nextFloat() - 0.5F) * r * 1.6F, v = (random.nextFloat() - 0.5F) * r * 1.6F;
            float nx = 0, ny = 0, nz = 0, px = 0, py = 0, pz = 0;
            switch (faces[i % 6]) {
                case 0 -> {
                    ny = 1;
                    py = r;
                    px = u;
                    pz = v;
                }
                case 1 -> {
                    ny = -1;
                    py = -r;
                    px = u;
                    pz = v;
                }
                case 2 -> {
                    nx = 1;
                    px = r;
                    py = u;
                    pz = v;
                }
                case 3 -> {
                    nx = -1;
                    px = -r;
                    py = u;
                    pz = v;
                }
                case 4 -> {
                    nz = 1;
                    pz = r;
                    px = u;
                    py = v;
                }
                case 5 -> {
                    nz = -1;
                    pz = -r;
                    px = u;
                    py = v;
                }
            }
            poseStack.pushPose();
            poseStack.translate(px, 1.25F + py, pz);
            poseStack.mulPose(new Quaternionf().rotationTo(0, 0, 1, nx, ny, nz));
            poseStack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360F));
            poseStack.mulPose(Axis.XP.rotationDegrees((random.nextFloat() - 0.5F) * 30F));
            poseStack.mulPose(Axis.YP.rotationDegrees((random.nextFloat() - 0.5F) * 30F));
            poseStack.scale(scale, scale, scale);
            itemState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
    }
}