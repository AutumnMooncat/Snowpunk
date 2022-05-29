package Snowpunk.cards.cores;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public abstract class AbstractCoreCard extends AbstractEasyCard {

    public static UIStrings strings = CardCrawlGame.languagePack.getUIString(makeID("Cores"));
    public static String[] TEXT = strings.TEXT;
    private static ArrayList<TooltipInfo> coreTooltip;

    public final CardRarity dropRarity;

    public AbstractCoreCard(String cardID, int cost, CardType type, CardRarity dropRarity) {
        super(cardID, cost, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.dropRarity = dropRarity;
        setDisplayRarity(dropRarity);
        TypeOverridePatch.setOverride(this, TEXT[0]);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (coreTooltip == null) {
            coreTooltip = new ArrayList<>();
            coreTooltip.add(new TooltipInfo(TEXT[1], TEXT[2]));
        }
        return coreTooltip;
    }

    public CardRarity getDropRarity() {
        return dropRarity;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void upp() {}

}
