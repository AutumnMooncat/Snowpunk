package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class SteamTrain extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SteamTrain.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 4, DMG = 32, UP_DMG = 8, UP_DMG2 = 10, STEAM = 1;

    public SteamTrain() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = STEAM;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToSelf(new SteamPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 1, 0);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 2, 1);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 3, 2);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 4, 3);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 5, 4);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 6, 5);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 7, 6);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 8, 7);
        addUpgradeData(() -> upgradeDamage(UP_DMG2));
        setDependencies(true, 9, 8);
    }
}