package Snowpunk.powers;

import Snowpunk.actions.MoveFromOnePileToAnotherAction;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ShovelCoalPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(ShovelCoalPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    int numExhausted = 0;

    public ShovelCoalPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
        numExhausted = 0;
    }

    @Override
    public void atStartOfTurn() {
        numExhausted = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (numExhausted < amount) {
            numExhausted++;
            Wiz.atb(new MoveFromOnePileToAnotherAction(card, Wiz.adp().exhaustPile, EvaporatePanel.evaporatePile));
            for (AbstractPower power : Wiz.adp().powers) {
                if (power instanceof OnEvaporatePower)
                    ((OnEvaporatePower) power).onEvaporate(card);
            }
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {
                    for (AbstractPower power : monster.powers) {
                        if (power instanceof OnEvaporatePower)
                            ((OnEvaporatePower) power).onEvaporate(card);
                    }
                }
            }
            flash();
        }
        super.onExhaust(card);
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShovelCoalPower(owner, amount);
    }
}
