package com.bernardo.dragonblockascension.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SayajinModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public static final Identifier TEXTURE = new Identifier("dragonblockascension",
            "textures/ecc/sayajin.png");

    private static final float UV_SCALE = 512.0f / 64.0f;

    public SayajinModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        int tw = 512;
        int th = 512;

        root.addChild("head",
            ModelPartBuilder.create()
                .uv((int)(0 * UV_SCALE), (int)(0 * UV_SCALE))
                .cuboid(-4, -8, -4, 8, 8, 8, new Dilation(0.5f)),
            ModelTransform.pivot(0, 0, 0));

        root.addChild("hat",
            ModelPartBuilder.create()
                .uv((int)(32 * UV_SCALE), (int)(0 * UV_SCALE))
                .cuboid(-4, -8, -4, 8, 8, 8, new Dilation(0.55f)),
            ModelTransform.pivot(0, 0, 0));

        root.addChild("body",
            ModelPartBuilder.create()
                .uv((int)(16 * UV_SCALE), (int)(16 * UV_SCALE))
                .cuboid(-4, 0, -2, 8, 12, 4, new Dilation(0.25f)),
            ModelTransform.pivot(0, 0, 0));

        root.addChild("right_arm",
            ModelPartBuilder.create()
                .uv((int)(40 * UV_SCALE), (int)(16 * UV_SCALE))
                .cuboid(-3, -2, -2, 4, 12, 4, new Dilation(0.25f)),
            ModelTransform.pivot(-5, 2, 0));

        root.addChild("left_arm",
            ModelPartBuilder.create()
                .uv((int)(32 * UV_SCALE), (int)(48 * UV_SCALE))
                .cuboid(-1, -2, -2, 4, 12, 4, new Dilation(0.25f)),
            ModelTransform.pivot(5, 2, 0));

        root.addChild("right_leg",
            ModelPartBuilder.create()
                .uv((int)(0 * UV_SCALE), (int)(16 * UV_SCALE))
                .cuboid(-2, 0, -2, 4, 12, 4, new Dilation(0.25f)),
            ModelTransform.pivot(-1.9f, 12, 0));

        root.addChild("left_leg",
            ModelPartBuilder.create()
                .uv((int)(16 * UV_SCALE), (int)(48 * UV_SCALE))
                .cuboid(-2, 0, -2, 4, 12, 4, new Dilation(0.25f)),
            ModelTransform.pivot(1.9f, 12, 0));

        return TexturedModelData.of(modelData, tw, th);
    }

    public static SayajinModel<?> createModel() {
        TexturedModelData tmd = getTexturedModelData();
        return new SayajinModel<>(tmd.createModel());
    }
}
