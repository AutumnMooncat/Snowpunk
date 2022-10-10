package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.CustomTags;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowblower extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Snowblower.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1, DMG = 13, UP_DMG = 3;

    public Snowblower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        exhaust = true;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < getSnow(); i++)
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}