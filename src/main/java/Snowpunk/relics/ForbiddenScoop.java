package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.powers.SteamPower;
import Snowpunk.relics.interfaces.ModifySnowballsRelic;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class ForbiddenScoop extends AbstractEasyRelic implements ModifySnowballsRelic {
    public static final String ID = makeID(ForbiddenScoop.class.getSimpleName());

    public static final int AMOUNT = 1;

    public ForbiddenScoop() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
        tips.clear();
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (Wiz.adp() == null)
            return DESCRIPTIONS[3];
        if (Wiz.adp().relics.contains(this))
            return DESCRIPTIONS[2];
        return DESCRIPTIONS[0] + getHPLost() + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        CardCrawlGame.screenShake.rumble(2.0F);
        addToBot(new VFXAction(Wiz.adp(), new BorderFlashEffect(Color.RED), 0.3F, true));
        Wiz.adp().maxHealth = Wiz.adp().maxHealth - getHPLost();
        if (Wiz.adp().currentHealth > Wiz.adp().maxHealth)
            Wiz.adp().currentHealth = Wiz.adp().maxHealth;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        initializeTips();
    }

    private int getHPLost() {
        return Math.round(Wiz.adp().maxHealth * .33f);
    }

    @Override
    public int modifySnow() {
        return 1;
    }
}
