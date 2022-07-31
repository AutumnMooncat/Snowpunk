package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModCardTempEverywhereAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Armageddon extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Armageddon.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 3;
    private static final int UP_COST = 2;

    private boolean allCards = false;

    public Armageddon() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseInfo = info = 0;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (allCards) {
            Wiz.atb(new ModCardTempEverywhereAction(99));
        } else {
            Wiz.atb(new ModCardTempAction(-1, 99, true));
        }
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            this.isInnate = true;
            this.baseInfo = this.info = 1;
        });
        addUpgradeData(this, () -> {
            allCards = true;
            uDesc();
        });
    }
}