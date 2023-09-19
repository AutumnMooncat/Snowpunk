package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Weld extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Weld.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 6, GEARS = 3;

    public Weld() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        int gears = getGears();
        if (magicNumber > 0)
            gears += magicNumber;
        if (gears > 0)
            Wiz.applyToEnemy(m, new SingePower(m, p, gears));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(4));
        addUpgradeData(() -> {
            magicNumber = baseMagicNumber = 0;
            upgradeMagicNumber(2);
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
    }
}