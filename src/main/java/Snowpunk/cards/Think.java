package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.actions.TinkerActionOLD;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.TinkerNextCardPower;
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

    private static final int COST = 1, BLOCK = 6, UPG_BLOCK = 3;
    private static final int TINKER = 1;
    private static final int UP_TINKER = 1;

    private boolean tinkerSelf = false;

    public Think() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = TINKER;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (tinkerSelf) {
            Wiz.atb(new TinkerAction(this));
        } else {
            Wiz.atb(new TinkerAction(magicNumber, false));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_TINKER), new int[]{}, new int[]{2});
        addUpgradeData(this, () -> {
            tinkerSelf = true;
            uDesc();
        }, new int[]{}, new int[]{1});
    }
}