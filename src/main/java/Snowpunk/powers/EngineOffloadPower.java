package Snowpunk.powers;

import Snowpunk.actions.CondenseRandomCardToDrawPileAction;
import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class EngineOffloadPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(EngineOffloadPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public EngineOffloadPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        Wiz.atb(new ModEngineTempAction(true));
        Wiz.atb(new CondenseRandomCardToDrawPileAction(EvaporatePanel.evaporatePile.size() + 2));
    }
}
