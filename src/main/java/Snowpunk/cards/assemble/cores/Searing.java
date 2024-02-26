package Snowpunk.cards.assemble.cores;

import Snowpunk.cardmods.DupeMod;
import Snowpunk.cards.assemble.CoreCard;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Searing extends CoreCard {
    public static final String ID = makeID(Searing.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Searing() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MOD);
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(1);
        return turns;
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.baseDamage > 0) ||
                coreCards.stream().anyMatch(coreCard -> coreCard.baseSecondDamage > 0) ||
                coreCards.stream().anyMatch(coreCard -> coreCard.baseBlock > 0) ||
                coreCards.stream().anyMatch(coreCard -> coreCard.baseSecondBlock > 0))
            return false;
        return true;
    }
}
