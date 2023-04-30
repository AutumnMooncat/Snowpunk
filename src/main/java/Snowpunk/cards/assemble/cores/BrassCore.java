package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ApplyCardModifierAction;
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
public class BrassCore extends CoreCard {
    public static final String ID = makeID(BrassCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, MAGIC = 4, UP_MAGIC = 2;

    public BrassCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MAGIC);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new ApplyCardModifierAction(card, new GearMod(magicNumber)));
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.damage > 0 || coreCard.block > 0 || CardModifierManager.hasModifier(coreCard, GearMod.ID)))
            return false;
        return true;
    }

    @Override
    public int getUpgradeAmount() {
        return UP_MAGIC;
    }
}
