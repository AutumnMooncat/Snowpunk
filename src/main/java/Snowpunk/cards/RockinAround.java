package Snowpunk.cards;

import Snowpunk.actions.AddHatsToRandomCardsAction;
import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class RockinAround extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(RockinAround.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;
    private static final int DMG = 9;
    private static final int UP_DMG = 7;

    public RockinAround() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new ApplyCardModifierAction(Wiz.adp().hand, new HatMod()));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(3));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}