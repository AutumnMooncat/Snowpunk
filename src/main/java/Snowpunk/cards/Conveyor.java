package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Conveyor extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(Conveyor.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, DRAW = 3, UP_DRAW = 1;

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
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(UP_DRAW)));
        addUpgradeData(() -> {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(3);
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(-1));
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank() {
        int remove = magicNumber;
        if (magicNumber < 0)
            remove = getGears() * 2;
        addToTop(new ApplyCardModifierAction(this, new GearMod(-remove)));
    }
}