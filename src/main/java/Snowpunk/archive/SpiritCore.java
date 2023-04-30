package Snowpunk.archive;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;
/*
@NoPools
@NoCompendium
public class SpiritCore extends CoreCard {
    public static final String ID = makeID(SpiritCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 6;

    public SpiritCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MAGIC);
        secondMagic = baseSecondMagic = MAGIC;
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        return turns;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new ChangeChristmasSpiritAction(card.secondMagic));
    }
}
*/