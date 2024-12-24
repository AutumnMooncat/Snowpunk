package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.InHandClankReaction;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Generator extends AbstractMultiUpgradeCard implements InHandClankReaction {
    public final static String ID = makeID(Generator.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;

    public Generator() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 1;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;// 39
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean willBlockClank(AbstractCard card) {
        return false;
    }

    @Override
    public void postClank(AbstractCard card) {
        Wiz.atb(new DiscardSpecificCardAction(this));
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.atb(new GainEnergyAction(secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeSecondMagic(1));
        addUpgradeData(() -> {
            selfRetain = true;
            uDesc();
        });
    }
}