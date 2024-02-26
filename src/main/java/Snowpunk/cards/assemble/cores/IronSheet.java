package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class IronSheet extends CoreCard {
    public static final String ID = makeID(IronSheet.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DAMAGE = 7, BLOCK = 7, UP_AMT = 2;

    public IronSheet() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.DMG, EffectTag.BLK);
        target = CardTarget.ENEMY;
        damage = baseDamage = DAMAGE;
        block = baseBlock = BLOCK;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        card.blck();
        card.getModifiedDamageAction(player, monster);
    }

    @Override
    public int getUpgradeAmount() {
        return UP_AMT;
    }

    @Override
    public boolean gearUpgrade() {
        return true;
    }
}
