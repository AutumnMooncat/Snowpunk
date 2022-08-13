package Snowpunk.cards.cores;

import Snowpunk.cardmods.BetterExhaustMod;
import Snowpunk.cardmods.cores.FluxcombobulatorMod;
import Snowpunk.cardmods.cores.YoinkMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.util.Triplet;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Yoink extends AbstractCoreCard {
    public static final String ID = makeID(Yoink.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Yoink() {
        super(ID, COST, TYPE, EffectTag.EXHAUSTS, EffectTag.REMOVES_EXHAUST);
        CardModifierManager.addModifier(this, new BetterExhaustMod());
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new YoinkMod(rawDescription, false));
        CardModifierManager.addModifier(card, new BetterExhaustMod());
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.YOINK, false, 0));
        }
    }

    @Override
    public boolean canSpawn(AssembledCard card, ArrayList<AbstractCoreCard> chosenCores) {
        return super.canSpawn(card, chosenCores) && chosenCores.stream().noneMatch(c -> c.effectTags.contains(EffectTag.REMOVES_EXHAUST) && c.effectTags.contains(EffectTag.PURGES));
    }

    @Override
    public void upp() {}
}