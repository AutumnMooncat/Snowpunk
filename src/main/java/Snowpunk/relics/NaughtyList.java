package Snowpunk.relics;

import Snowpunk.TheConductor;
import Snowpunk.util.Wiz;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;

import static Snowpunk.SnowpunkMod.makeID;

public class NaughtyList extends AbstractEasyRelic implements OnApplyPowerRelic {
    public static final String ID = makeID(NaughtyList.class.getSimpleName());
    public static final int AMOUNT = 1;

    public NaughtyList() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheConductor.Enums.SNOWY_BLUE_COLOR);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void atBattleStart() {
        counter = 3;
    }

    @Override
    public void onVictory() {
        counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && target != Wiz.adp() && counter > 0 && !target.hasPower(ArtifactPower.POWER_ID)) {
            counter--;
            if (power instanceof CloneablePowerInterface) {
                AbstractPower newPower = ((CloneablePowerInterface) power).makeCopy();
                newPower.owner = Wiz.adp();
                addToBot(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), newPower));
            } else
                addToBot(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new FrailPower(Wiz.adp(), 1, false)));
            flash();
        }
        return true;
    }
}
