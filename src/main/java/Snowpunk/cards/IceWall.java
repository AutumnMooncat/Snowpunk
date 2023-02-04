package Snowpunk.cards;

import Snowpunk.cardmods.ClockworkMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.IcePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
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

    private boolean iceOnPlay = false;

    public IceWall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, -2);
        iceOnPlay = false;
    }

    @Override
    public void triggerWhenDrawn() {
        Wiz.applyToSelf(new IcePower(Wiz.adp(), 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (iceOnPlay) {
            Wiz.applyToSelf(new IcePower(Wiz.adp(), 1));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new ClockworkMod()));
        addUpgradeData(this, () -> {
            iceOnPlay = true;
            uDesc();
        });
    }
}