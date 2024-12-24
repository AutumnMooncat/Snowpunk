package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Conveyor extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Conveyor.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, DRAW = 3;

    public Conveyor() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(DRAW));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int drawAmount = getGears();
        if (drawAmount > 0)
            addToBot(new DrawCardAction(drawAmount));

        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
        setDependencies(true, 1, 0);
    }

    @Override
    public void onClank(AbstractMonster target) {
//        int gears = 0;
//        if (CardModifierManager.hasModifier(this, GearMod.ID))
//            gears += ((GearMod) CardModifierManager.getModifiers(this, GearMod.ID).get(0)).amount;
//        if (gears % 2 == 1)
//            gears++;
//        gears /= 2;
//        if (gears != 0)
        addToTop(new ApplyCardModifierAction(this, new GearMod(-1)));
//        addToTop(new ResetExhaustAction(this, true));
    }

    @Override
    public void unClank(AbstractMonster target) {
//        int gears = 0;
//        if (CardModifierManager.hasModifier(this, GearMod.ID))
//            gears += ((GearMod) CardModifierManager.getModifiers(this, GearMod.ID).get(0)).amount;
//        if (gears != 0)
        addToTop(new ApplyCardModifierAction(this, new GearMod(1)));
//        addToTop(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
    }
}