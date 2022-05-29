package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Think extends AbstractEasyCard {
    public final static String ID = makeID(Think.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int TINKER = 1;
    private static final int UP_TINKER = 1;

    public Think() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = TINKER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i++) {
            Wiz.atb(new TinkerAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(UP_TINKER);
    }
}