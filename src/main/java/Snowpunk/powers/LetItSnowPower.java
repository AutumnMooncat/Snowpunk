package Snowpunk.powers;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.interfaces.OnUseSnowPower;
import Snowpunk.util.Wiz;
import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class LetItSnowPower extends AbstractEasyPower implements OnUseSnowPower {
    public static String POWER_ID = makeID(LetItSnowPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public LetItSnowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("tools");
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LetItSnowPower(owner, amount);
    }

    @Override
    public void onUseSnowball(int snow) {
        Wiz.atb(new DrawCardAction(snow * amount));
    }
}
