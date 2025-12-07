package Snowpunk.cards.assemble.cores;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Winder extends CoreCard {
    public static final String ID = makeID(Winder.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Winder() {
        super(ID, COST, TYPE, EffectTag.DMG, EffectTag.BUF);
        damage = baseDamage = 9;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        CardGroup gCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (CardModifierManager.hasModifier(c, GearMod.ID) && ((GearMod) CardModifierManager.getModifiers(c, GearMod.ID).get(0)).amount > 0)
                gCards.addToTop(c);
        }
        if (gCards.size() > 0)
            Wiz.atb(new ApplyCardModifierAction(gCards, gCards.size() + 1, new GearMod(1)));
    }

    @Override
    public int getUpgradeAmount() {
        return 3;
    }
}
