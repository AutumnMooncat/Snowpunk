package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.cards.abstracts.NonClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class ArtilleryCargo extends AbstractMultiUpgradeCard implements ClankCard, NonClankCard {
    public final static String ID = makeID(ArtilleryCargo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;
    private static final int DMG = 40;
    private static final int UP_DMG = 10;

    public ArtilleryCargo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ClankAction(this, m));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
    }

    @Override
    public void onClank(AbstractMonster monster) {
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        if (target != null)
            calculateCardDamage(target);

        if (target != null)
            dmgTop(target, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onNonClank(AbstractMonster target) {
        if (target != null)
            calculateCardDamage(target);

        if (target != null)
            dmgTop(target, AbstractGameAction.AttackEffect.FIRE);
    }
}