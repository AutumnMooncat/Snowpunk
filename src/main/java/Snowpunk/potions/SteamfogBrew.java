package Snowpunk.potions;

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

public class SteamfogBrew extends AbstractPotion {
    public static final String POTION_ID = makeID(SteamfogBrew.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SteamfogBrew() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.T, PotionColor.ANCIENT);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        labOutlineColor = SNOWY_BLUE;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            Wiz.applyToSelf(new SteamfogPower(AbstractDungeon.player, potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SteamfogBrew();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}