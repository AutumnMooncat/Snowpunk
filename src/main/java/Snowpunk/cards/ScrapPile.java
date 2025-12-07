package Snowpunk.cards;

import Snowpunk.actions.*;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ScrapPile extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(ScrapPile.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 6, BLOCK = 6;

    public ScrapPile() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        block = baseBlock = BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        exhaust = false;
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

        addToBot(new ClankAction(this, m));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeBlock(2);
            upgradeDamage(2);
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }


    @Override
    public void onClank(AbstractMonster target) {
        addToTop(new ResetExhaustAction(this, true));
    }

    @Override
    public void unClank(AbstractMonster target) {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }
}