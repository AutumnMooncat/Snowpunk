package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class SnowballCore extends CoreCard {
    public static final String ID = makeID(SnowballCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 2;

    public SnowballCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MAGIC, EffectTag.COLD);
        secondMagic = baseSecondMagic = MAGIC;
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        int totalCost = 0;
        for (CoreCard core : coreCards)
            totalCost += core.cost;

        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.HOT)))
            return true;
        return totalCost < 1;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new GainSnowballAction((card.secondMagic)));
    }

    @Override
    public int getUpgradeAmount() {
        return 1;
    }
}
