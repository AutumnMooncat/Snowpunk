package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.EvaporatePanelPatches;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BurningFury extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BurningFury.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0, FIRE = 1, UP_FIRE = 1, RESHUFFLE = 1;


    public BurningFury() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = FIRE;
        EvaporatePanelPatches.EvaporateField.evaporate.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ModEngineTempAction(2));
        Wiz.applyToSelf(new FireballPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            CardTemperatureFields.addInherentHeat(this, 1);
            EvaporatePanelPatches.EvaporateField.evaporate.set(this, false);
            uDesc();
        });
        addUpgradeData(this, () -> upgradeMagicNumber(UP_FIRE));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new VentMod()));
    }
}