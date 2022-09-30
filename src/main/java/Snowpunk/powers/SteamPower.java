package Snowpunk.powers;

import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamPower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(SteamPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public SteamPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && amount > 0) {
            /*if (!owner.hasPower(SteamFormPower.POWER_ID)) {
                action.exhaustCard = true;
            }*/
            flash();
            if (!card.exhaust)
                EvaporatePanelPatches.EvaporateField.evaporate.set(card, true);
            --amount;
            updateDescription();
            if (amount == 0) {
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }

//    @Override
//    public void atEndOfTurn(boolean isPlayer) {
//        if (!owner.hasPower(BackupBoilerPower.POWER_ID))
//            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
//        else
//            owner.getPower(BackupBoilerPower.POWER_ID).flash();
//    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
//        if (!owner.hasPower(BackupBoilerPower.POWER_ID))
//            description += DESCRIPTIONS[3];
    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return true;
    }
}
