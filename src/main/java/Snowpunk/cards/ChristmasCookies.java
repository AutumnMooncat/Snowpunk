package Snowpunk.cards;

import Snowpunk.actions.ChristmasCookiesAction;
import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.HorizontalThrowEffect;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ChristmasCookies extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChristmasCookies.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SINGE = 3, TIMES = 3, UP = 1;

    public ChristmasCookies() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = SINGE;
        secondMagic = baseSecondMagic = TIMES;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        //addToBot(new VFXAction(new HorizontalThrowEffect(m.hb.cX, m.hb.cY, Color.GREEN)));
        //addToBot(new ChristmasCookiesAction(m, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), magicNumber));

        for (int i = 0; i < secondMagic; i++) {
            AbstractMonster mo = AbstractDungeon.getRandomMonster();
            if (mo != null && mo.hb != null && mo.currentHealth > 0) {
                addToBot(new VFXAction(new HorizontalThrowEffect(mo.hb.cX, mo.hb.cY, Color.GREEN)));
                Wiz.applyToEnemy(mo, new SingePower(mo, player, magicNumber));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> upgradeSecondMagic(UP));
    }
}