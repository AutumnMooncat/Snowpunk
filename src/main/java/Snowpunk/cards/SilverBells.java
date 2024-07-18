package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SilverBells extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SilverBells.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 6;

    public SilverBells() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardModifierManager.addModifier(this, new GearMod(1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();
        if (getGears() > 0)
            Wiz.applyToSelf(new BlurPower(p, getGears()));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
    }
}