package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.CoreCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class BrassMagnet extends CoreCard {
    public static final String ID = makeID(BrassMagnet.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 0;

    public BrassMagnet() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.POW, EffectTag.CRD);
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(1);
        turns.add(2);
        return turns;
    }

    @Override
    public PowerCondition getPower() {
        return PowerCondition.MOD;
    }
}
