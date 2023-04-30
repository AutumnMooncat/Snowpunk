package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium

public class PierceCore extends CoreCard {
    public static final String ID = makeID(PierceCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 12, UP_DAMAGE = 3;

    public PierceCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.AB, EffectTag.ALLDmg);
        damage = baseDamage = DAMAGE;
        target = CardTarget.ALL_ENEMY;
        isMultiDamage = true;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.getModifiedDamageAction(player, monster);
    }

    @Override
    public boolean getCustomCANTSpawnCondition(ArrayList<CoreCard> coreCards) {
        return coreCards.size() < 1;
    }

    @Override
    public int getUpgradeAmount() {
        return UP_DAMAGE;
    }
}
