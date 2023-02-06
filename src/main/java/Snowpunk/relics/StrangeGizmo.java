package Snowpunk.relics;

import Snowpunk.TheConductor;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.CardRewardSkipButtonRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Snowpunk.SnowpunkMod.makeID;

public class StrangeGizmo extends AbstractEasyRelic implements CardRewardSkipButtonRelic {
    public static final String ID = makeID(StrangeGizmo.class.getSimpleName());

    public StrangeGizmo() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void onClickedButton() {
        ArrayList<AbstractCard> upgradableCards = new ArrayList();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }

        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            upgradableCards.get(0).upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }

    @Override
    public String getButtonLabel() {
        return DESCRIPTIONS[1];
    }

    @Override
    public Texture getTexture() {
        return CardRewardSkipButtonRelic.super.getTexture();
    }

    //TODO make button not show if no upgradeable cards once this feature is live
}
