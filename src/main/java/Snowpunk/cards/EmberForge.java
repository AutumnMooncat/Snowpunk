package Snowpunk.cards;

import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.EmberForgePower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class EmberForge extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EmberForge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 2, UP_COST2 = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 99;

    private boolean overheat;

    public EmberForge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ModEngineTempAction(magicNumber));
        Wiz.applyToSelf(new EmberForgePower(p));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgradeBaseCost(UP_COST2), 0);
        addUpgradeData(() -> {
            upgradeMagicNumber(UP_EFFECT);
            uDesc();
        });
    }
}