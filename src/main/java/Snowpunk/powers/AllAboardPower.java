package Snowpunk.powers;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class AllAboardPower extends AbstractEasyPower implements OnEvaporatePower {
    public static String POWER_ID = makeID(AllAboardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    public static int IDOffset = 0;

    //private boolean makeEthereal, upgrade;

    public AllAboardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        //this.makeEthereal = makeEthereal;
        //this.upgrade = upgrade;
        this.loadRegion("blur");
    }

    /*
        public void atStartOfTurn() {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                if (amount > 0) {
                    flash();
                    for (int i = 0; i < amount; i++) {
                        AbstractCard card = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON, AbstractDungeon.cardRandomRng);
                        if (makeEthereal)
                            CardModifierManager.addModifier(card, new EtherealMod());
                        if (upgrade)
                            card.upgrade();
                        addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy(), 1, false));
                    }
                }
            }
        }
    */
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        /*if (makeEthereal)
            description += DESCRIPTIONS[2];
        if (upgrade)
            description += DESCRIPTIONS[3];
        description += DESCRIPTIONS[4];*/
    }

    @Override
    public AbstractPower makeCopy() {
        return new AllAboardPower(owner, amount);
    }

    @Override
    public void onEvaporate(AbstractCard card) {
        Wiz.applyToSelf(new BrassPower(Wiz.adp(), amount));
        //addToBot(new ApplyCardModifierAction(card, new PlateMod(amount)));
        flash();
    }
}
