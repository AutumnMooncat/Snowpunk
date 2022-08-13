package Snowpunk.cards.cores;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cardmods.cores.QuickBlastMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.cardmods.parts.DrawMod;
import Snowpunk.util.Triplet;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class QuickBlast extends AbstractCoreCard {
    public static final String ID = makeID(QuickBlast.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final EffectTag VALUE = EffectTag.DAMAGE;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    public static final int UP_DAMAGE = 3;

    public QuickBlast() {
        super(ID, COST, TYPE, VALUE);
        baseDamage = damage = secondDamage = baseSecondDamage = DAMAGE;
        CardModifierManager.addModifier(this, new WhistolMod());
        CardModifierManager.addModifier(this, new DrawMod(2));
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new QuickBlastMod(rawDescription, useSecondDamage));
        CardModifierManager.addModifier(card, new DrawMod(2));
        CardModifierManager.addModifier(card, new WhistolMod());
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addInfo(new Triplet<>(AssembledCard.SaveInfo.CoreType.QUICK_BLAST, useSecondDamage, UP_DAMAGE));
            ((AssembledCard) card).saveDamage(DAMAGE, useSecondDamage);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeSecondDamage(UP_DAMAGE);
    }
}
