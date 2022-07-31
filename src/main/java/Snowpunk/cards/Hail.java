package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Hail extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Hail.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 6;
    private static final int UP_DMG = 3;
    private static final int BONUS = 0;
    private static final int UP_BONUS = 1;

    public Hail() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        baseMagicNumber = magicNumber = BONUS;
        isMultiDamage = true;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
        CardTemperatureFields.addInherentHeat(this, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int snow = getSnow() + magicNumber;
        Wiz.atb(new VFXAction(new BlizzardEffect(snow*3, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        for (int i = 0 ; i < snow ; i++) {
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractMonster mon = AbstractDungeon.getRandomMonster();
                    if (mon != null) {
                        Wiz.att(new ApplyPowerAction(mon, p, new ChillPower(mon, snow), snow, true));
                        Wiz.att(new DamageAction(mon, new DamageInfo(p, multiDamage[AbstractDungeon.getMonsters().monsters.indexOf(mon)], damageTypeForTurn), AttackEffect.BLUNT_LIGHT, true));
                    }
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_BONUS));
        addUpgradeData(this, () -> CardTemperatureFields.addInherentHeat(this, -1));
    }
}