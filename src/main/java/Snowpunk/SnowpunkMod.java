package Snowpunk;

import Snowpunk.augments.AugmentHelper;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.cards.cardvars.Info;
import Snowpunk.cards.cardvars.SecondBlock;
import Snowpunk.cards.cardvars.SecondDamage;
import Snowpunk.cards.cardvars.SecondMagicNumber;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.icons.IconContainer;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.relics.AbstractEasyRelic;
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
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class SnowpunkMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber, AddAudioSubscriber {

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

    public static final ArrayList<AbstractPartCard> parts = new ArrayList<>();
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
                .any(AbstractPartCard.class, (info, abstractPartCard) -> parts.add(abstractPartCard));

        new AutoAdd(modID)
                .packageFilter("Snowpunk.cards")
                .any(CoreCard.class, (info, coreCard) -> cores.add(coreCard));
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/eng/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/eng/Powerstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/CardModstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Chatterstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/DamageModstrings.json");

        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/PartAndCorestrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/eng/UIstrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/eng/Augmentstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
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
}
