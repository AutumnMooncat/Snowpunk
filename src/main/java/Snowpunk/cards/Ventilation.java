package Snowpunk.cards;

import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.Tinkerific;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CopyNextCardPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Ventilation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Ventilation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 8;

    public Ventilation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new GearMod(1));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        blck();
        if (getGears() > 0)
            addToBot(new ExhumeEvaporatedCardAction(getGears(), 0, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(2));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> {
            CardModifierManager.addModifier(this, new Tinkerific());
            uDesc();
        });
    }
}