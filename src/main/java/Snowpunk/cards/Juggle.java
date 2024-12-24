package Snowpunk.cards;

import Snowpunk.actions.GainHollyAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.actions.MoveModifiersAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Juggle extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Juggle.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    boolean copy = false;
    public Juggle() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        copy = false;
        CardModifierManager.addModifier(this, new HatMod());
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
//        Wiz.atb(new ResetExhaustAction(this, false));
        addToBot(new MoveModifiersAction(magicNumber, copy));
//        if (!CardModifierManager.hasModifier(this, HatMod.ID))
//            Wiz.atb(new ResetExhaustAction(this, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            copy = true;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 2, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Juggle();
    }
}