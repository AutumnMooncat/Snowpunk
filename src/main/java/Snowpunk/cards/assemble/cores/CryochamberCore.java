package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.FreezeNextCardPower;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class CryochamberCore extends CoreCard {
    public static final String ID = makeID(CryochamberCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public CryochamberCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.COLD);
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        if (coreCards.stream().anyMatch(coreCard -> coreCard.effectTags.contains(EffectTag.HOT)))
            return true;
        return false;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        Wiz.applyToSelf(new FreezeNextCardPower(player, 1));
    }

    @Override
    public boolean costUpgrade() {
        return true;
    }
}
