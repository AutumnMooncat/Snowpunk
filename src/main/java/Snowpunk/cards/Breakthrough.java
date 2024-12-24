package Snowpunk.cards;

import Snowpunk.actions.*;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.damageMods.BrassDamage;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Breakthrough extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Breakthrough.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Breakthrough() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
//        Wiz.atb(new IncreasePlatingAndGearsAction(false, -1, magicNumber));
//        Wiz.atb(new ClankAction(this));

        Wiz.atb(new GearCardsToHandAction());
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new HatMod());
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new HatMod());
        });
        addUpgradeData(() -> {
            upgradeDamage(1);
            CardModifierManager.addModifier(this, new HatMod());
        });
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }

//    @Override
//    public void onClank(AbstractMonster target) {
//        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
//        if(Wiz.adp().hand.size() > 0)
//            Wiz.att(new TransformCardInHandAction(AbstractDungeon.cardRandomRng.random(Wiz.adp().hand.size() - 1), c));
//    }
//
//    @Override
//    public void unClank(AbstractMonster target) {
//        Wiz.att(new GiftDiscoveryAction(3, false, false));
//    }
}