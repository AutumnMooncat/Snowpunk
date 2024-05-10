package Snowpunk.cardmods;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.GearNextPower;
import Snowpunk.powers.SnowpunkPower;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class GearMod extends AbstractCardModifier {
    public static final String ID = makeID(GearMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private boolean test = false;
    private static ArrayList<TooltipInfo> GearTip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/GearIcon.png");

    public GearMod() {
        this(0);
    }

    public GearMod(int amount) {
        this(amount, false);
    }

    public GearMod(int amount, boolean test) {
        this.test = test;
        priority = 1;
        this.amount += amount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.GEAR);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(CustomTags.GEAR);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            GearMod gearMod = (GearMod) CardModifierManager.getModifiers(card, ID).get(0);
            gearMod.amount += amount;
            if (gearMod.amount < 0)
                gearMod.amount = 0;
            return false;
        }
        if (test)
            return (CardModifierManager.hasModifier(card, ID));
        return true;
    }

    /*
        @Override
        public List<TooltipInfo> additionalTooltips(AbstractCard card) {
            if (GearTip == null) {
                GearTip = new ArrayList<>();
                GearTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.GEAR), BaseMod.getKeywordDescription(KeywordManager.GEAR)));
            }
            return GearTip;
        }
    */
    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        int numGears = amount;
        if (Wiz.adp() != null && Wiz.adp().hand.contains(card)) {
            if (Wiz.adp().hasPower(GearNextPower.POWER_ID) && Wiz.adp().getPower(GearNextPower.POWER_ID).amount > 0)
                numGears += Wiz.adp().getPower(GearNextPower.POWER_ID).amount;
            if (Wiz.adp().hasPower(SnowpunkPower.POWER_ID) && AbstractEasyCard.getSnowStatic() > 0)
                numGears += Wiz.adp().getPower(SnowpunkPower.POWER_ID).amount * AbstractEasyCard.getSnowStatic();
        }

        if (numGears > 0) {
            if (numGears > amount)
                ExtraIcons.icon(tex).text(String.valueOf(numGears)).textColor(Color.GREEN).render(card);
            else
                ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
        }
        /*if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);*/
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GearMod(amount);
    }
}