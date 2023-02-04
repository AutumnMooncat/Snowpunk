package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.cards.old_cores.AbstractCoreCard;
import Snowpunk.util.UpgradeRunnable;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class StrikeCore extends CoreCard {
    public static final String ID = makeID(StrikeCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 8, UP_DAMAGE = 3;

    public StrikeCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.AB);
        damage = baseDamage = DAMAGE;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.getModifiedDamageAction(player, monster);
    }
}
