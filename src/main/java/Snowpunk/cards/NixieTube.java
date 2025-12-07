package Snowpunk.cards;

import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.MultiUpgradeInHandAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cardmods.Tinkerific;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class NixieTube extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(NixieTube.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean addGear = false;

    public NixieTube() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = 6;
        CardModifierManager.addModifier(this, new GearMod(2));
        CardModifierManager.addModifier(this, new Tinkerific());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        int gears = GearMod.getGears(this);
        if (gears > 0)
            addToBot(new ExhaustAction(gears, false, true, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(3));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}