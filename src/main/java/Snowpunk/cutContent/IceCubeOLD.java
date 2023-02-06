package Snowpunk.cutContent;

import Snowpunk.actions.ImmediateExhaustCardAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnTempChangeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.IceCubePower;
import Snowpunk.util.Wiz;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class IceCubeOLD extends AbstractMultiUpgradeCard implements OnTempChangeCard {
    public final static String ID = makeID(IceCubeOLD.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int ICE = 3;
    private static final int UP_ICE = 1;

    public IceCubeOLD() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = ICE;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IceCubePower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_ICE);
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

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgradeMagicNumber(UP_ICE));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
    }
}