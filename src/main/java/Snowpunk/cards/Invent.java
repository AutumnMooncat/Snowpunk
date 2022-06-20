package Snowpunk.cards;

import Snowpunk.actions.AssembleCardAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Invent extends AbstractEasyCard {
    public final static String ID = makeID(Invent.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Invent() {
        super(ID, COST, TYPE, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING); // We don't want this generated in combat
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.CREATION));
        } else {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.INVENTION));
        }
    }

    public void upp() {
        uDesc();
    }
}