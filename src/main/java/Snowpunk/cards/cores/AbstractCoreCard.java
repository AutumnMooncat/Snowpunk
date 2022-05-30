package Snowpunk.cards.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public abstract class AbstractCoreCard extends AbstractEasyCard {
    public static String[] KEYWORD_TEXT = CardCrawlGame.languagePack.getUIString(makeID("Cores")).TEXT;
    private static ArrayList<TooltipInfo> coreTooltip;

    public final CardRarity dropRarity;

    public AbstractCoreCard(String cardID, int cost, CardType type, CardRarity dropRarity) {
        super(cardID, cost, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.dropRarity = dropRarity;
        setDisplayRarity(dropRarity);
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

    public abstract void apply(AbstractCard card);

    public void prepMultiCoreDownside() {}

    public CardRarity getDropRarity() {
        return dropRarity;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

}
