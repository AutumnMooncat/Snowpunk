package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ItsColdOutside extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ItsColdOutside.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean FREEZE = false, HAND = false;

    public ItsColdOutside() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, -2);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ModCardTempAction(HAND ? p.hand.size() : 1, FREEZE ? -99 : -1, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() ->
        {
            FREEZE = true;
            uDesc();
            rawDescription = TEXT[0];
            initializeDescription();
        });
        addUpgradeData(() ->
        {
            HAND = true;
            rawDescription = TEXT[1];
            initializeDescription();
        });
        addUpgradeData(() ->
        {
            exhaust = false;
            rawDescription = TEXT[2];
            initializeDescription();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
        setExclusions(0, 1, 2);
        setDependencies(false, 3, 0, 1, 2);
    }
}