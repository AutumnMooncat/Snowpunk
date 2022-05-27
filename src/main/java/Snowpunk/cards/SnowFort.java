package Snowpunk.cards;

import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.SCostFieldPatches;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SnowFort extends AbstractEasyCard {
    public final static String ID = makeID("SnowFort");

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int BLK = 5;
    private static final int UP_BLK = 3;

    public SnowFort() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
        CardModifierManager.addModifier(this, new TemperatureMod(true, -1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < getSnow() ; i++) {
            blck();
        }
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }
}