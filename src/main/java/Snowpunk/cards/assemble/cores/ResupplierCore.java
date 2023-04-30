package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.UpgradeRandomCardsAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ResupplierCore extends CoreCard {
    public static final String ID = makeID(ResupplierCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, GEARS = 2, UP_GEARS = 1;

    public ResupplierCore() {
        super(ID, COST, TYPE, EffectTag.CORE);
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        int drawAmount = card.getGears();
        if (drawAmount > 0)
            Wiz.atb(new DrawCardAction(drawAmount));
    }

    @Override
    public int getUpgradeAmount() {
        return UP_GEARS;
    }

    @Override
    public boolean gearUpgrade() {
        return true;
    }
}
