package com.wdiscute.laicaps.entity.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.entity.ModBoatEntity;
import com.wdiscute.laicaps.entity.ModChestBoatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

public class ModBoatRenderer extends BoatRenderer
{
    private final Map<ModBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public ModBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
        this.boatResources = Stream.of(ModBoatEntity.Type.values()).collect(ImmutableMap.toImmutableMap(type -> type,
                type -> Pair.of(ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, getTextureLocation(type, pChestBoat)), this.createBoatModel(pContext, type, pChestBoat))));
    }

    private static String getTextureLocation(ModBoatEntity.Type pType, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png" : "textures/entity/boat/" + pType.getName() + ".png";
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, ModBoatEntity.Type type, boolean chestBoat) {

        System.out.println("0000000000000000000000000000000");
        System.out.println("context " + context);
        System.out.println("type " + type);
        System.out.println("chestBoat " + chestBoat);
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, type.getName());

        ModelLayerLocation modellayerlocation = chestBoat ? new ModelLayerLocation(location.withPrefix("chest_boat/"), "main") : new ModelLayerLocation(location.withPrefix("boat/"), "main");
        ModelPart modelpart = context.bakeLayer(modellayerlocation);
        System.out.println("modellayerlocation " + modellayerlocation);
        System.out.println("modelpart " + modelpart);
        System.out.println("0000000000000000000000000000000");

        return chestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);

    }

    public static ModelLayerLocation createBoatModelName(ModBoatEntity.Type pType) {
        return createLocation("boat/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(ModBoatEntity.Type pType) {
        return createLocation("chest_boat/" + pType.getName(), "main");
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Laicaps.MOD_ID, pPath), pModel);
    }


    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if(boat instanceof ModBoatEntity modBoat) {
            return this.boatResources.get(modBoat.getModVariant());
        } else if(boat instanceof ModChestBoatEntity modChestBoatEntity) {
            return this.boatResources.get(modChestBoatEntity.getModVariant());
        } else {
            return null;
        }
    }

}
