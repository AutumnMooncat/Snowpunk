package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class BoltShot extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BoltShot.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int DMG = 12;
    private static final int UP_DMG = 2;
    private static final int DOWN_DMG = -3;
    private static final int VULN = 2;
    private static final int UP_VULN = 1;

    private boolean weak = false;

    public BoltShot() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = VULN;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (weak) {
            Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
        }
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(UP_DMG);
            upgradeMagicNumber(UP_VULN);
        });
        addUpgradeData(() -> {
            upgradeDamage(DOWN_DMG);
            upgradeBaseCost(UP_COST);
        });
        addUpgradeData(() -> {
            weak = true;
            uDesc();
        });
    }
}