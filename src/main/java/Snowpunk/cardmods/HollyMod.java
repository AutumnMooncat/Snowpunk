package Snowpunk.cardmods;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.TexLoader;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.SnowpunkMod.modID;

public class HollyMod extends AbstractCardModifier {

    public static final String ID = makeID(HollyMod.class.getSimpleName());

    private int amount = 1;
    private static ArrayList<TooltipInfo> Tooltip;

    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/HollyIcon.png");

    public HollyMod(int amount) {
        this.amount = amount;
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.WREATH), BaseMod.getKeywordDescription(KeywordManager.WREATH)));
        }
        return Tooltip;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damage >= 0)
            damage += amount;
        return damage;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if (block >= 0)
            block += amount;
        return block;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            HollyMod hollyMod = (HollyMod) CardModifierManager.getModifiers(card, ID).get(0);
            hollyMod.amount += amount;
            return false;
        }

        if (card.baseBlock < 0 && card.baseDamage < 0)
            return false;

        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HollyMod(amount);
    }

}
