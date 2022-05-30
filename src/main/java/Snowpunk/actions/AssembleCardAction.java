package Snowpunk.actions;

import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class AssembleCardAction extends AbstractGameAction {
    public static final ArrayList<AbstractCoreCard> pickedCores = new ArrayList<>();
    int parts;
    int cores;

    public AssembleCardAction(int cores, int parts) {
        this.cores = cores;
        this.parts = parts;
    }

    @Override
    public void update() {
        pickedCores.clear();
        if (cores == 0) {
            return;
        }
        AssembledCard ac = new AssembledCard();
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                finalizeCard(ac);
                this.isDone = true;
            }
        });
        for (int i = 0 ; i < parts ; i++) {
            Wiz.att(new TinkerAction(ac));
        }
        for (int i = 0 ; i < cores ; i++) {
            Wiz.att(new PickCoresAction(ac));
        }
        this.isDone = true;
    }

    private void finalizeCard(AssembledCard card) {
        AssembledCard copy = (AssembledCard) card.makeStatEquivalentCopy();
        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(copy, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        } else {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(copy, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

}
