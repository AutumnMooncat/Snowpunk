package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.HeatTransferPower;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TrolleyForm extends AbstractEasyCard {
    public final static String ID = makeID(TrolleyForm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int BURN = 3;
    private static final int UP_BURN = 1;

    public TrolleyForm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BURN;
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HeatTransferPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_BURN);
    }
}