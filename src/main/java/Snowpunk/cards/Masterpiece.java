package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Masterpiece extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Masterpiece.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 50;
    private static final int UP_DMG = 10;

    public Masterpiece() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}