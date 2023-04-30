package Snowpunk.cards;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TrainScythe extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TrainScythe.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 6, UP_DMG = 2;

    public TrainScythe() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        CardTemperatureFields.addInherentHeat(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new EverburnMod()));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        setDependencies(true, 2, 0, 1);
    }
}