package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.BoostInfoAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.HiddenMagicNumberMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Carol extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Carol.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Carol() {
        this(0);
    }

    public Carol(int numBoosted) {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 5;
        secondMagic = baseSecondMagic = 2;
        info = baseInfo = numBoosted;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new GainHollyAction(magicNumber));
        addToBot(new ApplyCardModifierAction(this, new HiddenMagicNumberMod(secondMagic)));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeMagicNumber(1);
            upgradeSecondMagic(1);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Carol(info);
    }
}