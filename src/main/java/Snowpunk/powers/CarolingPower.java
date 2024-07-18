package Snowpunk.powers;

import Snowpunk.actions.CondenseAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.Caroling;
import Snowpunk.powers.interfaces.OnCreateCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class CarolingPower extends AbstractEasyPower implements OnCreateCardPower {
    public static String POWER_ID = makeID(CarolingPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static UIStrings carolStrings = CardCrawlGame.languagePack.getUIString(makeID("Carols"));
    public static String[] CAROLS = carolStrings.TEXT;


    public CarolingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            carol();
            /*for (int i = 0; i < amount; i++) {
                AbstractCard card = Caroling.Carols.get(AbstractDungeon.cardRandomRng.random(Caroling.Carols.size())).makeStatEquivalentCopy();
                Wiz.atb(new MakeTempCardInHandAction(card));
            }*/
        }
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        CardModifierManager.addModifier(card, new GearMod(amount, true));
        card.superFlash();
        card.applyPowers();
        flash();
    }

    public static void carol() {
        Random random = new Random();
        int speech = 1 + random.nextInt(CAROLS.length - 2);
        Wiz.atb(new TalkAction(true, CAROLS[speech], 2, 2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CarolingPower(owner, amount);
    }
}
