package Snowpunk.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Pressure extends DynamicVariable {

    @Override
    public String key() {
        return makeID("P");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return EnergyPanel.getCurrentEnergy();
    }

    @Override
    public int baseValue(AbstractCard card) {
        return EnergyPanel.getCurrentEnergy();
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}