package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.ChooChooPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChooChoo extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChooChoo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 5, BRASS = 25;
    private boolean endTurn = true;

    public ChooChoo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = BRASS;
        endTurn = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BrassPower(p, magicNumber));
        Wiz.applyToSelf(new ChooChooPower(p, 1));
        if (endTurn)
            addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(7));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> {
            endTurn = false;
            uDesc();
        });
    }
}