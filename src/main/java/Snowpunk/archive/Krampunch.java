package Snowpunk.archive;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Krampunch extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Krampunch.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0, DAMAGE = 6, UP_DAMAGE = 2;

    public Krampunch() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DAMAGE));
        addUpgradeData(() -> upgradeDamage(UP_DAMAGE));
        addUpgradeData(() -> upgradeDamage(UP_DAMAGE));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank() {
        addToTop(new ModCardTempAction(this, CardTemperatureFields.HOT));
    }
}