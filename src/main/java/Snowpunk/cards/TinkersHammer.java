package Snowpunk.cards;

import Snowpunk.actions.MultiUpgradeInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnObtainCard;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.SmithEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TinkersHammer extends AbstractMultiUpgradeCard implements OnObtainCard {
    public final static String ID = makeID(TinkersHammer.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public TinkersHammer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MultiUpgradeInHandAction(magicNumber, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        setDependencies(true, 2, 0, 1);
    }

    @Override
    public void onObtain() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.closeCurrentScreen();
        }
        AbstractDungeon.effectsQueue.add(new SmithEffect());

    }
}