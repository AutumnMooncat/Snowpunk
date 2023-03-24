package Snowpunk.archive;

import Snowpunk.actions.PlayLinkedCardsInHandAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class ChooChoo extends AbstractEasyCard {
    public final static String ID = makeID(ChooChoo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;

    public ChooChoo() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        CardTemperatureFields.addInherentHeat(this, 1);
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new PlayLinkedCardsInHandAction());
    }

    public void upp() {
        upgradeBaseCost(1);
        upgradedCost = true;
    }
}