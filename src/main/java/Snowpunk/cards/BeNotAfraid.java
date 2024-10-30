package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CarolingPower;
import Snowpunk.powers.GraceHealPower;
import Snowpunk.powers.GracePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BeNotAfraid extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BeNotAfraid.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;

    boolean heal = false;

    public BeNotAfraid() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        heal = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new GracePower(p, magicNumber));
        if (heal)
            Wiz.applyToSelf(new GraceHealPower(p, 7));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(2));
        addUpgradeData(() -> {
            heal = true;
            uDesc();
        });
    }
}