package Snowpunk.actions;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class GainBrassFromSnowAction extends AbstractGameAction {

    int mult;

    public GainBrassFromSnowAction(int mult) {
        this.mult = mult;
    }

    @Override
    public void update() {
        int snow = SnowballPatches.Snowballs.getEffectiveAmount();
        if (snow > 0)
            Wiz.atb(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new BrassPower(Wiz.adp(), mult * snow)));

        isDone = true;
    }
}
