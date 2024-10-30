package Snowpunk.cards;

import Snowpunk.actions.*;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class PipeBurst extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(PipeBurst.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 4;

    boolean random;
    public PipeBurst() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        random = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        blck();

        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> {
            random = false;
            uDesc();
        });
    }

    @Override
    public void onClank(AbstractMonster target) {
        if (random)
            addToTop(new EvaporateRandomCardAction());
        else
            addToTop(new ChooseCardToEvaporateAction(1));
    }

    @Override
    public void unClank(AbstractMonster target) {
        addToBot(new ExhumeEvaporatedCardAction(1, 0, random, this));
    }

}