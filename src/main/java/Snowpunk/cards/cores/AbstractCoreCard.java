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
    public enum ValueType {
        DAMAGE,
        BLOCK,
        MAGIC,
        NONE
    }
    public static String[] KEYWORD_TEXT = CardCrawlGame.languagePack.getUIString(makeID("Cores")).TEXT;
    private static ArrayList<TooltipInfo> coreTooltip;

    public final CardRarity dropRarity;
    public final ValueType valueType;

    public AbstractCoreCard(String cardID, CardType type, CardRarity dropRarity, ValueType valueType) {
        super(cardID, -2, type, CardRarity.SPECIAL, CardTarget.NONE);
        this.dropRarity = dropRarity;
        this.valueType = valueType;
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

    public boolean canSpawn(ArrayList<AbstractCoreCard> chosenCores) {
        return chosenCores.stream().noneMatch(c -> c.getClass().equals(this.getClass())) && (this.valueType == ValueType.NONE || chosenCores.stream().filter(c -> c.valueType == this.valueType).count() < 2);
    }

    public abstract void apply(AbstractCard card);

    public abstract void prepForSelection(ArrayList<AbstractCoreCard> chosenCores);

    public void prepRenderedCost(int currentDoubledCost) {
        switch (dropRarity) {
            case COMMON:
                if (currentDoubledCost % 2 == 0) {
                    this.cost = this.costForTurn = 1;
                } else {
                    this.cost = this.costForTurn = 0;
                }
                break;
            case RARE:
                if (currentDoubledCost % 2 == 0) {
                    this.cost = this.costForTurn = 2;
                } else {
                    this.cost = this.costForTurn = 1;
                }
                break;
            default:
                this.cost = this.costForTurn = 1;
                break;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

}
