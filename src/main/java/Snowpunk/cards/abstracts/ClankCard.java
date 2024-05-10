package Snowpunk.cards.abstracts;

import Snowpunk.powers.PermWrenchPower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.WrenchEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface ClankCard {
    public abstract void onClank(AbstractMonster monster);
}
