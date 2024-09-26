package Snowpunk.cards;

import Snowpunk.actions.UpgradeRandomInHardWithVisualAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Forge extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Forge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    public Forge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 2;
        magicNumber = baseMagicNumber = 1;
        CardTemperatureFields.addInherentHeat(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new UpgradeRandomInHardWithVisualAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}