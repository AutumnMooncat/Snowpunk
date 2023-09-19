package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.adp;

public class ChristmasSpiritPower extends AbstractEasyPower implements HealthBarRenderPower {
    public static String POWER_ID = makeID(ChristmasSpiritPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private final Color hpBarColor = new Color(12f / 256f, 242 / 256f, 108 / 256f, 1);

    public ChristmasSpiritPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, NeutralPowertypePatch.NEUTRAL, false, owner, amount);
    }


    @Override
    public void atStartOfTurn() {
        if (owner != adp() && owner.currentHealth <= amount) {
            flash();
            escapeNext((AbstractMonster) owner);
        }
    }

    private void escapeNext(AbstractMonster monster) {
        Random random = new Random();
        int speech = 1 + random.nextInt(strings.DESCRIPTIONS.length - 2);
        if (!monster.cannotEscape && !monster.escapeNext) {
            monster.escapeNext = true;
            Wiz.atb(new TalkAction(monster, strings.DESCRIPTIONS[speech]));
            Wiz.atb(new EscapeAction(monster));
            Wiz.atb(new SetMoveAction(monster, (byte) 3, AbstractMonster.Intent.ESCAPE));
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
}
