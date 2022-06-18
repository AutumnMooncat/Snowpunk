package Snowpunk.cards;

import Snowpunk.actions.ImmediateExhaustCardAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.OnTempChangeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.IceCubePower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCube extends AbstractEasyCard implements OnTempChangeCard {
    public final static String ID = makeID(IceCube.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.POWER;

    private static final int COST = -1;
    private static final int BONUS = 1;
    private static final int UP_BONUS = 2;

    public IceCube() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BONUS;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IceCubePower(p, getSnow() + magicNumber));
        if (!this.freeToPlayOnce) {
            Wiz.atb(new RemoveSpecificPowerAction(p, p, SnowballPower.POWER_ID));
        }
    }

    public void upp() {
        upgradeMagicNumber(UP_BONUS);
    }

    @Override
    public void onTempChange(int change) {
        if (change > 0 && Wiz.adp() != null) {
            if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null) {
                if (Wiz.adp().cardInUse == this) {
                    for (AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                        if (a instanceof UseCardAction) {
                            if (ReflectionHacks.getPrivate(a, UseCardAction.class, "targetCard") == this) {
                                ((UseCardAction) a).exhaustCard = true;
                            }
                        }
                    }
                } else {
                    Wiz.att(new ImmediateExhaustCardAction(this));
                }
            }
        }
    }
}