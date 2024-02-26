package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CharPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Overheated extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Overheated.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 8, SINGE = 3;
    private boolean aoe;

    public Overheated() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = SINGE;
        secondMagic = baseSecondMagic = 1;
        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT);
        aoe = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (aoe) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                dmg(mo, AbstractGameAction.AttackEffect.FIRE);
                Wiz.applyToEnemy(mo, new SingePower(mo, p, magicNumber));
                Wiz.applyToEnemy(mo, new CharPower(mo, secondMagic));
            }
        } else {
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
            Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));
            Wiz.applyToEnemy(m, new CharPower(m, secondMagic));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            upgradeDamage(3);
            upgradeMagicNumber(1);
        });
        addUpgradeData(() ->
        {
            target = CardTarget.ALL_ENEMY;
            aoe = true;
            isMultiDamage = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeSecondMagic(1));
    }
}