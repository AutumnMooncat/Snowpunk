package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.patches.NegativeCostFieldPatches;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class AllEnemiesCore extends CoreCard {
    public static final String ID = makeID(AllEnemiesCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public AllEnemiesCore() {
        super(ID, COST, TYPE, EffectTag.MOD);
        target = CardTarget.ALL_ENEMY;
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        return turns;
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.get(coreCards.size() - 1).damage < 1)
            return true;
        return false;
    }
}
