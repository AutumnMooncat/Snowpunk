package Snowpunk.cards.old_cores;

import Snowpunk.cardmods.TinkerSelfMod;
import Snowpunk.cardmods.cores.ScavengeStrikeMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.util.Triplet;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ScavengedStrike extends AbstractCoreCard {
    public static final String ID = makeID(ScavengedStrike.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final EffectTag VALUE = EffectTag.DAMAGE;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    public static final int UP_DAMAGE = 4;

    public ScavengedStrike() {
        super(ID, COST, TYPE, VALUE);
        baseDamage = damage = secondDamage = baseSecondDamage = DAMAGE;
        CardModifierManager.addModifier(this, new TinkerSelfMod());
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new ScavengeStrikeMod(rawDescription, useSecondDamage));
        CardModifierManager.addModifier(card, new TinkerSelfMod());
        card.tags.add(CardTags.STRIKE);
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addInfo(new Triplet<>(ARCHIVED_AssembledCard.SaveInfo.CoreType.SCAVENGE_STRIKE, useSecondDamage, UP_DAMAGE));
            ((ARCHIVED_AssembledCard) card).saveDamage(DAMAGE, useSecondDamage);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeSecondDamage(UP_DAMAGE);
    }
}
