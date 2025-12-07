package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.BoostInfoAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.HiddenMagicNumberMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.HollyPatches;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class Carol extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Carol.class.getSimpleName());
    public static UIStrings carolStrings = CardCrawlGame.languagePack.getUIString(makeID("Carols"));
    public static String[] CAROLS = carolStrings.TEXT;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public Carol() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new GainHollyAction(6));
        carol();
//        addToBot(new ApplyCardModifierAction(this, new HiddenMagicNumberMod(secondMagic)));
        if (HollyPatches.Holly.amount > 0)
            Wiz.atb(new GainSnowballAction(magicNumber));
    }


    public static void carol() {
        Random random = new Random();
        int speech = 1 + random.nextInt(CAROLS.length - 2);
        Wiz.atb(new TalkAction(true, CAROLS[speech], 2, 2));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Carol();
    }
}