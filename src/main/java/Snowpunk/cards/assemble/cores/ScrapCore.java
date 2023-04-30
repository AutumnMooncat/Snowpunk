package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.UpgradeRandomCardsAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ScrapCore extends CoreCard {
    public static final String ID = makeID(ScrapCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, GEARS = 3, UP_GEARS = 2;

    public ScrapCore() {
        super(ID, COST, TYPE, EffectTag.CORE);
        CardModifierManager.addModifier(this, new GearMod(GEARS));
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        int gears = card.getGears();
        if (gears > 0)
            Wiz.atb(new UpgradeRandomCardsAction(gears));
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
