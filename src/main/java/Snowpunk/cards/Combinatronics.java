package Snowpunk.cards;

import Snowpunk.actions.CombinatronicsAction;
import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Combinatronics extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Combinatronics.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;


    public Combinatronics() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new CombinatronicsAction());
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}