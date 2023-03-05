package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.WidgetsPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Sproinket extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Sproinket.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 9, GEAR = 3, UP_DMG = 4, UP_GEAR = 2;

    public Sproinket() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        magicNumber = baseMagicNumber = GEAR;
        exhaust = true;
        CardModifierManager.addModifier(this, new GearMod(0));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        calculateCardDamage(target);
        addToBot(new SFXAction("snowpunk:boing"));
        addToBot(new VFXAction(new FlickCoinEffect(player.hb.cX, player.hb.cY, target.hb.cX, target.hb.cY), 0.3F));

        dmg(target, AbstractGameAction.AttackEffect.NONE);
        Wiz.applyToSelf(new WidgetsPower(player, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_GEAR));
    }
}