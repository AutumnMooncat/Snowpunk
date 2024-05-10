package Snowpunk.cards;

import Snowpunk.actions.CondenseAction;
import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class EngineOffload extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EngineOffload.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0;

    boolean hot;
    public EngineOffload() {
        super(ID, COST, TYPE, RARITY, TARGET);
        info = baseInfo = 0;
        hot = false;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhumeEvaporatedCardAction(1, 1, hot ? 1 : 0));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeInfo(1);
            hot = true;
        });
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
    }
}