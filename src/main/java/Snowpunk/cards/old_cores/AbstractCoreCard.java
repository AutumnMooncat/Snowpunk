package Snowpunk.cards.old_cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public abstract class AbstractCoreCard extends AbstractEasyCard {
    public enum EffectTag {
        DAMAGE,
        BLOCK,
        MAGIC,
        NONE,
        EXHAUSTS,
        REMOVES_EXHAUST,
        UPGRADES_COST,
    }
    public static String[] KEYWORD_TEXT = CardCrawlGame.languagePack.getUIString(makeID("Cores")).TEXT;
    private static ArrayList<TooltipInfo> coreTooltip;

    public final ArrayList<EffectTag> effectTags;
    public boolean useSecondDamage, useSecondBlock, useSecondMagic;

    public AbstractCoreCard(String cardID, int cost, CardType type, EffectTag... effectTags) {
        super(cardID, cost, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.effectTags = new ArrayList<>(Arrays.asList(effectTags));
        TypeOverridePatch.setOverride(this, KEYWORD_TEXT[0]);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (coreTooltip == null) {
            coreTooltip = new ArrayList<>();
            coreTooltip.add(new TooltipInfo(KEYWORD_TEXT[1], KEYWORD_TEXT[2]));
        }
        return coreTooltip;
    }

    public boolean canSpawn(ARCHIVED_AssembledCard card, ArrayList<AbstractCoreCard> chosenCores) {
        if (chosenCores.stream().anyMatch(c -> c.getClass().equals(this.getClass()))) {
            return false;
        }
        if (effectTags.contains(EffectTag.DAMAGE) && chosenCores.stream().anyMatch(c -> c.effectTags.contains(EffectTag.DAMAGE))) {
            return false;
        }
        if (effectTags.contains(EffectTag.BLOCK) && chosenCores.stream().anyMatch(c -> c.effectTags.contains(EffectTag.BLOCK))) {
            return false;
        }
        if (effectTags.contains(EffectTag.MAGIC) && chosenCores.stream().filter(c -> c.effectTags.contains(EffectTag.MAGIC)).count() > 1) {
            return false;
        }
        return true;
    }

    public abstract void apply(AbstractCard card);

    public void prepForSelection(ARCHIVED_AssembledCard card, ArrayList<AbstractCoreCard> chosenCores) {
        if (effectTags.contains(EffectTag.DAMAGE) && chosenCores.stream().anyMatch(c -> c.effectTags.contains(EffectTag.DAMAGE))) {
            swapDynvarKey(EffectTag.DAMAGE);
            useSecondDamage = true;
        }
        if (effectTags.contains(EffectTag.BLOCK) && chosenCores.stream().anyMatch(c -> c.effectTags.contains(EffectTag.BLOCK))) {
            swapDynvarKey(EffectTag.BLOCK);
            useSecondBlock = true;
        }
        if (effectTags.contains(EffectTag.MAGIC) && chosenCores.stream().anyMatch(c -> c.effectTags.contains(EffectTag.MAGIC))) {
            swapDynvarKey(EffectTag.MAGIC);
            useSecondMagic = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    protected void swapDynvarKey(EffectTag type) {
        switch (type) {
            case DAMAGE:
                this.rawDescription = this.rawDescription.replace("!D!", "!Snowpunk:D2!");
                break;
            case BLOCK:
                this.rawDescription = this.rawDescription.replace("!B!", "!Snowpunk:B2!");
                break;
            case MAGIC:
                this.rawDescription = this.rawDescription.replace("!M!", "!Snowpunk:M2!");
                break;
            default:
                break;
        }
        initializeDescription();
    }

}
