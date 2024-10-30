package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Kindness extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Kindness.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int DMG = 14;


    public Kindness() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HealAction(m, p, magicNumber));
        Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> upgradeMagicNumber(2));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}