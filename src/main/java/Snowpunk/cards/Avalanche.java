package Snowpunk.cards;

import Snowpunk.cardmods.DupeMod;
import Snowpunk.cardmods.FrostMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Avalanche extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Avalanche.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int UP_COST = 3;
    private static final int DMG = 12;
    private static final int UP_DMG = 4;
    private static final int SCALE = 2;
    private static final int UP_SCALE = 2;

    private boolean addCopy = false;

    public Avalanche() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        CardTemperatureFields.addInherentHeat(this, -1);
        CardModifierManager.addModifier(this, new FrostMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (addCopy)
            addToBot(new MakeTempCardInDiscardAction(makeStatEquivalentCopy(), 1));
    }

    /*
        @Override
        public void onRetained() {
            if (magicNumber > 0) {
                this.upgradeDamage(this.magicNumber);
            }
        }
    */
    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new DupeMod()));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
    }
}