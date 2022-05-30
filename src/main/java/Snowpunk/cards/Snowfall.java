package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowfall extends AbstractEasyCard {
    public final static String ID = makeID(Snowfall.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLK = 10;
    private static final int UP_BLK = 4;

    public Snowfall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new ModCardTempAction(-1, -1, true));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }
}