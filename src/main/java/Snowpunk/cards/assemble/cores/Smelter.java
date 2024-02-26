package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.DelayedHookAction;
import Snowpunk.actions.MoveExhaustedCardToEvaporatePileAction;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Smelter extends CoreCard {
    public static final String ID = makeID(Smelter.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Smelter() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.CRD);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.atb(new MoveExhaustedCardToEvaporatePileAction(magicNumber));
    }

    @Override
    public int getUpgradeAmount() {
        return 1;
    }
}
