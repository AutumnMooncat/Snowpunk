package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ReturnAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Overblown extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Overblown.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 8, UP_DMG = 1;

    public Overblown() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        int drawAmount = getGears();
        if (drawAmount > 0)
            Wiz.atb(new DrawCardAction(drawAmount));
        Wiz.atb(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(UP_DMG);
            CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        });
        addUpgradeData(() -> {
            upgradeDamage(UP_DMG);
            CardModifierManager.addModifier(this, new GearMod(1));
        });
        addUpgradeData(() -> {
            upgradeDamage(UP_DMG);
            CardModifierManager.addModifier(this, new GearMod(1));
        });
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank(AbstractMonster target) {
        int drawAmount = getGears();
        if (drawAmount > 0)
            addToTop(new DiscardAction(Wiz.adp(), Wiz.adp(), drawAmount, false));
    }

    @Override
    public void unClank(AbstractMonster target) {
        int drawAmount = getGears();
        if (drawAmount > 0)
            addToTop(new ReturnAction(drawAmount));
    }
}