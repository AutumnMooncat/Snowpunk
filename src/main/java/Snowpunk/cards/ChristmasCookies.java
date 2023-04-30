package Snowpunk.cards;

import Snowpunk.actions.ChristmasCookiesAction;
import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.HorizontalThrowEffect;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookies extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChristmasCookies.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 12, HEAL = 4, UP_DMG = 3, UP_HEAL = 2;

    public ChristmasCookies() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        magicNumber = baseMagicNumber = HEAL;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        CardModifierManager.addModifier(this, new EverburnMod());
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new VFXAction(new HorizontalThrowEffect(m.hb.cX, m.hb.cY, Color.GREEN)));
        addToBot(new ChristmasCookiesAction(m, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeMagicNumber(UP_HEAL));
    }
}