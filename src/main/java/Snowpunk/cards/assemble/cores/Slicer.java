package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Slicer extends CoreCard {
    public static final String ID = makeID(Slicer.class.getSimpleName());

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 7, UP_DAMAGE = 2;

    public Slicer() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.DMG);
        target = CardTarget.ENEMY;
        damage = baseDamage = DAMAGE;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.getModifiedDamageAction(player, monster);
        card.getModifiedDamageAction(player, monster);
    }

    @Override
    public int getUpgradeAmount() {
        return UP_DAMAGE;
    }
}
