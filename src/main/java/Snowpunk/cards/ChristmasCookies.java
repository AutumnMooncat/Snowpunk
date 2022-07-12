package Snowpunk.cards;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookies extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChristmasCookies.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0, MAGIC = 4, UP_MAGIC = 4, UP_HEAL = 2;

    public ChristmasCookies() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        secondMagic = baseSecondMagic = MAGIC;
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HolidayCheerUpAction(magicNumber));
        Wiz.atb(new HealAction(p, p, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(this, () -> upgradeSecondMagic(UP_HEAL));
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
    }
}