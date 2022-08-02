package Snowpunk.cards;

import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TrainStation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TrainStation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private boolean ChooChoo = false;

    private static final int COST = 1, UP_COST = 0, FIRE = 2, UP_FIRE = 1, RESHUFFLE = 1;

    public TrainStation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        cardToPreview.add(new ChuggaChugga());
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new ChuggaChugga(), 1, false));
        if (ChooChoo)
            addToBot(new MakeTempCardInHandAction(new ChooChoo(), 1, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> upgrade2());
        addUpgradeData(this, () -> upgrade3());
    }

    private void upgrade2() {
        upgradeInfo(1);
        exhaust = true;
    }

    private void upgrade3() {
        cardToPreview.add(new ChooChoo());
        ChooChoo = true;
        uDesc();
    }
}