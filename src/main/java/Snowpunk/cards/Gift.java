package Snowpunk.cards;

import Snowpunk.actions.CondenseRandomCardToDrawPileAction;
import Snowpunk.actions.GiftDiscoveryAction;
import Snowpunk.actions.HolidayCheerUpAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.EngineOffloadPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Gift extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Gift.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 5, UP_MAGIC = 3;

    public Gift() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        baseInfo = info = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HolidayCheerUpAction(magicNumber));
        Wiz.atb(new GiftDiscoveryAction(3, info > 0 && exhaust));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
        addUpgradeData(this, () -> upgrade2(), null, new int[]{}, true, new int[]{2});
        addUpgradeData(this, () -> upgradeInfo(2), null, new int[]{}, true, new int[]{1});
    }

    private void upgrade2() {
        upgradeInfo(1);
        exhaust = false;
    }
}