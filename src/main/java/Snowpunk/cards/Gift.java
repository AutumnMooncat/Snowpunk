package Snowpunk.cards;

import Snowpunk.actions.GiftDiscoveryAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Gift extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Gift.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 3, UP_MAGIC = 2;

    public Gift() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GiftDiscoveryAction(magicNumber, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
            initializeDescription();
        });
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}