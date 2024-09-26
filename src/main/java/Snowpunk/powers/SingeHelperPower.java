package Snowpunk.powers;

import Snowpunk.powers.interfaces.OnEvaporatePower;
import Snowpunk.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class SingeHelperPower extends AbstractEasyPower implements InvisiblePower {
    public static String POWER_ID = makeID(SingeHelperPower.class.getSimpleName());

    public SingeHelperPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "Singe Helper Power", NeutralPowertypePatch.NEUTRAL, true, owner, amount);
        this.loadRegion("flameBarrier");
        priority = -1;
    }

    @Override
    public void updateDescription() {
        description = "";
    }

    @Override
    public AbstractPower makeCopy() {
        return new SingeHelperPower(owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!owner.hasPower(CharPower.POWER_ID)) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (monster.currentHealth > 0 && !monster.isDeadOrEscaped()) {
                    SingePower singe = (SingePower) monster.getPower(SingePower.POWER_ID);
                    if (singe != null && AbstractDungeon.player.hand.size() > 0)
                        Wiz.atb(new ReducePowerAction(owner, owner, singe, AbstractDungeon.player.hand.size()));
                }
            }
        }
    }
}
