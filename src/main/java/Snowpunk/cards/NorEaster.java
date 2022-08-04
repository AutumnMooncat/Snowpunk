package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.actions.ModCardTempEverywhereAction;
import Snowpunk.actions.ModEngineStabilityPointAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class NorEaster extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(NorEaster.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private boolean allCards = false;

    public NorEaster() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (allCards) {
            Wiz.atb(new ModCardTempEverywhereAction(-99));
        } else {
            Wiz.atb(new ModCardTempAction(-1, -99, true));
        }
        Wiz.atb(new ModEngineTempAction(-99));
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