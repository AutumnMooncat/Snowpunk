package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

public class BlackIce extends AbstractEasyCard {
    public final static String ID = makeID(BlackIce.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;

    public BlackIce() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() > 0) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    if (upgraded) {
                        Wiz.applyToEnemy(mo, new FrostbitePower(mo, p, getSnow()));
                    }
                    Wiz.applyToEnemy(mo, new WeakPower(mo, getSnow(), false));
                    Wiz.applyToEnemy(mo, new VulnerablePower(mo, getSnow(), false));
                }
            }
        }
        if (!this.freeToPlayOnce) {
            Wiz.atb(new RemoveSpecificPowerAction(p, p, SnowballPower.POWER_ID));
        }
    }

    public void upp() {
        uDesc();
    }
}