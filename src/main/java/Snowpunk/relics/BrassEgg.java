package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.Iterator;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassEgg extends AbstractEasyRelic {
    public static final String ID = makeID(BrassEgg.class.getSimpleName());
    public static final int AMOUNT = 3;

    private ArrayList<AbstractCard> pickedCards;

    public BrassEgg() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        description = DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
        pickedCards = new ArrayList<>();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        //tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        pickedCards.clear();
    }

    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards == null)
                return;

            for (AbstractCard card : reward.cards)
                onPreviewObtainCard(card);
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    public void onObtainCard(AbstractCard c) {
        if (!pickedCards.contains(c)) {
            CardModifierManager.addModifier(c, new PlateMod(AMOUNT, true));
            pickedCards.add(c);
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}
