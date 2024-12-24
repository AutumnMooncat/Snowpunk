package Snowpunk.cards;

import Snowpunk.actions.UpgradeInHandAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import java.util.ArrayList;
import java.util.Collections;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.atb;

public class Toolbox extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Toolbox.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Toolbox() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 3;
        isMultiDamage = true;
//        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new SFXAction("ATTACK_HEAVY"));
//        addToBot(new VFXAction(p, new CleaveEffect(), 0.05F));
//        allDmg(AbstractGameAction.AttackEffect.NONE);

        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped() && mo.currentHealth > 0)
                monsters.add(mo);
        }
        Collections.shuffle(monsters, AbstractDungeon.cardRng.random);
        for (AbstractMonster mo : monsters) {
            Wiz.atb(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, mo.hb.cX, mo.hb.cY), 0.1F));
//            DamageInfo info = new DamageInfo(Wiz.adp(), damage, DamageInfo.DamageType.NORMAL);
//            //info.applyPowers(info.owner, mo);
//            atb(new DamageAction(mo, info, AbstractGameAction.AttackEffect.NONE));
        }
        allDmg(AbstractGameAction.AttackEffect.NONE);

        Wiz.atb(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY), 0.1F));
        Wiz.atb(new WaitAction(.1f));
        Wiz.atb(new ArmamentsAction(true));
        Wiz.atb(new SFXAction("snowpunk:clank"));

//        if (targetEvaporated)
//            Wiz.atb(new AbstractGameAction() {
//                @Override
//                public void update() {
//                    for (AbstractCard card : EvaporatePanel.evaporatePile.group) {
//                        if (card.canUpgrade())
//                            card.upgrade();
//                        card.applyPowers();
//                    }
//                    isDone = true;
//                }
//            });

//        int numGears = getGears();
//        if(numGears > 0){
//            Wiz.applyToSelf(new BrassPower(Wiz.adp(), numGears));
//            Wiz.atb(new UpgradeInHandAction(numGears));
//        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}