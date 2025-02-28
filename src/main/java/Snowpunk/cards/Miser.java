package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.UpgradeInHandAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.MiserPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

public class Miser extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Miser.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CLANK = 1, UP_CLANK = 1;

    boolean boostTemp = false;
    public Miser() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        boostTemp = false;
        exhaust = true;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCardModifier> hat = new ArrayList<>();
        hat.add(new HatMod(1));
        Wiz.atb(new EnhanceCardInHardAction(1, 0, boostTemp ? CardTemperatureFields.COLD : 0, hat, false,
                (c) -> (CardTemperatureFields.getCardHeat(c) < 0)));
        Wiz.atb(new EnhanceCardInHardAction(1, 2, boostTemp ? CardTemperatureFields.HOT : 0, null, false,
                (c) -> (CardTemperatureFields.getCardHeat(c) > 0)));
//        Wiz.applyToSelf(new FineTunePower(p, magicNumber));
//        if(getGears() > 0)
//        Wiz.atb(new DrawCardAction(getGears()));
//        Wiz.applyToSelf(new MiserPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeInfo(1);
            exhaust = false;
        });
        addUpgradeData(() -> {
            boostTemp = true;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}