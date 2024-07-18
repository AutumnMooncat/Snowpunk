package Snowpunk.patches;

import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.interfaces.OnTempChangeCard;
import Snowpunk.powers.CoolNextCardPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.FireburstPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardTemperatureFields {
    public static final Color HOT_TINT = new Color(1, 209 / 255f, 209 / 255f, 1);
    public static final Color COLD_TINT = new Color(209 / 255f, 253 / 255f, 1, 1);
    public static final Color STABLE_TINT = Color.WHITE.cpy();
    public static final Color OVERHEAT_TINT = new Color(1, 130 / 255f, 130 / 255f, 1);
    public static final Color FROZEN_TINT = new Color(130 / 255f, 251 / 255f, 1, 1);
    public static final int COLD = -1, HOT = 1;

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class TemperatureFields {
        public static SpireField<Integer> inherentHeat = new SpireField<>(() -> 0);
        public static SpireField<Integer> addedHeat = new SpireField<>(() -> 0);
    }

    public static int getCardHeat(AbstractCard card) {
        return TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card);
    }

    public static int getCardHeatFloored(AbstractCard card) {
        if (TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card) < 0)
            return COLD;
        if (TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card) > 0)
            return HOT;
        return 0;
    }

    public static int getExpectedCardHeatWhenPlayed(AbstractCard card) {
        int heat = getCardHeat(card);
        if (Wiz.adp() != null) {
            if (Wiz.adp().hasPower(FireballPower.POWER_ID) || Wiz.adp().hasPower(FireburstPower.POWER_ID)) {
                if (heat <= 0)
                    heat = HOT;
                else
                    heat++;

                if (Wiz.adp().hasPower(FireburstPower.POWER_ID))
                    heat++;
            }
            if (Wiz.adp().hasPower(CoolNextCardPower.POWER_ID)) {
                if (heat >= 0)
                    heat = COLD;
                else
                    heat--;
            }
        }
        /*
        if (heat > HOT)
            heat = HOT;

        if (heat < COLD)
            heat = COLD;*/

        return heat;
    }

    public static void reduceTemp(AbstractCard card) {
        int prevTotal = TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card);
        if (prevTotal > 0)
            TemperatureFields.addedHeat.set(card, TemperatureFields.addedHeat.get(card) - 1);
        if (prevTotal < 0)
            TemperatureFields.addedHeat.set(card, TemperatureFields.addedHeat.get(card) + 1);
    }

    public static void addInherentHeat(AbstractCard card, int amount) {
        if (amount == 0)
            return;
        addAndClampHeat(card, amount, true);
        CardModifierManager.addModifier(card, new TemperatureMod());
    }

    public static void addHeat(AbstractCard card, int amount) {
        if (amount == 0)
            return;
        addAndClampHeat(card, amount, false);
        CardModifierManager.addModifier(card, new TemperatureMod());
    }

    public static void setAddedHeat(AbstractCard card, int amount) {
        if (amount == 0)
            return;
        TemperatureFields.addedHeat.set(card, amount);
        CardModifierManager.addModifier(card, new TemperatureMod());
    }

    private static void addAndClampHeat(AbstractCard card, int amount, boolean addInherent) {
        int prevTotal = TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card);

        int heat = 0;
        if (amount > 0) {
            if (prevTotal <= 0)
                heat = amount;
            else
                heat = prevTotal + amount;
        } else if (amount < 0) {
            if (prevTotal > 0)
                heat = amount;
            else
                heat = prevTotal + amount;
        }

        if (addInherent) {
            TemperatureFields.inherentHeat.set(card, heat);
            TemperatureFields.addedHeat.set(card, 0);
        } else {
            TemperatureFields.addedHeat.set(card, heat - TemperatureFields.inherentHeat.get(card));
        }

        int inherent = TemperatureFields.inherentHeat.get(card);
        int added = TemperatureFields.addedHeat.get(card);

        if (CardModifierManager.hasModifier(card, EverburnMod.ID)) {
            if (inherent < HOT) {
                TemperatureFields.inherentHeat.set(card, HOT);
                inherent = TemperatureFields.inherentHeat.get(card);
            }
            if (inherent + added < HOT)
                TemperatureFields.addedHeat.set(card, HOT - inherent);
        }

        /*if (inherent > HOT) {
            inherent = HOT;
        } else if (inherent < COLD) {
            inherent = COLD;
        }
        if (inherent + added > HOT) {
            added = HOT - inherent;
        } else if (inherent + added < COLD) {
            added = COLD - inherent;
        }*/

        if (added + inherent != prevTotal) {
            flashHeatColor(card);
            if (card instanceof OnTempChangeCard) {
                ((OnTempChangeCard) card).onTempChange((added + inherent) - prevTotal);
            }
        }

        TemperatureFields.inherentHeat.set(card, inherent);
        TemperatureFields.addedHeat.set(card, added);
    }

    public static Color getCardTint(AbstractCard card) {
        switch (getCardHeatFloored(card)) {
            case COLD:
                return COLD_TINT;
            case HOT:
                return HOT_TINT;
            default:
                return STABLE_TINT;
        }
    }

    public static void flashHeatColor(AbstractCard card) {
        switch (getCardHeatFloored(card)) {
            case COLD:
                card.superFlash(COLD_TINT.cpy());
                break;
            case 0:
                card.superFlash(Color.WHITE.cpy());
                break;
            case HOT:
                card.superFlash(HOT_TINT.cpy());
                break;
        }
    }

    public static boolean canModTemp(AbstractCard card, int amount) {
        int heat = CardTemperatureFields.getCardHeat(card);
        /*if (amount > 0)
            return heat < HOT;
        if (amount < 0)
            return heat > COLD;*/
        return true;
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            //Copy non-inherent heat over to the new card
            setAddedHeat(result, TemperatureFields.addedHeat.get(self));
            return result;
        }
    }
}
