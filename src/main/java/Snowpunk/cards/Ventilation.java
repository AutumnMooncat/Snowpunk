package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.Tinkerific;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CopyNextCardPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Ventilation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Ventilation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 11;

    public Ventilation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 1;
        CardModifierManager.addModifier(this, new GearMod(1));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        blck();
        if (getGears() > 0) {
            Wiz.atb(new BetterSelectCardsCenteredAction(Wiz.adp().discardPile.group, getGears(), "", true, card -> true, cards -> {
                for (AbstractCard c : cards)
                    EvaporatePanel.Evaporate(c);
            }));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> upgradeBlock(3));
        setDependencies(true, 2, 1);
    }
}