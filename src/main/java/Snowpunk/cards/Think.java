package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
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

    public Think() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        baseMagicNumber = magicNumber = TINKER;
        info = baseInfo = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (int i = 0 ; i < magicNumber ; i++) {
            Wiz.atb(new TinkerAction());
        }
        if (info == 1) {
            Wiz.applyToSelf(new TinkerNextCardPower(p, 1, true));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_TINKER));
        addUpgradeData(this, () -> upgradeInfo(1));
    }
}