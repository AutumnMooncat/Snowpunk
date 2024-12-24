package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.BoostInfoAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.HiddenMagicNumberMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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

    public Carol() {
        this(0);
    }

    public Carol(int numBoosted) {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 6;
        secondMagic = baseSecondMagic = 2;
        info = baseInfo = numBoosted;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new GainHollyAction(magicNumber));
        carol();
        addToBot(new ApplyCardModifierAction(this, new HiddenMagicNumberMod(secondMagic)));
    }


    public static void carol() {
        Random random = new Random();
        int speech = 1 + random.nextInt(CAROLS.length - 2);
        Wiz.atb(new TalkAction(true, CAROLS[speech], 2, 2));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Carol(info);
    }
}