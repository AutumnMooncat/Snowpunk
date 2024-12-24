package Snowpunk.cards;

import Snowpunk.actions.*;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
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

    private static final int COST = -1, DMG = 6, UP_DMG = 2;

    public Snowblower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        CardModifierManager.addModifier(this, new PlateMod(2));
//        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
//        Wiz.atb(new ResetExhaustAction(this, false));
        int effect = energyOnUse;
        if (player.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            player.getRelic("Chemical X").flash();
        }

        //for (int i = 0; i < effect; i++)
//        if (effect > 0)
//            addToBot(new ThrowAttackAction(new DamageInfo(player, damage, damageTypeForTurn), effect, Color.WHITE));
        for (int i = 0; i < effect; i++) {
            Wiz.atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        if (!freeToPlayOnce)
            player.energy.use(EnergyPanel.totalCount);

//        if (magicNumber > 0)
//            Wiz.atb(new GainSnowballAction(magicNumber));

        Wiz.atb(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new PlateMod(1));
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new PlateMod(1));
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new PlateMod(1));
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank(AbstractMonster target) {
//        addToTop(new ResetExhaustAction(this, true));
//        addToTop(new ModifyDamageAction(uuid, -2));
        int plating = -1;
        PlateMod plateMod = (PlateMod) CardModifierManager.getModifiers(this, PlateMod.ID).get(0);
        if (plateMod != null)
            plating = plateMod.amount;
        if (plating > 0)
            addToTop(new ApplyCardModifierAction(this, new PlateMod(-plating)));
    }

    @Override
    public void unClank(AbstractMonster target) {
//        addToTop(new ModifyDamageAction(uuid, 2));
//        addToTop(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
        int plating = -1;
        PlateMod plateMod = (PlateMod) CardModifierManager.getModifiers(this, PlateMod.ID).get(0);
        if (plateMod != null)
            plating = plateMod.amount;
        if (plating > 0)
            addToTop(new ApplyCardModifierAction(this, new PlateMod(plating)));
    }
}