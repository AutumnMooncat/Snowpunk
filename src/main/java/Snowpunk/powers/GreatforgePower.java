package Snowpunk.powers;

import Snowpunk.actions.UpgradeRandomInHardWithVisualAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class GreatforgePower extends AbstractEasyPower {
    public static String POWER_ID = makeID(GreatforgePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    //private int cardsDrawnThisTurn = 0;

    public GreatforgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        for (int i = 0; i < amount; i++)
            addToBot(new UpgradeRandomInHardWithVisualAction(1));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        if (amount > 1)
            description += DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GreatforgePower(owner, amount);
    }
}
