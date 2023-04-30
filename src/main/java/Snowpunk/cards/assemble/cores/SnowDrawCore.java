package Snowpunk.cards.assemble.cores;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class SnowDrawCore extends CoreCard {
    public static final String ID = makeID(SnowDrawCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public SnowDrawCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.COLD);
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
        int drawAmount = getSnow();
        if (drawAmount > 0)
            Wiz.atb(new DrawCardAction(drawAmount));
    }
}
