package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.DoublePowersAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Capsule extends CoreCard {
    public static final String ID = makeID(Capsule.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Capsule() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.CRD, EffectTag.CLK, EffectTag.BUF);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new GainEnergyAction(magicNumber));
        Wiz.atb(new ClankAction(card));
    }

    @Override
    public void onClank(AssembledCard card) {
        Wiz.att(new GainSnowballAction(-1));
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(1);
        return turns;
    }

    @Override
    public int getUpgradeAmount() {
        return 1;
    }
}
