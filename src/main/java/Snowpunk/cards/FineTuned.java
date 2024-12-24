package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.FineTunedPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FineTuned extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FineTuned.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CLANK = 1, UP_CLANK = 1;

    public FineTuned() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
//        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        Wiz.applyToSelf(new FineTunePower(p, magicNumber));
//        if(getGears() > 0)
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.applyToSelf(new FineTunedPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            selfRetain = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}