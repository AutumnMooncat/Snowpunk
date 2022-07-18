package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.actions.TinkerAction;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ProtectionPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class IceWall extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(IceWall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 4;
    private static final int UP_COST = 3;

    private boolean freeze = false;

    public IceWall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerWhenDrawn() {
        Wiz.applyToSelf(new ProtectionPower(Wiz.adp(), 1));
        if (freeze) {
            Wiz.atb(new ModEngineTempAction(-99));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new FrostMod()));
        addUpgradeData(this, () -> {
            freeze = true;
            uDesc();
        });
    }
}