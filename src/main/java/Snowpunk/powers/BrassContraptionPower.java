package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassContraptionPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(BrassContraptionPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private int cardsDrawnThisTurn = 0;

    public BrassContraptionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }

    @Override
    public void atStartOfTurn() {
        cardsDrawnThisTurn = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.canUpgrade() && cardsDrawnThisTurn < amount) {
            cardsDrawnThisTurn++;
            card.upgrade();
            card.applyPowers();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
