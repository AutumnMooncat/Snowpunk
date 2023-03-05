package Snowpunk.powers;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class TheSnowmanPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(TheSnowmanPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public TheSnowmanPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("hymn");
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0; i < amount; i++) {
            Wiz.atb(new ApplyCardModifierAction(Wiz.adp().hand.getRandomCard(true), new HatMod()));
        }
        flash();
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
