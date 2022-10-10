package Snowpunk.cards;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.actions.PlayLinkedCardsInHandAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class HotChocolate extends AbstractEasyCard {
    public final static String ID = makeID(HotChocolate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public HotChocolate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 10;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ChangeChristmasSpiritAction(secondMagic));
        Wiz.applyToSelf(new SnowballPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondMagic(4);
    }
}