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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ConveyorCore extends CoreCard {
    public static final String ID = makeID(ConveyorCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, MAGIC = 2;

    public ConveyorCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MAGIC, EffectTag.CLANK);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.CLANK)))
            return true;
        return false;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new DrawCardAction(card.magicNumber));
        Wiz.atb(new ClankAction(card));
    }

    @Override
    public void onClank(AssembledCard card) {
        Wiz.att(new DiscardAction(Wiz.adp(), Wiz.adp(), card.magicNumber, false));
    }
}
