package Snowpunk.cards.assemble.cores;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.WidgetsPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class TinkererCore extends CoreCard {
    public static final String ID = makeID(TinkererCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, GEARS = 2, UP_GEARS = 2;

    public TinkererCore() {
        super(ID, COST, TYPE, EffectTag.CORE);
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        int gears = card.getGears();
        if (gears > 0)
            Wiz.applyToSelf(new WidgetsPower(player, gears));
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        return turns;
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
