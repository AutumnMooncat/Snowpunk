package Snowpunk.cards;

import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class GoldenTicket extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(GoldenTicket.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean freeUpgrade = false;

    public GoldenTicket() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MakeCopyInHandAction(freeUpgrade, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            freeUpgrade = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> {
            exhaust = false;
            upgradeInfo(1);
        });
    }
}