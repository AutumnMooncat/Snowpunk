package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
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

    private static final int COST = 1, DMG = 11, UP_DMG = 4;

    public Sproinket() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        if (target != null)
            calculateCardDamage(target);
        addToBot(new SFXAction("snowpunk:boing"));

        if (target != null) {
            addToBot(new VFXAction(new FlickCoinEffect(player.hb.cX, player.hb.cY, target.hb.cX, target.hb.cY), 0.3F));
            dmg(target, AbstractGameAction.AttackEffect.NONE);
        }

        //Wiz.applyToSelf(new BrassPower(player, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
    }
}