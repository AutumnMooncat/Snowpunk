package Snowpunk.cards.cardvars;

import Snowpunk.powers.interfaces.ModifyPressurePower;
import Snowpunk.util.Wiz;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Pressure extends DynamicVariable {

    @Override
    public String key() {
        return makeID("P");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return value(card) != baseValue(card);
    }

    @Override
    public int value(AbstractCard card) {
        return getPressure();
    }

    @Override
    public int baseValue(AbstractCard card) {
        return EnergyPanel.getCurrentEnergy();
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }

    public static int getPressure() {
        int p = EnergyPanel.getCurrentEnergy();
        if (Wiz.adp() != null) {
            for (AbstractRelic r : Wiz.adp().relics) {
                if (r instanceof ModifyPressurePower) {
                    p = ((ModifyPressurePower) r).modifyPressure(p);
                }
            }
        }
        return p;
    }
}