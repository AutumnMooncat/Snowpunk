package Snowpunk.cards;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TheCryogenizer extends AbstractEasyCard {
    public final static String ID = makeID("TheCryogenizer");

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 50;
    private static final int MIN_HEAT = 1;
    private static final int UP_MIN_HEAT = 0;

    public TheCryogenizer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseInfo = info = 2;
        CardModifierManager.addModifier(this, new TemperatureMod(true, -2));
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        } else {
            return info == 0;
        }
    }

    private void updateInfo() {
        int heat = TemperatureMod.getCardHeat(this);
        if (heat < (upgraded ? UP_MIN_HEAT : MIN_HEAT)) {
            info = upgraded ? 1 : 2;
        } else {
            info = 0;
        }
        isInfoModified = info != baseInfo;
    }

    public void upp() {
        updateInfo();
        initializeDescription();
        //CardModifierManager.addModifier(this, new TemperatureMod(true, 1));
    }
}