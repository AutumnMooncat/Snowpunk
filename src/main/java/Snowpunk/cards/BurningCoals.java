package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BurningCoals extends AbstractEasyCard {
    public final static String ID = makeID("BurningCoals");

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public BurningCoals() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new TemperatureMod(true, 1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SelectCardsAction(p.hand.group, 1, "", false, card -> TemperatureMod.getCardHeat(card) != 2, cards -> {
            for (AbstractCard c : cards) {
                Wiz.atb(new ModCardTempAction(c, 1));
            }
        }));
    }

    public void upp() {
        CardModifierManager.addModifier(this, new TemperatureMod(true, 1));
    }
}