package Snowpunk.cards.parts;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

public abstract class AbstractPartCard extends AbstractEasyCard {
    public static UIStrings strings = CardCrawlGame.languagePack.getUIString(makeID("Parts"));
    public static String[] TEXT = strings.TEXT;
    private static ArrayList<TooltipInfo> partTooltip;

    protected static final Predicate<AbstractCard> isPlayable = c -> c.cost != -2;
    protected static final Predicate<AbstractCard> hasBlockValue = c -> (c.baseBlock >= 0 && !(c instanceof RitualDagger));
    protected static final Predicate<AbstractCard> hasDamageValue = c -> c.baseDamage >= 0;
    protected static final Predicate<AbstractCard> hasMagicValue = c -> c.baseMagicNumber >= 0;
    protected static final Predicate<AbstractCard> isAttack = c -> c.type == CardType.ATTACK;
    protected static final Predicate<AbstractCard> isSkill = c -> c.type == CardType.SKILL;
    protected static final Predicate<AbstractCard> isPower = c -> c.type == CardType.POWER;
    protected static final Predicate<AbstractCard> nonZeroCost = c -> c.cost > 0 || c.cost == -1;
    protected static final Predicate<AbstractCard> greaterThanZeroCost = c -> c.cost > 0;

    public final CardRarity dropRarity;

    public AbstractPartCard(String cardID, CardType type, CardRarity dropRarity) {
        super(cardID, -2, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.dropRarity = dropRarity;
        setDisplayRarity(dropRarity);
        TypeOverridePatch.setOverride(this, TEXT[0]);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (partTooltip == null) {
            partTooltip = new ArrayList<>();
            partTooltip.add(new TooltipInfo(TEXT[0], TEXT[1]));
        }
        return partTooltip;
    }

    public CardRarity getDropRarity() {
        return dropRarity;
    }

    public abstract Predicate<AbstractCard> getFilter();

    public abstract void apply(AbstractCard card);

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upp() {}
}
