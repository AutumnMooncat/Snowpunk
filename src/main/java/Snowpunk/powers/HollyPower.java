package Snowpunk.powers;

import Snowpunk.cards.FaLaLaLaLa;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import static Snowpunk.SnowpunkMod.makeID;

public class HollyPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(HollyPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public HollyPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, num);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new MakeTempCardInHandAction(new FaLaLaLaLa(amount), false, true));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
