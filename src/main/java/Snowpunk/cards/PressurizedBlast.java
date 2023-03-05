package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;
import static java.lang.Math.signum;

public class PressurizedBlast extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(PressurizedBlast.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 0, DMG = 2, UP_DMG = 1, TIMES = 3, UP_TIMES = 1;

    public PressurizedBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = TIMES;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect attackEffect = (CardTemperatureFields.getCardHeat(this) > 0 ? AbstractGameAction.AttackEffect.FIRE : AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        for (int i = 0; i < magicNumber; i++)
            addToBot(new AttackDamageRandomEnemyAction(this, attackEffect));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_TIMES));
    }
}