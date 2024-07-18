package Snowpunk.potions;

import Snowpunk.actions.CondenseAction;
import Snowpunk.actions.ModCardTempAction;
import Snowpunk.powers.SteamfogPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.SNOWY_BLUE;
import static Snowpunk.SnowpunkMod.makeID;

public class IceblastTonic extends AbstractPotion {
    public static final String POTION_ID = makeID(IceblastTonic.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public IceblastTonic() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPIKY, PotionColor.ANCIENT);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        labOutlineColor = SNOWY_BLUE;
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.COLD), BaseMod.getKeywordDescription(KeywordManager.COLD)));
    }

    @Override
    public void use(AbstractCreature target) {
        Wiz.atb(new ModCardTempAction(99, -potency, true));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new IceblastTonic();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }
}