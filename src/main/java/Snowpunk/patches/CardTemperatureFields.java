package Snowpunk.patches;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.interfaces.OnTempChangeCard;
import Snowpunk.util.GunManager;
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

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class TemperatureFields {
        public static SpireField<Integer> inherentHeat = new SpireField<>(() -> 0);
        public static SpireField<Integer> addedHeat = new SpireField<>(() -> 0);
    }

    public static int getCardHeat(AbstractCard card) {
        return TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card);
    }

    public static void addInherentHeat(AbstractCard card, int amount) {
        addInherentHeat(card, amount, true);
    }

    public static void addHeat(AbstractCard card, int amount) {
        addHeat(card, amount, true);
    }

    public static void addInherentHeat(AbstractCard card, int amount, boolean affectGun) {
        if (amount == 0)
            return;
        addAndClampHeat(card, amount, true, affectGun);
        CardModifierManager.addModifier(card, new TemperatureMod());
    }

    public static void addHeat(AbstractCard card, int amount, boolean affectGun) {
        if (amount == 0)
            return;
        addAndClampHeat(card, amount, false, affectGun);
        CardModifierManager.addModifier(card, new TemperatureMod());
    }

    private static void addAndClampHeat(AbstractCard card, int amount, boolean addInherent, boolean affectGun) {
        int prevTotal = TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card);
        if (addInherent)
            TemperatureFields.inherentHeat.set(card, TemperatureFields.inherentHeat.get(card) + amount);
        else
            TemperatureFields.addedHeat.set(card, TemperatureFields.addedHeat.get(card) + amount);

        int inherent = TemperatureFields.inherentHeat.get(card);
        int added = TemperatureFields.addedHeat.get(card);

        if (inherent > 2) {
            inherent = 2;
        } else if (inherent < -2) {
            inherent = -2;
        }
        if (inherent + added > 2) {
            added = 2 - inherent;
        } else if (inherent + added < -2) {
            added = -2 - inherent;
        }

        //If inherent goes up, but added goes down due to clamping, no change actually happens to current heat
        //int delta = (inherent + added) - (TemperatureFields.inherentHeat.get(card) + TemperatureFields.addedHeat.get(card));
        if (added + inherent != prevTotal) {
            flashHeatColor(card);
            if (card instanceof OnTempChangeCard) {
                ((OnTempChangeCard) card).onTempChange((added + inherent) - prevTotal);
            }
            if (card.tags.contains(CustomTags.GUN)) {
                GunManager.RunGunUpdate(card);
            }
        }

        TemperatureFields.inherentHeat.set(card, inherent);
        TemperatureFields.addedHeat.set(card, added);
    }

    public static Color getCardTint(AbstractCard card) {
        switch (getCardHeat(card)) {
            case -2:
                return FROZEN_TINT;
            case -1:
                return COLD_TINT;
            case 1:
                return HOT_TINT;
            case 2:
                return OVERHEAT_TINT;
            default:
                return STABLE_TINT;
        }
    }

    public static void flashHeatColor(AbstractCard card) {
        switch (getCardHeat(card)) {
            case -2:
                card.superFlash(FROZEN_TINT.cpy());
                break;
            case -1:
                card.superFlash(COLD_TINT.cpy());
                break;
            case 0:
                card.superFlash(Color.WHITE.cpy());
                break;
            case 1:
                card.superFlash(HOT_TINT.cpy());
                break;
            case 2:
                card.superFlash(OVERHEAT_TINT.cpy());
                break;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            //Copy non-inherent heat over to the new card
            addHeat(result, TemperatureFields.addedHeat.get(self));
            return result;
        }
    }


}
