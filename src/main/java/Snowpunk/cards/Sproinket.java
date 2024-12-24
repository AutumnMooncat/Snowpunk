package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.WindupPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Sproinket extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Sproinket.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 6, UP_DMG = 2;

    public Sproinket() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
//        AbstractMonster target = AbstractDungeon.getRandomMonster();
//        if (target != null) {
        calculateCardDamage(m);
        addToTop(new SFXAction("snowpunk:boing"));
        addToBot(new VFXAction(new FlickCoinEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY, m.hb.cX, m.hb.cY), 0.3F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
//        }
        int gears = getGears();
        if (gears > 0)
            Wiz.applyToSelf(new BrassPower(player, gears));
        Wiz.applyToSelf(new WindupPower(Wiz.adp(), 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
    }
}