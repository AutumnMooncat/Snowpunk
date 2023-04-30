package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class RollCore extends CoreCard {
    public static final String ID = makeID(RollCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 8, UP_BLOCK = 2;

    public RollCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.AB);
        block = baseBlock = BLOCK;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.blck();
        Wiz.applyToSelf(new NextTurnBlockPower(player, card.block));
    }

    @Override
    public int getUpgradeAmount() {
        return UP_BLOCK;
    }
}
