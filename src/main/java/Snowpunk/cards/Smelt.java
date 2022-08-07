package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Smelt extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Smelt.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 5, UPG_BLOCK = 3, UP_COST = 0;

    public Smelt() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (Wiz.adp().hand.contains(this)) {
            int handIndex = Wiz.adp().hand.group.indexOf(this);
            if (handIndex > 0) {
                Wiz.atb(new ModCardTempAction(Wiz.adp().hand.group.get(handIndex-1), 1));
            }
            if (handIndex < Wiz.adp().hand.size()-1) {
                Wiz.atb(new ModCardTempAction(Wiz.adp().hand.group.get(handIndex+1), 1));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new VentMod()), new int[]{}, new int[]{2});
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST), new int[]{}, new int[]{1});
    }
}