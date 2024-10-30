package Snowpunk.cards;

import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.FineTunePower;
import Snowpunk.powers.GildedWrenchPower;
import Snowpunk.powers.WrenchPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class FineTuned extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FineTuned.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CLANK = 1, UP_CLANK = 1;

    public FineTuned() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        Wiz.applyToSelf(new FineTunePower(p, magicNumber));
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.applyToSelf(new GildedWrenchPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            selfRetain = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}