package Snowpunk.powers;

import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class AshesToAshesPower extends AbstractEasyPower implements OnEvaporatePower {
    public static String POWER_ID = makeID(AshesToAshesPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    boolean evaporated;

    public AshesToAshesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        evaporated = false;
    }

    @Override
    public void atStartOfTurn() {
        evaporated = false;
    }

    @Override
    public void onEvaporate(AbstractCard card) {
        if (!evaporated) {
            flash();
            Wiz.applyToSelf(new FireballPower(owner, amount));
            evaporated = true;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }
}
