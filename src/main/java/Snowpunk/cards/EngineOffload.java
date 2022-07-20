package Snowpunk.cards;

import Snowpunk.actions.CondenseRandomCardToDrawPileAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.EngineOffloadPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static Snowpunk.SnowpunkMod.makeID;

public class EngineOffload extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EngineOffload.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, UP_COST = 1;

    public EngineOffload() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (type != CardType.POWER) {
            Wiz.atb(new ModEngineTempAction(true));
            Wiz.atb(new CondenseRandomCardToDrawPileAction(EvaporatePanel.evaporatePile.size() + 2));
        } else {
            Wiz.applyToSelf(new EngineOffloadPower(p, -1));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> upgrade2());
        addUpgradeData(this, () -> upgrade3(), 1);
    }

    private void upgrade2() {
        upgradeInfo(1);
        exhaust = false;
    }

    private void upgrade3() {
        type = CardType.POWER;
        uDesc();
    }
}