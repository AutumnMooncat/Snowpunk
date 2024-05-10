package Snowpunk.cards;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.actions.MultDebuffsAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static Snowpunk.SnowpunkMod.makeID;

public class BlackFlash extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BlackFlash.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DAMAGE = 6, UP_DAMAGE = 3;

    public BlackFlash() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m) {
        if (isMultiDamage) {
            allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (mo == null || mo.isDeadOrEscaped() || mo.currentHealth <= 0)
                    continue;
                Wiz.atb(new MultDebuffsAction(mo, magicNumber));
            }
        } else {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            if (m == null || m.isDeadOrEscaped() || m.currentHealth <= 0)
                return;
            Wiz.atb(new MultDebuffsAction(m, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DAMAGE));
        addUpgradeData(() -> {
            isMultiDamage = true;
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(1));
    }
}