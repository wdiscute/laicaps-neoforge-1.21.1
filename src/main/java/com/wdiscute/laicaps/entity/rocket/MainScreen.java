package com.wdiscute.laicaps.entity.rocket;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.wdiscute.laicaps.util.AdvHelper;
import com.wdiscute.laicaps.Laicaps;
import com.wdiscute.laicaps.ModItems;
import com.wdiscute.laicaps.network.Payloads;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class MainScreen extends Screen
{

    private static final Logger log = LoggerFactory.getLogger(MainScreen.class);

    private static final ResourceLocation INV_AND_BORDER_BACKGROUND = Laicaps.rl("textures/gui/main_screen/inventory_overlay.png");
    private static final ResourceLocation PLANET_SCREEN_BACKGROUND = Laicaps.rl("textures/gui/main_screen/planet_screen_background.png");
    private static final ResourceLocation BLACK_OVERLAY = Laicaps.rl("textures/gui/telescope/black.png");

    private static final ResourceKey<Level> EMBER_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl("ember"));
    private static final ResourceKey<Level> ASHA_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl( "asha"));
    private static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));
    private static final ResourceKey<Level> LUNAMAR_KEY = ResourceKey.create(Registries.DIMENSION, Laicaps.rl( "lunamar"));

    private static final ResourceLocation EMBER = Laicaps.rl("textures/gui/telescope/ember.png");
    private static final ResourceLocation EMBER_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/ember_highlighted.png");
    private static final ResourceLocation EMBER_BLUR = Laicaps.rl("textures/gui/telescope/ember_blur.png");
    private static final ResourceLocation EMBER_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/ember_blur_highlighted.png");
    private static final ResourceLocation ASHA = Laicaps.rl("textures/gui/telescope/asha.png");
    private static final ResourceLocation ASHA_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/asha_highlighted.png");
    private static final ResourceLocation ASHA_BLUR = Laicaps.rl("textures/gui/telescope/asha_blur.png");
    private static final ResourceLocation ASHA_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/asha_blur_highlighted.png");
    private static final ResourceLocation OVERWORLD = Laicaps.rl("textures/gui/telescope/overworld.png");
    private static final ResourceLocation OVERWORLD_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/overworld_highlighted.png");
    private static final ResourceLocation LUNAMAR = Laicaps.rl("textures/gui/telescope/lunamar.png");
    private static final ResourceLocation LUNAMAR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/lunamar_highlighted.png");
    private static final ResourceLocation LUNAMAR_BLUR = Laicaps.rl("textures/gui/telescope/lunamar_blur.png");
    private static final ResourceLocation LUNAMAR_BLUR_HIGHLIGHTED = Laicaps.rl("textures/gui/telescope/lunamar_blur_highlighted.png");

    private static final ResourceLocation EMBER_TO_EMBER = Laicaps.rl("textures/gui/main_screen/ember_to_ember.png");
    private static final ResourceLocation EMBER_TO_ASHA = Laicaps.rl("textures/gui/main_screen/ember_to_asha.png");
    private static final ResourceLocation EMBER_TO_OVERWORLD = Laicaps.rl("textures/gui/main_screen/ember_to_overworld.png");
    private static final ResourceLocation EMBER_TO_LUNAMAR = Laicaps.rl("textures/gui/main_screen/ember_to_lunamar.png");

    private static final ResourceLocation ASHA_TO_EMBER = Laicaps.rl("textures/gui/main_screen/asha_to_ember.png");
    private static final ResourceLocation ASHA_TO_ASHA = Laicaps.rl("textures/gui/main_screen/asha_to_asha.png");
    private static final ResourceLocation ASHA_TO_OVERWORLD = Laicaps.rl("textures/gui/main_screen/asha_to_overworld.png");
    private static final ResourceLocation ASHA_TO_LUNAMAR = Laicaps.rl("textures/gui/main_screen/asha_to_lunamar.png");

    private static final ResourceLocation OVERWORLD_TO_EMBER = Laicaps.rl("textures/gui/main_screen/overworld_to_ember.png");
    private static final ResourceLocation OVERWORLD_TO_ASHA = Laicaps.rl("textures/gui/main_screen/overworld_to_asha.png");
    private static final ResourceLocation OVERWORLD_TO_OVERWORLD = Laicaps.rl("textures/gui/main_screen/overworld_to_overworld.png");
    private static final ResourceLocation OVERWORLD_TO_LUNAMAR = Laicaps.rl("textures/gui/main_screen/overworld_to_lunamar.png");

    private static final ResourceLocation LUNAMAR_TO_EMBER = Laicaps.rl("textures/gui/main_screen/lunamar_to_ember.png");
    private static final ResourceLocation LUNAMAR_TO_ASHA = Laicaps.rl("textures/gui/main_screen/lunamar_to_asha.png");
    private static final ResourceLocation LUNAMAR_TO_OVERWORLD = Laicaps.rl("textures/gui/main_screen/lunamar_to_overworld.png");
    private static final ResourceLocation LUNAMAR_TO_LUNAMAR = Laicaps.rl("textures/gui/main_screen/lunamar_to_lunamar.png");

    private static final ResourceLocation UNKNOWN_TO_EMBER = Laicaps.rl("textures/gui/main_screen/unknown_to_ember.png");
    private static final ResourceLocation UNKNOWN_TO_ASHA = Laicaps.rl("textures/gui/main_screen/unknown_to_asha.png");
    private static final ResourceLocation UNKNOWN_TO_OVERWORLD = Laicaps.rl("textures/gui/main_screen/unknown_to_overworld.png");
    private static final ResourceLocation UNKNOWN_TO_LUNAMAR = Laicaps.rl("textures/gui/main_screen/unknown_to_lunamar.png");

    private final RE re;

    private ItemStack planet;

    int uiX;
    int uiY;

    int canvasX;
    int canvasY;

    int fuel = 0;

    boolean emberDiscovered;
    int emberEntries;
    boolean ashaDiscovered;
    int ashaEntries;
    boolean lunamarDiscovered;
    int lunamarEntries;

    int rocketState = 0;

    @Override
    protected void init()
    {
        super.init();

        uiX = (width - 512) / 2;
        uiY = (height - 256) / 2;
        canvasX = uiX + 178;
        canvasY = uiY + 3;
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        double x = mouseX - uiX;
        double y = mouseY - uiY;

        if(rocketState != 0) return false;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
        {
            PacketDistributor.sendToServer(new Payloads.ChangePlanetSelected(re.getStringUUID(), "ember"));
        }

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            PacketDistributor.sendToServer(new Payloads.ChangePlanetSelected(re.getStringUUID(), "asha"));

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            PacketDistributor.sendToServer(new Payloads.ChangePlanetSelected(re.getStringUUID(), "overworld"));

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            PacketDistributor.sendToServer(new Payloads.ChangePlanetSelected(re.getStringUUID(), "lunamar"));

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        if(re == null) return;

        planet = re.getEntityData().get(RE.PLANET_SELECTED);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BLACK_OVERLAY);



        //renders background
        renderImage(guiGraphics, PLANET_SCREEN_BACKGROUND);


        //render fuel
        fuel = re.getEntityData().get(RE.FUEL);

        float fuelPercentage = (((float) (fuel * 100) / 1300));
        float fuelPercentageOfBar = fuelPercentage * 130 / 100;

        guiGraphics.fill(uiX + 31, uiY + 102, uiX + ((int) (34 + fuelPercentageOfBar)), uiY + 115,-5020);





        checkMissingItemsMessage(guiGraphics);

        //render inventory overlay
        renderImage(guiGraphics, INV_AND_BORDER_BACKGROUND);

        //render planet selected arrow
        renderPlanetArrows(guiGraphics);

        //render planets with blurs & highlights
        {
            ResourceLocation rl;
            RenderSystem.enableBlend();

            //ember
            if (emberDiscovered)
                rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_HIGHLIGHTED : EMBER;
            else
                rl = (x > 250 && x < 283 && y > 170 && y < 200) ? EMBER_BLUR_HIGHLIGHTED : EMBER_BLUR;

            renderImage(guiGraphics, rl);


            //asha
            if (ashaDiscovered)
                rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_HIGHLIGHTED : ASHA;
            else
                rl = (x > 310 && x < 335 && y > 86 && y < 111) ? ASHA_BLUR_HIGHLIGHTED : ASHA_BLUR;

            renderImage(guiGraphics, rl);

            //overworld
            rl = (x > 392 && x < 429 && y > 121 && y < 160) ? OVERWORLD_HIGHLIGHTED : OVERWORLD;
            renderImage(guiGraphics, rl);

            //lunamar
            if (lunamarDiscovered)
                rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_HIGHLIGHTED : LUNAMAR;
            else
                rl = (x > 444 && x < 589 && y > 16 && y < 65) ? LUNAMAR_BLUR_HIGHLIGHTED : LUNAMAR_BLUR;

            renderImage(guiGraphics, rl);

            RenderSystem.disableBlend();
        }

        //render tooltip
        renderPlanetTooltip(guiGraphics, mouseX, mouseY);
    }



    private void checkMissingItemsMessage(@NotNull GuiGraphics guiGraphics)
    {

        ItemStack planet = re.getEntityData().get(RE.PLANET_SELECTED);

        //check knowledge
        boolean planetDiscovered = planet.is(
                ModItems.EMBER) && emberDiscovered ||
                planet.is(ModItems.ASHA) && ashaDiscovered ||
                planet.is(ModItems.OVERWORLD) ||
                planet.is(ModItems.LUNAMAR) && lunamarDiscovered;


        if (!planetDiscovered)
        {
            guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.main_screen.missing_knowledge"), uiX + 30, uiY + 70, 13186614, true);
            return;
        }

        //check fuel
        if (!getFuelRequired())
        {
            guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.main_screen.missing_fuel"), uiX + 30, uiY + 70, 13186614, true);
            return;
        }



        if (rocketState == 0)
        {
            guiGraphics.drawString(this.font, Component.translatable("gui.laicaps.main_screen.missing_nothing"), uiX + 30, uiY + 70, 13186614, true);
        }

    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey))
        {
            this.onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean getFuelRequired()
    {
        float fuelRequired = 0;
        boolean selectedPlanetHasAFuelConsumedSet = false;

        assert Minecraft.getInstance().player != null;

        if (Minecraft.getInstance().player.level().dimension() == EMBER_KEY)
        {
            if (planet.is(ModItems.EMBER)) fuelRequired = 120;
            if (planet.is(ModItems.ASHA)) fuelRequired = 490;
            if (planet.is(ModItems.OVERWORLD)) fuelRequired = 700;
            if (planet.is(ModItems.LUNAMAR)) fuelRequired = 1240;
            selectedPlanetHasAFuelConsumedSet = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == ASHA_KEY)
        {
            if (planet.is(ModItems.EMBER)) fuelRequired = 490;
            if (planet.is(ModItems.ASHA)) fuelRequired = 120;
            if (planet.is(ModItems.OVERWORLD)) fuelRequired = 330;
            if (planet.is(ModItems.LUNAMAR)) fuelRequired = 870;
            selectedPlanetHasAFuelConsumedSet = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == OVERWORLD_KEY)
        {
            if (planet.is(ModItems.EMBER)) fuelRequired = 790;
            if (planet.is(ModItems.ASHA)) fuelRequired = 330;
            if (planet.is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (planet.is(ModItems.LUNAMAR)) fuelRequired = 660;
            selectedPlanetHasAFuelConsumedSet = true;
        }

        if (Minecraft.getInstance().player.level().dimension() == LUNAMAR_KEY)
        {
            if (planet.is(ModItems.EMBER)) fuelRequired = 1240;
            if (planet.is(ModItems.ASHA)) fuelRequired = 870;
            if (planet.is(ModItems.OVERWORLD)) fuelRequired = 660;
            if (planet.is(ModItems.LUNAMAR)) fuelRequired = 120;
            selectedPlanetHasAFuelConsumedSet = true;
        }

        if (!selectedPlanetHasAFuelConsumedSet)
        {
            if (planet.is(ModItems.EMBER)) fuelRequired = 120;
            if (planet.is(ModItems.ASHA)) fuelRequired = 120;
            if (planet.is(ModItems.OVERWORLD)) fuelRequired = 120;
            if (planet.is(ModItems.LUNAMAR)) fuelRequired = 120;
        }

        return fuel > fuelRequired;

    }

    private void renderPlanetArrows(GuiGraphics guiGraphics)
    {
        if (Minecraft.getInstance().player.level().dimension() == EMBER_KEY)
        {
            if (planet.is(ModItems.EMBER)) renderImage(guiGraphics, EMBER_TO_EMBER);
            if (planet.is(ModItems.ASHA)) renderImage(guiGraphics, EMBER_TO_ASHA);
            if (planet.is(ModItems.OVERWORLD)) renderImage(guiGraphics, EMBER_TO_OVERWORLD);
            if (planet.is(ModItems.LUNAMAR)) renderImage(guiGraphics, EMBER_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == ASHA_KEY)
        {
            if (planet.is(ModItems.EMBER)) renderImage(guiGraphics, ASHA_TO_EMBER);
            if (planet.is(ModItems.ASHA)) renderImage(guiGraphics, ASHA_TO_ASHA);
            if (planet.is(ModItems.OVERWORLD)) renderImage(guiGraphics, ASHA_TO_OVERWORLD);
            if (planet.is(ModItems.LUNAMAR)) renderImage(guiGraphics, ASHA_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == OVERWORLD_KEY)
        {
            if (planet.is(ModItems.EMBER)) renderImage(guiGraphics, OVERWORLD_TO_EMBER);
            if (planet.is(ModItems.ASHA)) renderImage(guiGraphics, OVERWORLD_TO_ASHA);
            if (planet.is(ModItems.OVERWORLD)) renderImage(guiGraphics, OVERWORLD_TO_OVERWORLD);
            if (planet.is(ModItems.LUNAMAR)) renderImage(guiGraphics, OVERWORLD_TO_LUNAMAR);
            return;
        }

        if (Minecraft.getInstance().player.level().dimension() == LUNAMAR_KEY)
        {
            if (planet.is(ModItems.EMBER)) renderImage(guiGraphics, LUNAMAR_TO_EMBER);
            if (planet.is(ModItems.ASHA)) renderImage(guiGraphics, LUNAMAR_TO_ASHA);
            if (planet.is(ModItems.OVERWORLD)) renderImage(guiGraphics, LUNAMAR_TO_OVERWORLD);
            if (planet.is(ModItems.LUNAMAR)) renderImage(guiGraphics, LUNAMAR_TO_LUNAMAR);
            return;
        }

        if (planet.is(ModItems.EMBER)) renderImage(guiGraphics, UNKNOWN_TO_EMBER);
        if (planet.is(ModItems.ASHA)) renderImage(guiGraphics, UNKNOWN_TO_ASHA);
        if (planet.is(ModItems.OVERWORLD)) renderImage(guiGraphics, UNKNOWN_TO_OVERWORLD);
        if (planet.is(ModItems.LUNAMAR)) renderImage(guiGraphics, UNKNOWN_TO_LUNAMAR);

    }


    private void renderPlanetTooltip(GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        String planet = "";

        double x = mouseX - uiX;
        double y = mouseY - uiY;

        //ember
        if (x > 250 && x < 283 && y > 170 && y < 200)
            planet = emberDiscovered ? "ember" : "ember_blur";

        //asha
        if (x > 310 && x < 335 && y > 86 && y < 111)
            planet = ashaDiscovered ? "asha" : "asha_blur";

        //overworld
        if (x > 392 && x < 429 && y > 121 && y < 160)
            planet = "overworld";

        //lunamar
        if (x > 444 && x < 589 && y > 16 && y < 65)
            planet = lunamarDiscovered ? "lunamar" : "lunamar_blur";

        //return if mouse is not hovering a planet
        if (planet.isEmpty()) return;

        //add base tooltips for each planet
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            if (I18n.exists("gui.laicaps.main_screen.tooltip." + planet + "." + i))
            {
                list.add(Component.translatable("gui.laicaps.main_screen.tooltip." + planet + "." + i));
            } else
            {
                if (i == 0)
                    list.add(Component.literal("translation key missing! gui.laicaps.main_screen.tooltip." + planet + "." + i));
                if (i == 0) list.add(Component.literal("Report to @wdiscute on discord"));
                if (i == 0) list.add(Component.literal("or whoever is translating the mod! :3"));
                break;
            }
        }


        //add "there's more to research" and "Click to Select" for non-blurred
        if(false)
        {
            if (Objects.equals(planet, "ember"))
            {
                list.add(Component.translatable("gui.laicaps.main_screen.tooltip.generic.chart"));
                if (emberEntries < Laicaps.EMBER_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "asha"))
            {
                list.add(Component.translatable("gui.laicaps.main_screen.tooltip.generic.chart"));
                if (ashaEntries < Laicaps.ASHA_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }

            if (Objects.equals(planet, "overworld"))
            {
                list.add(Component.translatable("gui.laicaps.main_screen.tooltip.generic.chart"));
            }

            if (Objects.equals(planet, "lunamar"))
            {
                list.add(Component.translatable("gui.laicaps.main_screen.tooltip.generic.chart"));
                if (lunamarEntries < Laicaps.LUNAMAR_ENTRIES)
                    list.add(Component.translatable("gui.laicaps.telescope.tooltip.generic.research"));
            }
        }

        guiGraphics.renderComponentTooltip(this.font, list, ((int) mouseX), ((int) mouseY));
    }


    private void renderImage(GuiGraphics guiGraphics, ResourceLocation rl)
    {
        guiGraphics.blit(rl, uiX, uiY, 0, 0, 512, 256, 512, 256);
    }

    public MainScreen(RE re)
    {
        super(Component.empty());

        this.re = re;

        ClientAdvancements adv = Minecraft.getInstance().getConnection().getAdvancements();

        emberDiscovered = AdvHelper.hasAdvancement(adv, "ember_discovered");
        ashaDiscovered = AdvHelper.hasAdvancement(adv, "asha_discovered");
        lunamarDiscovered = AdvHelper.hasAdvancement(adv, "lunamar_discovered");

        emberEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "ember_entries");
        ashaEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "asha_entries");
        lunamarEntries = AdvHelper.getEntriesCompletedFromAdvancement(adv, "lunamar_entries");
    }


}
