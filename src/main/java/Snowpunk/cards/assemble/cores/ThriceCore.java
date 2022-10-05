package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;
/*
@NoPools
@NoCompendium
public class ThriceCore extends CoreCard {
    public static final String ID = makeID(ThriceCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public ThriceCore() {
        super(ID, COST, TYPE, EffectTag.ABMOD);
        exhaust = true;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        if(card.cores.contains(this) && card.cores.indexOf(this) > 0 && card.cores.get(card.cores.indexOf(this) - 1).effectTags.contains(EffectTag.AB))
        {
            card.cores.get(card.cores.indexOf(this) - 1).onUseEffect(player, monster, card);
            card.cores.get(card.cores.indexOf(this) - 1).onUseEffect(player, monster, card);
        }
        else if(card.cores.contains(this) && card.cores.indexOf(this) > 1 && card.cores.get(card.cores.indexOf(this) - 1).effectTags.contains(EffectTag.AMOD) && card.cores.get(card.cores.indexOf(this) - 2).damage > 0)
        {
            card.cores.get(card.cores.indexOf(this) - 2).onUseEffect(player, monster, card);
            card.cores.get(card.cores.indexOf(this) - 2).onUseEffect(player, monster, card);
        }
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
        if (!coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.AB)) ||
                coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.ABMOD)))
            return true;
        return coreCards.stream().anyMatch(coreCard -> coreCard.exhaust);
    }

    @Override
    public void setStats(AbstractCard card) {
        card.exhaust = true;
    }
}
*/