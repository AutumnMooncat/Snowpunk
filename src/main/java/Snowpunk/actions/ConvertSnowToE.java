package Snowpunk.actions;

import Snowpunk.patches.SnowballPatches;
import Snowpunk.powers.interfaces.OnCondensePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.CondenseEffect;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.function.Predicate;

public class ConvertSnowToE extends AbstractGameAction {

    public ConvertSnowToE(int mult) {
        amount = mult;
    }

    @Override
    public void update() {
        int snow = SnowballPatches.Snowballs.getEffectiveAmount();
        Wiz.att(new GainEnergyAction(Math.max(0, snow * amount)));
        Wiz.att(new GainSnowballAction(-snow));
        isDone = true;
    }
}
