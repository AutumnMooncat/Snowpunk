package Snowpunk.cards;

import Snowpunk.actions.ScatterDamageAction;
import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.IgnoreEnemyPowersPatches;
import Snowpunk.powers.BurnPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class BuckShot extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BuckShot.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 18;
    private static final int UP_DMG = 6;
    private static final int BURN = 4;

    public BuckShot() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        info = baseInfo = 0;
        IgnoreEnemyPowersPatches.IgnoreField.ignore.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ScatterDamageAction(this, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, damageMap -> {
            if (info > 0) {
                for (AbstractMonster mon : damageMap.keySet()) {
                    Wiz.applyToEnemy(mon, new BurnPower(mon, p, magicNumber));
                }
            }
        }));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(this, () -> {
            upgradeInfo(1);
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(BURN);
            uDesc();
        });
    }
}