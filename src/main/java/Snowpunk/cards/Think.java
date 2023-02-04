package Snowpunk.cards;

import Snowpunk.actions.MultiUpgradeInHandAction;
import Snowpunk.actions.UpgradeInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Think extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Think.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 5, UPG_BLOCK = 3;
    private static final int TINKER = 1;
    private static final int UP_TINKER = 1;

    private boolean tinkerSelf = false;

    public Think() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = TINKER;
        secondMagic = baseSecondMagic = 1;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new MultiUpgradeInHandAction(magicNumber, secondMagic));
        /*if (tinkerSelf) {
            Wiz.atb(new TinkerAction(this));
        } else {
            Wiz.atb(new TinkerAction(magicNumber, false));
        }*/
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(() -> upgradeSecondMagic(1));
        addUpgradeData(() -> upgradeSecondMagic(1));
        setDependencies(true, 2, 1);
    }
}