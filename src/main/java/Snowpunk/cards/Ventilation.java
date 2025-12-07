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
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Ventilation extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Ventilation.class.getSimpleName());

    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("Evaporate")).TEXT;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BLOCK = 12;

    public Ventilation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        blck();
        int numGears = getGears();
        if (numGears > 0) {
            Wiz.atb(new BetterSelectCardsCenteredAction(Wiz.adp().discardPile.group, numGears, TEXT[0] + numGears + TEXT[1], true, card -> true, cards -> {
                for (AbstractCard c : cards)
                    EvaporatePanel.Evaporate(c);
            }));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> upgradeBlock(4));
    }
}