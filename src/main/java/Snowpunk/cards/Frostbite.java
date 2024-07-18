package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Frostbite extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Frostbite.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public Frostbite() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FrostbitePower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
        //addUpgradeData(() -> upgradeBaseCost(1));
    }

}