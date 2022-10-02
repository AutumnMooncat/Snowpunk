package Snowpunk.cards;

import Snowpunk.actions.TinkerAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BreakthroughPower;
import Snowpunk.powers.TinkerNextCardPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Breakthrough extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Breakthrough.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int CARDS = 1;
    private static final int UP_CARDS = 1;

    private boolean selfTinker = false;

    public Breakthrough() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = CARDS;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BreakthroughPower(p, magicNumber));
        Wiz.applyToSelf(new TinkerNextCardPower(p, magicNumber));
        if (selfTinker) {
            Wiz.atb(new TinkerAction(this));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> {
            selfTinker = true;
            exhaust = false;
            uDesc();
        });
        addUpgradeData(this, () -> upgradeMagicNumber(UP_CARDS));
    }
}