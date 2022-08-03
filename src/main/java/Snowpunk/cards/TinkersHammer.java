package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
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

    private boolean selfTinker = false;

    public TinkersHammer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (selfTinker) {
            Wiz.atb(new TinkerAction(this));
        } else {
            Wiz.atb(new TinkerAction(1, false));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            selfTinker = true;
            exhaust = false;
            uDesc();
        }, new int[]{}, new int[]{1});
        addUpgradeData(this, () -> {
            exhaust = false;
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }, new int[]{}, new int[]{0});
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
    }

    @Override
    public void onObtain() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.closeCurrentScreen();
        }
        AbstractDungeon.effectsQueue.add(new SmithEffect());

    }
}