package Snowpunk.damageMods;

import Snowpunk.SnowpunkMod;
import Snowpunk.icons.IconContainer;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

public class FireDamage extends AbstractDamageModifier {

    public static final String ID = SnowpunkMod.makeID("FireDamage");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private TooltipInfo fireTooltip = null;
    private final boolean inherent;
    private final boolean pushIcon;

    public FireDamage() {
        this(false, false);
    }

    public FireDamage(boolean inherent, boolean pushIcon) {
        this.inherent = inherent;
        this.pushIcon = pushIcon;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        return target.currentBlock == 0 ? damage * 1.2F : damage;
    }

    @Override
    public ArrayList<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> l = super.getCustomTooltips();
        if (fireTooltip == null) {
            fireTooltip = new TooltipInfo(cardStrings.DESCRIPTION, cardStrings.EXTENDED_DESCRIPTION[0]);
        }
        l.add(fireTooltip);
        return l;
    }

    @Override
    public boolean shouldPushIconToCard(AbstractCard card) {
        return pushIcon;
    }

    @Override
    public AbstractCustomIcon getAccompanyingIcon() {
        return IconContainer.FireIcon.get();
    }

    @Override
    public String getCardDescriptor() {
        return cardStrings.NAME;
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new FireDamage();
    }
}
