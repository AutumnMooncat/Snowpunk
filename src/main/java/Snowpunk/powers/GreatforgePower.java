package Snowpunk.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class GreatforgePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(GreatforgePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private int cardsDrawnThisTurn = 0;

    public GreatforgePower(AbstractCreature owner, int amount) {
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
            card.superFlash();
            card.applyPowers();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GreatforgePower(owner, amount);
    }
}
