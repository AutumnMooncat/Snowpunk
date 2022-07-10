package Snowpunk.cards;

import Snowpunk.actions.ScatterDamageAction;
import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BuckShot extends AbstractEasyCard {
    public final static String ID = makeID(BuckShot.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int UP_DMG = 2;
    private static final int HITS = 4;

    public BuckShot() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        CardModifierManager.addModifier(this, new WhistolMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ScatterDamageAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}