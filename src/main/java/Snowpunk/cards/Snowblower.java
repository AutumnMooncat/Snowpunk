package Snowpunk.cards;

import Snowpunk.actions.*;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SnowballPatches;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowblower extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Snowblower.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
        }
        return Tooltip;
    }

    public Snowblower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 6;
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

        int snow = SnowballPatches.Snowballs.getEffectiveAmount();
        if (snow > 0)
            Wiz.atb(new GainEnergyAction(snow));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

//    @Override
//    public void onClank(AbstractMonster target) {
////        addToTop(new ResetExhaustAction(this, true));
////        addToTop(new ModifyDamageAction(uuid, -2));
//        addToTop(new ApplyCardModifierAction(this, new PlateMod(-3)));
//    }

//    @Override
//    public void unClank(AbstractMonster target) {
////        addToTop(new ModifyDamageAction(uuid, 2));
////        addToTop(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
//        addToTop(new ApplyCardModifierAction(this, new PlateMod(3)));
//    }
}