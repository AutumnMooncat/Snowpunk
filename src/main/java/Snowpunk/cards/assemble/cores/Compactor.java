package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ExhaustSpecificCardAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.AssembledPower;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Compactor extends CoreCard {
    public static final String ID = makeID(Compactor.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;

    public Compactor() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.POST_POW);
        secondMagic = baseSecondMagic = 3;
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(2);
        return turns;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new ApplyPowerAction(player, player, new AssembledPower(player, secondMagic, card.cores.get(0).magicNumber, card)));
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (!coreCards.get(coreCards.size() - 1).effectTags.contains(EffectTag.POW) || !coreCards.get(coreCards.size() - 1).effectTags.contains(EffectTag.CRD)
                || coreCards.get(coreCards.size() - 1) instanceof BrassMagnet)
            return true;
        return false;
    }

    @Override
    public void onPowerTrigger(AbstractCard card, int amount) {
        Wiz.atb(new ExhaustSpecificCardAction(card));
        Wiz.applyToSelf(new BrassPower(AbstractDungeon.player, amount));
    }

    @Override
    public void onPowerTrigger(AbstractCard card, int amount, UseCardAction action) {
        action.exhaustCard = true;
        Wiz.applyToSelf(new BrassPower(AbstractDungeon.player, amount));
    }

    @Override
    public String powerIcon() {
        return "modeShift";
    }

    @Override
    public String[] getExtended() {
        return TEXT;
    }

    @Override
    public int getUpgradeAmount() {
        return 1;
    }
}
