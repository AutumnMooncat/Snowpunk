package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Whistol extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Whistol.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 14, UP_DMG = 3, SELF_DMG = 4, DOWN_DMG = -2;

    public Whistol() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        magicNumber = baseMagicNumber = SELF_DMG;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        Wiz.atb(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(DOWN_DMG));
    }

    @Override
    public void onClank() {
        addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}