package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.ClankAction;
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

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 4;

    public PipeBurst() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int numEnemies = 1;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster.currentHealth > 0 && !monster.isDeadOrEscaped())
                numEnemies++;
        }
        for (int i = 0; i < numEnemies; i++)
            blck();

        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }

    @Override
    public void onClank(AbstractMonster monster) {
        int remove = 99;
        if (magicNumber > 0)
            remove = magicNumber;
        addToTop(new DiscardAction(Wiz.adp(), Wiz.adp(), remove, false));
    }
}