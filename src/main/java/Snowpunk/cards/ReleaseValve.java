package Snowpunk.cards;

import Snowpunk.actions.ExhumeEvaporatedCardAction;
import Snowpunk.actions.PlayTopCardAndEvaporateAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class ReleaseValve extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ReleaseValve.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1, SINGE = 2, UP_SINGE = 2;
    public boolean random = true;
    public ReleaseValve() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(3));
        random = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int numGears = getGears();
        if (numGears > 0)
            Wiz.applyToEnemy(m, new SingePower(m, numGears));
        Wiz.atb(new PlayTopCardAndEvaporateAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng)));
//            addToBot(new ExhumeEvaporatedCardAction(numGears, 1, random));
    }

    /*
        public void applyPowers() {
            int realBaseMagic = baseMagicNumber;
            baseMagicNumber += getGears() * EvaporatePanel.evaporatePile.size();
            super.applyPowers();
            magicNumber = baseMagicNumber;
            baseMagicNumber = realBaseMagic;
            isMagicNumberModified = magicNumber != baseMagicNumber;
        }
    */
    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}