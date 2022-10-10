package Snowpunk.cards;

import Snowpunk.actions.ChangeCostAction;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class PolarExpress extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(PolarExpress.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 8, UP_COST = 6, MAGIC = 2, UP_MAGIC = 1;

    boolean costUp = false;

    public PolarExpress() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        info = baseInfo = COST;
        costUp = false;
        CardTemperatureFields.addInherentHeat(this, -1);
        CardModifierManager.addModifier(this, new FrostMod(true));
        cardToPreview.add(new HotChocolate());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new HotChocolate(), magicNumber, false));
        Wiz.atb(new ChangeCostAction(this, info));
    }


    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeBaseCost(UP_COST);
            upgradeInfo(UP_COST - COST);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(UP_MAGIC));
    }
}