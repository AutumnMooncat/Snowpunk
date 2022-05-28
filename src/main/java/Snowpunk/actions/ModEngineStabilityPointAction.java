package Snowpunk.actions;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.util.SteamEngine;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModEngineStabilityPointAction extends AbstractGameAction
{

    public ModEngineStabilityPointAction(int amount)
    {
        this.amount = amount;
    }

    @Override
    public void update()
    {
        SteamEngine.modifyStabilityPoint(amount);
        isDone = true;
    }
}
