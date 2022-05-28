package Snowpunk.cards;

import Snowpunk.actions.ImmediateExhaustCardAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.OnTempChangeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCube extends AbstractEasyCard implements OnTempChangeCard {
    public final static String ID = makeID("IceCube");

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int SNOW = 1;
    private static final int UP_SNOW = 1;

    public IceCube() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SNOW;
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SnowballPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_SNOW);
    }

    @Override
    public void onTempChange(int change) {
        if (change > 0) {
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