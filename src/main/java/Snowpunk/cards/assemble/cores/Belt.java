package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ClankAction;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Belt extends CoreCard {
    public static final String ID = makeID(Belt.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, MAGIC = 2;

    public Belt() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MGC, EffectTag.CLK);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new DrawCardAction(card.magicNumber));
        Wiz.atb(new ClankAction(card));
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
        if (coreCards.size() > 0 && coreCards.get(coreCards.size() - 1).effectTags.contains(EffectTag.POW))
            return true;
        return false;
    }

    @Override
    public void onClank(AssembledCard card) {
        Wiz.att(new DiscardAction(Wiz.adp(), Wiz.adp(), card.magicNumber, false));
    }
}
