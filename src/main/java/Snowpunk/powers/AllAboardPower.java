package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class AllAboardPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(AllAboardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static int IDOffset = 0;

    public AllAboardPower(AbstractCreature owner, int upgrades) {
        super(POWER_ID + IDOffset, strings.NAME, PowerType.BUFF, false, owner, upgrades);
        IDOffset++;
        this.loadRegion("blur");
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            AbstractCard card = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON, AbstractDungeon.cardRandomRng);
            if (amount > 0) {
                for (int i = 0; i < amount; i++)
                    card.upgrade();
            }
            addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 1, false));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 0)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
