package Snowpunk.cards;

import Snowpunk.actions.GainBlockEqualToDebuffAction;
import Snowpunk.cardmods.FlaminMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.damageMods.CauterizeDamage;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Scald extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Scald.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    public Scald() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 10;
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CardTemperatureFields.getExpectedCardHeatWhenPlayed(this) > 0)
            addToBot(new DamageAction(m, BindingHelper.makeInfo(this, p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        else
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        Wiz.applyToEnemy(m, new SingePower(m, magicNumber));
        Wiz.atb(new GainBlockEqualToDebuffAction(SingePower.POWER_ID, m));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(2);
            upgradeMagicNumber(1);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> {
            upgradeDamage(2);
            upgradeMagicNumber(1);
        });
        setDependencies(true, 2, 0);
    }
}