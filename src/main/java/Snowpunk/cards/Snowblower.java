package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.actions.ThrowAttackAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowblower extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Snowblower.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1, DMG = 9, UP_DMG = 3;

    public Snowblower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        Wiz.atb(new ResetExhaustAction(this, false));
        int effect = energyOnUse;
        if (player.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            player.getRelic("Chemical X").flash();
        }

        //for (int i = 0; i < effect; i++)
        if (effect > 0)
            addToBot(new ThrowAttackAction(new DamageInfo(player, damage, damageTypeForTurn), effect, Color.WHITE));

        if (!freeToPlayOnce)
            player.energy.use(EnergyPanel.totalCount);

        if (magicNumber > 0)
            Wiz.atb(new GainSnowballAction(magicNumber));

        Wiz.atb(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank(AbstractMonster monster) {
        addToTop(new ResetExhaustAction(this, true));
    }
}