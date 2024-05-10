package Snowpunk.potions;

import Snowpunk.TheConductor;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.SteamfogPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.SNOWY_BLUE;
import static Snowpunk.SnowpunkMod.makeID;

public class BottledInspiration extends AbstractPotion {
    public static final String POTION_ID = makeID(BottledInspiration.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BottledInspiration() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.JAR, PotionColor.ANCIENT);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        labOutlineColor = SNOWY_BLUE;
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.BRASS), BaseMod.getKeywordDescription(KeywordManager.BRASS)));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            Wiz.applyToSelf(new BrassPower(AbstractDungeon.player, potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledInspiration();
    }

    @Override
    public int getPotency(final int potency) {
        return 8;
    }
}