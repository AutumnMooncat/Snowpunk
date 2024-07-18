package Snowpunk.cards.assemble.cores;

import Snowpunk.cardmods.DesperationMod;
import Snowpunk.cardmods.DualstrikeMod;
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
public class Swift extends CoreCard {
    public static final String ID = makeID(Swift.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    public Swift() {
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
                coreCards.stream().anyMatch(coreCard -> coreCard.baseSecondDamage > 0))
            return false;
        return true;
    }

    @Override
    public void setStats(AbstractCard card) {
        CardModifierManager.addModifier(card, new DualstrikeMod());
    }
}
