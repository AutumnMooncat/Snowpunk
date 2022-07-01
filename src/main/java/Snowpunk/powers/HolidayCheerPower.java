package Snowpunk.powers;

import Snowpunk.relics.ChristmasSpirit;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;

public class HolidayCheerPower extends AbstractEasyPower implements HealthBarRenderPower {
    public static String POWER_ID = makeID(HolidayCheerPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private final Color hpBarColor = new Color(Color.GREEN);
    private boolean winByHoliday = false;

    public HolidayCheerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, NeutralPowertypePatch.NEUTRAL, false, owner, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (!isPlayer)
            return;
        flash();
        boolean goingToWin = true;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.currentHealth <= amount) {
                escapeNext(monster);
            } else
                goingToWin = false;
        }
        if (goingToWin) {
            winByHoliday = true;
            ChristmasSpirit christmasSpirit = checkChristmas();
            if (christmasSpirit != null)
                christmasSpirit.wonFromCheer = true;
        }
    }

    private void escapeNext(AbstractMonster monster) {
        Random random = new Random();
        int speech = 1 + random.nextInt(strings.DESCRIPTIONS.length - 2);
        if (!monster.cannotEscape && !monster.escapeNext) {
            monster.escapeNext = true;
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(monster));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(monster, (byte) 3, AbstractMonster.Intent.ESCAPE));
            AbstractDungeon.effectList.add(new SpeechBubble(monster.dialogX, monster.dialogY, 3.0F, strings.DESCRIPTIONS[speech], false));
            ChristmasSpirit christmasSpirit = checkChristmas();
            if (christmasSpirit != null)
                christmasSpirit.counter++;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }

    private ChristmasSpirit checkChristmas() {
        if (!adp().hasRelic(ChristmasSpirit.ID)) {
            adp().relics.add(new ChristmasSpirit());
            adp().reorganizeRelics();
        }
        return (ChristmasSpirit) adp().getRelic(ChristmasSpirit.ID);
    }
}
