package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SingePower;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class ThermalShock extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ThermalShock.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BURN = 5;
    private static final int UP_BURN = 2;
    private static final int CHILL = 2;

    public ThermalShock() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = BURN;
        info = baseInfo = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                Wiz.applyToEnemy(mo, new SingePower(mo, p, magicNumber));
                Wiz.applyToEnemy(mo, new ChillPower(mo, magicNumber));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_BURN));
        //addUpgradeData(() -> CardModifierManager.addModifier(this, new FluxMod()));
        //addUpgradeData(() -> upgrade3(), 1);
    }
/*
    private void upgrade3() {
        CardTemperatureFields.addInherentHeat(this, 1);
        exhaust = false;
        upgradeInfo(1);
    }*/
}