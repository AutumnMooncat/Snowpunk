package Snowpunk.powers;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.interfaces.OnClankPower;
import Snowpunk.powers.interfaces.OnCondensePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;

public class AssembledPower extends AbstractEasyPower implements OnCondensePower, OnClankPower, NonStackablePower {
    public static String POWER_ID = makeID(AssembledPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public AssembledCard core;
    public int cardsDrawnThisTurn, secondAmount, cardsPlayedThisTurn;
    public boolean postStartDraw;

    public AssembledPower(AbstractCreature owner, int amount, int secondAmount, AssembledCard assembledCard) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        core = assembledCard;
        cardsDrawnThisTurn = 0;
        cardsPlayedThisTurn = 0;
        this.secondAmount = secondAmount;
        if (core.cores.size() > 2)
            loadRegion(core.cores.get(1).powerIcon());
        else
            loadRegion("tools");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        cardsDrawnThisTurn = 0;
        cardsPlayedThisTurn = 0;
        postStartDraw = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.PLAY && cardsPlayedThisTurn < 1) {
            cardsPlayedThisTurn++;
            flash();
            core.cores.get(1).onPowerTrigger(card, amount, action);
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.DRAW && cardsDrawnThisTurn < secondAmount) {
            cardsDrawnThisTurn++;
            flash();
            core.cores.get(1).onPowerTrigger(card, amount);
        }
    }

    @Override
    public void onCondense(AbstractCard card) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.HOT) {
            core.cores.get(1).onPowerTrigger(card, amount);
            flash();
        }
    }

    @Override
    public void onClank(AbstractCard card) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.CLANK) {
            flash();
            core.cores.get(1).onPowerTrigger(card, amount);
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.END) {
            flash();
            for (AbstractCard card : adp().hand.group)
                core.cores.get(1).onPowerTrigger(card, amount);
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (adp() != null && adp().hasPower(AssembledPower.POWER_ID)) {
                            for (AbstractPower p : adp().powers) {
                                if (p instanceof AssembledPower)
                                    ((AssembledPower) p).postStartDraw = true;
                            }
                        }
                        isDone = true;
                    }
                });
                isDone = true;
            }
        });
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.START) {
            flash();
            core.cores.get(1).onPowerTrigger(amount);
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.DBF) {
            if (power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && source == owner && target != owner && !target.hasPower("Artifact")) {
                flash();
                core.cores.get(1).onPowerTrigger(amount);
            }
        }
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if (core.cores.get(0).getPower() == CoreCard.PowerCondition.BLK && blockAmount > 0.0F) {
            flash();
            core.cores.get(1).onPowerTrigger(amount);
        }

    }// 34

    @Override
    public void updateDescription() {
        if (core == null)
            return;
        if (core.name != null)
            name = core.name;
        if (secondAmount == -1) {
            if (amount == 1)
                description = core.cores.get(0).rawDescription + " " + core.cores.get(1).getExtended()[2];
            else
                description = core.cores.get(0).rawDescription + " " + core.cores.get(1).getExtended()[0] + amount + core.cores.get(1).getExtended()[1];
        } else {
            if (amount == 1)
                description = core.cores.get(0).getExtended()[0] + secondAmount + core.cores.get(0).getExtended()[1] + core.cores.get(1).getExtended()[2];
            else
                description = core.cores.get(0).getExtended()[0] + secondAmount + core.cores.get(0).getExtended()[1] + core.cores.get(1).getExtended()[0] + amount + core.cores.get(1).getExtended()[1];
        }
        description = description.replaceAll("snowpunk:", "#y");
    }
}
