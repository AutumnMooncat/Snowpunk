package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassContraptionPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(BrassContraptionPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public BrassContraptionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (amount > 0 && card.canUpgrade()) {
            for (int i = 0; i < amount; i++)
                card.upgrade();
            card.applyPowers();
        }
    }

    /*
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer && Wiz.adp().drawPile.size() > 0)
        {
            for (int i = 0; i < amount; i++)
                Wiz.atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        for(AbstractCard c: Wiz.adp().drawPile.group)
                        {
                            if(c.canUpgrade())
                            {
                                addToBot(new UpgradeSpecificCardAction(c));
                            }
                        }
                        isDone = true;
                    }
                });
            addToBot(new VFXAction(new UpgradeShineEffect(CardGroup.DRAW_PILE_X, CardGroup.DRAW_PILE_Y)));
            flash();
        }
    }*/

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
