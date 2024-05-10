package Snowpunk;

import Snowpunk.augments.AugmentHelper;
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
import Snowpunk.util.KeywordManager;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.mod.stslib.patches.cardInterfaces.MultiUpgradePatches;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class SnowpunkMod implements
        EditCardsSubscriber,
        EditKeywordsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber, AddAudioSubscriber, OnPlayerTurnStartSubscriber, OnStartBattleSubscriber {

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

    public static final ArrayList<CoreCard> cores = new ArrayList<>();


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

    public static void initialize() {
        SnowpunkMod thismod = new SnowpunkMod();
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
        String curPath = "eng";
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + curPath + "/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + curPath + "/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + curPath + "/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + curPath + "/Powerstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + curPath + "/PartAndCorestrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + curPath + "/UIstrings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + curPath + "/Potionstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                switch (keyword.ID) {
                    case "hot":
                        KeywordManager.HOT = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "cold":
                        KeywordManager.COLD = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "snowball":
                        KeywordManager.SNOW = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "fireball":
                        KeywordManager.FIRE = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "hat":
                        KeywordManager.HAT = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "plating":
                        KeywordManager.PLATE = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "condensed":
                        KeywordManager.COND = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "gear":
                        KeywordManager.GEAR = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "overdrive":
                        KeywordManager.OVER = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "holly":
                        KeywordManager.HOLLY = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "brass":
                        KeywordManager.BRASS = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
                        break;
                    case "flamin":
                        KeywordManager.FLAMIN = modID.toLowerCase() + ":" + keyword.ID.toLowerCase();
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
        BaseMod.addAudio("snowpunk:masterpiece", modID + "Resources/audio/masterpiece.mp3");
    }

    @Override
    public void receivePostInitialize() {
        if (Loader.isModLoaded("CardAugments")) {
            AugmentHelper.register();
        }
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
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        SnowballPatches.Snowballs.startTurn();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        SnowballPatches.Snowballs.setSnow(0);
    }
}
