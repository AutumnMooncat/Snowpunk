package Snowpunk.cards;

import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Holly extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Holly.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UP_MAGIC = 3;

    public Holly() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseInfo = info = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HolidayCheerUpAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(this, () -> innateUpgrade());
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new ReshuffleMod()));
    }

    private void innateUpgrade() {
        isInnate = true;
        upgradeInfo(1);
    }
}