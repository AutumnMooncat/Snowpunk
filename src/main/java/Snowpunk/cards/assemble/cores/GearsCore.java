package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.actions.UpgradeRandomCardsAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
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
public class GearsCore extends CoreCard {
    public static final String ID = makeID(GearsCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, GEARS = 6, UP_GEARS = 3;

    public GearsCore() {
        super(ID, COST, TYPE, EffectTag.CORE);
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.damage > 0 || coreCard.block > 0 || CardModifierManager.hasModifier(coreCard, GearMod.ID))) {
            if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.CLANK)))
                return true;
            else
                return false;
        }
        return true;
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(1);
        return turns;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new ClankAction(card));
    }

    @Override
    public int getUpgradeAmount() {
        return UP_GEARS;
    }

    @Override
    public boolean gearUpgrade() {
        return true;
    }

    @Override
    public void onClank(AssembledCard card) {
        addToTop(new ResetExhaustAction(card, true));
    }
}
