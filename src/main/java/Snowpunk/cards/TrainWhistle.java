package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.TrainWhistlePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TrainWhistle extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TrainWhistle.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2, GEARS = 4, UP_GEARS = 2;

    private boolean upgradeDraw = false;

    public TrainWhistle() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int gears = getGears();
        if (gears > 0)
            Wiz.applyToSelf(new TrainWhistlePower(p, gears));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(UP_GEARS)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeBaseCost(1));
        setDependencies(true, 2, 0, 1);
    }
}