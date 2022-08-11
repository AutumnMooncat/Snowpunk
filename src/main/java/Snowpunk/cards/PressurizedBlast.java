package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;
import static java.lang.Math.signum;

public class PressurizedBlast extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(PressurizedBlast.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 2, UP_DMG = 1, TIMES = 3, UP_TIMES = 1;

    public PressurizedBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = TIMES;
        CardModifierManager.addModifier(this, new WhistolMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            dmg(m, (CardTemperatureFields.getCardHeat(this) > 0 ? AbstractGameAction.AttackEffect.FIRE : AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_TIMES), 0, 1);
    }
}