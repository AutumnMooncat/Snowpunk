package Snowpunk.cards;

import Snowpunk.cardmods.FlaminMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Scald extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Scald.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    public Scald() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 2;
        baseMagicNumber = magicNumber = 2;
        CardTemperatureFields.addInherentHeat(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.applyToEnemy(m, new SingePower(m, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(1);
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
    }
}