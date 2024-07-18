package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class AnotherAnvil extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(AnotherAnvil.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    public AnotherAnvil() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 18;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            addToBot(new WaitAction(0.8F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        Wiz.atb(new ApotheosisAction());
        Wiz.atb(new UpgradeSpecificCardAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(6));
        addUpgradeData(() -> upgradeDamage(6));
        addUpgradeData(() -> upgradeDamage(6));
        addUpgradeData(() -> upgradeDamage(6));
        addUpgradeData(() -> upgradeDamage(6));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
        setDependencies(true, 3, 2);
        setDependencies(true, 4, 3);
    }
}