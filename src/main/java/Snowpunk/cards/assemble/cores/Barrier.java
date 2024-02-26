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
public class Barrier extends CoreCard {
    public static final String ID = makeID(Barrier.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 6, UP_BLOCK = 2;

    public Barrier() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.BLK);
        block = baseBlock = BLOCK;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.blck();
        card.blck();
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.size() > 0 && coreCards.get(coreCards.size() - 1).effectTags.contains(EffectTag.POW))
            return true;
        return false;
    }

    @Override
    public int getUpgradeAmount() {
        return UP_BLOCK;
    }
}
