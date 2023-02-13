package Snowpunk.cards.abstracts;

import Snowpunk.SnowpunkMod;
import Snowpunk.patches.MkPatches;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;

public abstract class AbstractSCostMultiUpgradeCard extends AbstractMultiUpgradeCard {

    public AbstractSCostMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        setTextures();
    }

    public AbstractSCostMultiUpgradeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        setTextures();
    }

    private void setTextures() {
        setOrbTexture(SnowpunkMod.CARD_SNOW_S, SnowpunkMod.CARD_SNOW_L);
    }

}
