package Snowpunk.cardmods;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.GearMultCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.SnowpunkPower;
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

public class PlateMod extends AbstractCardModifier {
    public static final String ID = makeID(PlateMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private boolean test = false;
    private static ArrayList<TooltipInfo> PlateTip;
    private static final Texture tex = TexLoader.getTexture(modID + "Resources/images/ui/PlateIcon.png");

    public PlateMod() {
        this(0);
    }

    public PlateMod(int amount) {
        this(amount, false);
    }

    public PlateMod(int amount, boolean test) {
        this.test = test;
        priority = 1;
        this.amount += amount;
    }

    /*@Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (damage >= 0)
            damage += amount * mult;
        return damage;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (block >= 0)
            block += amount * mult;
        return block;
    }*/

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + amount;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block + amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            PlateMod plateMod = (PlateMod) CardModifierManager.getModifiers(card, ID).get(0);
            plateMod.amount += amount;
            if (plateMod.amount < 0)
                plateMod.amount = 0;
            return false;
        }
        return (card.baseBlock >= 0 || card.baseDamage >= 0);
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (PlateTip == null) {
            PlateTip = new ArrayList<>();
            PlateTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.PLATE), BaseMod.getKeywordDescription(KeywordManager.PLATE)));
        }
        return PlateTip;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        int numGears = amount;
        /*if (Wiz.adp() != null && Wiz.adp().hand.contains(card)) {
            if (Wiz.adp().hasPower(BrassPower.POWER_ID) && Wiz.adp().getPower(BrassPower.POWER_ID).amount > 0)
                numGears += Wiz.adp().getPower(BrassPower.POWER_ID).amount;
            if (Wiz.adp().hasPower(SnowpunkPower.POWER_ID) && AbstractEasyCard.getSnowStatic() > 0)
                numGears += Wiz.adp().getPower(SnowpunkPower.POWER_ID).amount * AbstractEasyCard.getSnowStatic();
        }

        if (numGears > 0) {
            if (numGears > amount)
                ExtraIcons.icon(tex).text(String.valueOf(numGears)).textColor(Color.GREEN).render(card);
            else
                ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
        }*/
        ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
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
        return new PlateMod(amount);
    }
}