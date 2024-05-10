package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.cardmods.HatMod;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static Snowpunk.SnowpunkMod.makeID;

public class MagicScarf extends AbstractEasyRelic {
    public static final String ID = makeID(MagicScarf.class.getSimpleName());

    public MagicScarf() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        if (CardModifierManager.hasModifier(card, HatMod.ID) && ((HatMod) CardModifierManager.getModifiers(card, HatMod.ID).get(0)).amount > 0) {
            Wiz.atb(new DrawCardAction(1));
            flash();
        }
        super.onUseCard(card, useCardAction);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
