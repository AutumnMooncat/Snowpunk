package Snowpunk;

import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.cards.cardvars.Info;
import Snowpunk.cards.cardvars.SecondBlock;
import Snowpunk.cards.cardvars.SecondDamage;
import Snowpunk.cards.cardvars.SecondMagicNumber;
import Snowpunk.icons.IconContainer;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.potions.BottledInspiration;
import Snowpunk.potions.IceblastTonic;
import Snowpunk.potions.SteamfogBrew;
import Snowpunk.relics.AbstractEasyRelic;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import basemod.*;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.MultiUpgradePatches;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class SnowpunkMod implements
        EditCardsSubscriber,
        EditKeywordsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        StartGameSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber,
        OnPlayerTurnStartSubscriber,
        OnStartBattleSubscriber,
        PostUpdateSubscriber {

    public static final String modID = "Snowpunk";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static final Color SNOWY_BLUE = CardHelper.getColor(189, 237, 253);

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/bg_attack_ice_painterly.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/bg_attack_ice_painterly.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/bg_attack_ice_painterly.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/bg_attack_ice_painterly.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/bg_attack_ice_painterly.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/bg_attack_ice_painterly.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    public static final String CARD_SNOW_S = modID + "Resources/images/512/energy_snow.png";
    public static final String CARD_SNOW_L = modID + "Resources/images/1024/snowball.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";

    public static final String ENABLE_CARD_BATTLE_TALK_SETTING = "enableCardBattleTalk";
    public static boolean enableCardBattleTalkEffect = false;

    public static final String CARD_BATTLE_TALK_PROBABILITY_SETTING = "cardTalkProbability";
    public static int cardTalkProbability = 10; //Out of 100

    public static final String ENABLE_DAMAGED_BATTLE_TALK_SETTING = "enableDamagedBattleTalk";
    public static boolean enableDamagedBattleTalkEffect = false;

    public static final String DAMAGED_BATTLE_TALK_PROBABILITY_SETTING = "damagedTalkProbability";
    public static int damagedTalkProbability = 20; //Out of 100

    public static final String ENABLE_PRE_BATTLE_TALK_SETTING = "enablePreBattleTalk";
    public static boolean enablePreBattleTalkEffect = false;

    public static final String PRE_BATTLE_TALK_PROBABILITY_SETTING = "preTalkProbability";
    public static int preTalkProbability = 50; //Out of 100

    public static final Color BOTTLED_INSPIRATION_LIQUID = CardHelper.getColor(240, 240, 150);
    public static final Color BOTTLED_INSPIRATION_HYBRID = CardHelper.getColor(230, 200, 50);
    public static final Color BOTTLED_INSPIRATION_SPOTS = CardHelper.getColor(250, 250, 250);

    public static final Color ICEBLAST_TONIC_LIQUID = CardHelper.getColor(130, 180, 230);
    public static final Color ICEBLAST_TONIC_HYBRID = CardHelper.getColor(170, 220, 250);

    public static final Color STEAMFOG_BREW_LIQUID = CardHelper.getColor(150, 150, 180);
    public static final Color STEAMFOG_BREW_HYBRID = CardHelper.getColor(80, 90, 130);
    public static final Color STEAMFOG_BREW_SPOTS = CardHelper.getColor(180, 180, 180);

    public static SpireConfig config = null;
    public static int sfx = 1;
    public static boolean drawHot, conductoMode, altForge, singeHP;
    public static final ArrayList<CoreCard> cores = new ArrayList<>();
    public static String lang = "eng";


    public SnowpunkMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheConductor.Enums.SNOWY_BLUE_COLOR, SNOWY_BLUE, SNOWY_BLUE, SNOWY_BLUE,
                SNOWY_BLUE, SNOWY_BLUE, SNOWY_BLUE, SNOWY_BLUE,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static String makeShaderPath(String resourcePath) {
        return modID + "Resources/shaders/" + resourcePath;
    }

    public static void initialize() {
        SnowpunkMod thismod = new SnowpunkMod();
        try {
            Properties defaults = new Properties();
            defaults.put("EvaporateTutorial", Boolean.toString(false));
            defaults.put("drawHot", Boolean.toString(true));
            defaults.put("conductoMode", Boolean.toString(false));
            defaults.put("altForge", Boolean.toString(false));
            defaults.put("singeHP", Boolean.toString(true));
            defaults.setProperty("sfx", "2");
            config = new SpireConfig("TheConductor", "config", defaults);
            sfx = config.getInt("sfx");
            drawHot = config.getBool("drawHot");
            conductoMode = config.getBool("conductoMode");
            altForge = config.getBool("altForge");
            singeHP = config.getBool("singeHP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheConductor(TheConductor.characterStrings.NAMES[1], TheConductor.Enums.THE_CONDUCTOR),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheConductor.Enums.THE_CONDUCTOR);

        receiveEditPotions();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(IconContainer.GearIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.SnowIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.TempIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.FireIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.HollyIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.ColdIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.HotIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.OverIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.PlateIcon.get());
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new SecondBlock());
        BaseMod.addDynamicVariable(new Info());
        new AutoAdd(modID)
                .packageFilter("Snowpunk.cards")
                .setDefaultSeen(true)
                .cards();

        new AutoAdd(modID)
                .packageFilter("Snowpunk.cards")
                .any(CoreCard.class, (info, coreCard) -> cores.add(coreCard));
    }


    public void receiveEditPotions() {
        BaseMod.addPotion(BottledInspiration.class, BOTTLED_INSPIRATION_LIQUID, BOTTLED_INSPIRATION_HYBRID, BOTTLED_INSPIRATION_HYBRID, BottledInspiration.POTION_ID, TheConductor.Enums.THE_CONDUCTOR);
        BaseMod.addPotion(SteamfogBrew.class, STEAMFOG_BREW_LIQUID, STEAMFOG_BREW_HYBRID, STEAMFOG_BREW_HYBRID, SteamfogBrew.POTION_ID, TheConductor.Enums.THE_CONDUCTOR);
        BaseMod.addPotion(IceblastTonic.class, ICEBLAST_TONIC_LIQUID, ICEBLAST_TONIC_HYBRID, null, IceblastTonic.POTION_ID, TheConductor.Enums.THE_CONDUCTOR);
    }

    @Override
    public void receiveEditStrings() {
        loadStrings("eng");
        if (Settings.language != Settings.GameLanguage.ENG) {
            try {
                lang = Settings.language.toString().toLowerCase();
                loadStrings(Settings.language.toString().toLowerCase());
            } catch (GdxRuntimeException er) {
                System.out.println("Vacant: Adding keywords error: Language not found, defaulted to eng.");
                lang = "eng";
            }
        }
    }

    private void loadStrings(String curPath) {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + curPath + "/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + curPath + "/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + curPath + "/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + curPath + "/Powerstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + curPath + "/PartAndCorestrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + curPath + "/UIstrings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + curPath + "/Potionstrings.json");

        BaseMod.loadCustomStringsFile(TutorialStrings.class, modID + "Resources/localization/" + curPath + "/Potionstrings.json");
    }


    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + lang + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                switch (keyword.ID) {
                    case "hot":
                        KeywordManager.HOT = keyword.NAMES[0].toLowerCase();
                        break;
                    case "cold":
                        KeywordManager.COLD = keyword.NAMES[0].toLowerCase();
                        break;
                    case "snowball":
                        KeywordManager.SNOW = keyword.NAMES[0].toLowerCase();
                        break;
                    case "fireball":
                        KeywordManager.FIRE = keyword.NAMES[0].toLowerCase();
                        break;
                    case "hat":
                        KeywordManager.HAT = keyword.NAMES[0].toLowerCase();
                        break;
                    case "plating":
                        KeywordManager.PLATE = keyword.NAMES[0].toLowerCase();
                        break;
                    case "gear":
                        KeywordManager.GEAR = keyword.NAMES[0].toLowerCase();
                        break;
                    case "holly":
                        KeywordManager.HOLLY = keyword.NAMES[0].toLowerCase();
                        break;
                    case "brass":
                        KeywordManager.BRASS = keyword.NAMES[0].toLowerCase();
                        break;
                    case "singe":
                        KeywordManager.SINGE = keyword.NAMES[0].toLowerCase();
                        break;
                    case "chill":
                        KeywordManager.CHILL = keyword.NAMES[0].toLowerCase();
                        break;
                }
            }
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("snowpunk:tick", modID + "Resources/audio/tick.wav");
        BaseMod.addAudio("snowpunk:tock", modID + "Resources/audio/tock.wav");
        BaseMod.addAudio("snowpunk:FIVEGOLDENRINGS", modID + "Resources/audio/FIVEGOLDENRINGS.wav");
        BaseMod.addAudio("snowpunk:boing", modID + "Resources/audio/boing.mp3");
        BaseMod.addAudio("snowpunk:screm", modID + "Resources/audio/screm.wav");
        BaseMod.addAudio("snowpunk:clank", modID + "Resources/audio/clank.mp3");
        BaseMod.addAudio("snowpunk:unclank", modID + "Resources/audio/clank_prevented.mp3");
        BaseMod.addAudio("snowpunk:wrench", modID + "Resources/audio/wrench.mp3");
        BaseMod.addAudio("snowpunk:bonk", modID + "Resources/audio/bonk.mp3");
        BaseMod.addAudio("snowpunk:snow1", modID + "Resources/audio/snow1.wav");
        BaseMod.addAudio("snowpunk:snow2", modID + "Resources/audio/snow2.wav");
        BaseMod.addAudio("snowpunk:snow3", modID + "Resources/audio/snow3.wav");
        BaseMod.addAudio("snowpunk:snow4", modID + "Resources/audio/snow4.wav");
        BaseMod.addAudio("snowpunk:holly", modID + "Resources/audio/holly.wav");
        BaseMod.addAudio("snowpunk:choochoo", modID + "Resources/audio/CHOOCHOO.wav");
        BaseMod.addAudio("snowpunk:masterpiece", modID + "Resources/audio/masterpiece.mp3");
    }

    @Override
    public void receivePostInitialize() {
//        if (Loader.isModLoaded("CardAugments")) {
//            AugmentHelper.register();
//        }
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Color.RED.cpy();

            @Override
            public boolean test(AbstractCard abstractCard) {
                return MultiUpgradePatches.MultiUpgradeFields.glowRed.get(abstractCard);
            }

            @Override
            public Color getColor(AbstractCard abstractCard) {
                return c;
            }

            @Override
            public String glowID() {
                return makeID("ExclusionGlow");
            }
        });

        DynamicTextBlocks.registerCustomCheck(makeID("CardTemp"), card -> CardTemperatureFields.getCardHeat(card));


        String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("Configs")).TEXT;
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabeledToggleButton(TEXT[3], 350, 700, Settings.CREAM_COLOR, FontHelper.charDescFont, config.getBool("drawHot"), settingsPanel, label -> {
        }, button -> {
            drawHot = button.enabled;
            config.setBool("drawHot", button.enabled);
            try {
                config.save();
            } catch (Exception e) {
            }
        }));
        settingsPanel.addUIElement(new ModLabel(TEXT[4], 350, 660, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, update -> {
        }));
        settingsPanel.addUIElement(new ModMinMaxSlider("", 400, 620, 1, 4, sfx, "%.0f", settingsPanel, slider -> {
            float sliderValue = (int) slider.getValue();
            sfx = Math.round(sliderValue);
            config.setString("sfx", Integer.toString(sfx));
            try {
                config.save();
            } catch (Exception e) {
            }
        }));
        settingsPanel.addUIElement(new ModLabeledToggleButton(TEXT[5], 350, 565, Settings.CREAM_COLOR, FontHelper.charDescFont, config.getBool("conductoMode"), settingsPanel, label -> {
        }, button -> {
            conductoMode = button.enabled;
            config.setBool("conductoMode", button.enabled);
            try {
                config.save();
            } catch (Exception e) {
            }
        }));
        settingsPanel.addUIElement(new ModLabeledToggleButton(TEXT[6], 350, 520, Settings.CREAM_COLOR, FontHelper.charDescFont, config.getBool("altForge"), settingsPanel, label -> {
        }, button -> {
            altForge = button.enabled;
            config.setBool("altForge", button.enabled);
            try {
                config.save();
            } catch (Exception e) {
            }
        }));
        settingsPanel.addUIElement(new ModLabeledToggleButton(TEXT[7], 350, 475, Settings.CREAM_COLOR, FontHelper.charDescFont, config.getBool("singeHP"), settingsPanel, label -> {
        }, button -> {
            singeHP = button.enabled;
            config.setBool("singeHP", button.enabled);
            try {
                config.save();
            } catch (Exception e) {
            }
        }));
        BaseMod.registerModBadge(TexLoader.getTexture(makeImagePath("ui/badge.png")), TEXT[0], TEXT[1], TEXT[2], settingsPanel);

//        if (ModManager.isChimeraLoaded)
//            CardAugmentsLoader.load();
    }

    public static int getSFXFrequency() {
        switch (sfx) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 5;
            default:
                return -1;
        }
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        SnowballPatches.Snowballs.startTurn();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        SnowballPatches.Snowballs.setSnow(0);
    }

    @Override
    public void receiveStartGame() {
        EvaporatePanel.evaporatePile.clear();
    }

    public static float time = 0f;
    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getRawDeltaTime();
    }

}
