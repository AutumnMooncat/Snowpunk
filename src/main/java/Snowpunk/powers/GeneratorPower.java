package Snowpunk.powers;

import Snowpunk.actions.UpgradeWithVisualAction;
import Snowpunk.powers.interfaces.OnClankPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class GeneratorPower extends AbstractEasyPower implements OnClankPower {
    public static String POWER_ID = makeID(GeneratorPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public GeneratorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (amount > 1)
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GeneratorPower(owner, amount);
    }

    @Override
    public void onClank(AbstractCard card) {
        if (amount > 0) {
            Wiz.atb(new DrawCardAction(1));
            Wiz.atb(new GainEnergyAction(1));
            Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
        }
    }
}
