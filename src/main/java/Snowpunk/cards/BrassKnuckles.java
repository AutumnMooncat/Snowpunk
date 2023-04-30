package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassKnuckles extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(BrassKnuckles.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 5;

    private boolean third = false;
    private boolean burn = false;
    private boolean chill = false;

    public BrassKnuckles() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(2)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> {
            magicNumber = baseMagicNumber = 0;
            upgradeMagicNumber(2);
            uDesc();
        });
        setExclusions(1, 2);

    }

    @Override
    public void onClank() {
        addToTop(new ApplyCardModifierAction(this, new GearMod(magicNumber > 0 ? -magicNumber : -99999)));
    }
}