package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BurnPower;
import Snowpunk.util.SteamEngine;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Snowpunk.SnowpunkMod.makeID;

public class Comburstion extends AbstractEasyCard {
    public final static String ID = makeID(Comburstion.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 16;
    private static final int UP_DMG = 6;
    private static final int BURN = 8;

    public Comburstion() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = BURN;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        if (SteamEngine.heat == SteamEngine.OVERHEATED) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new BurnPower(mo, p, magicNumber));
                }
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (SteamEngine.heat == SteamEngine.OVERHEATED) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}