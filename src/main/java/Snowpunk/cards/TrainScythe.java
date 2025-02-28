package Snowpunk.cards;

import Snowpunk.cardmods.*;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class TrainScythe extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TrainScythe.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 6, UP_DMG = 2;

    public boolean targetEvaporated;

    public TrainScythe() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        targetEvaporated = false;
        CardModifierManager.addModifier(this, new Tinkerific());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.05F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.05F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

//        Wiz.atb(new ArmamentsAction(true));
//
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
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> upgradeDamage(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        setDependencies(true, 1, 0);
    }
}