package Snowpunk.patches;

import Snowpunk.cards.AnotherAnvil;
import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ApotheosisEvaporatePatch {
    @SpirePatch(clz = ApotheosisAction.class, method = "update")
    public static class ApotheosisEvaporate {
        @SpireInsertPatch(
                rloc = 5
        )
        public static void update() {
            for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
                if (card.canUpgrade())
                    card.upgrade();
                card.applyPowers();
            }
        }
    }
}
