package Snowpunk.powers;

import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BeaconBeamPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(BeaconBeamPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public BeaconBeamPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("nirvana");
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL || !owner.hasPower(SnowballPower.POWER_ID)) {
            return damage;
        }
        return damage + (this.amount * owner.getPower(SnowballPower.POWER_ID).amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
