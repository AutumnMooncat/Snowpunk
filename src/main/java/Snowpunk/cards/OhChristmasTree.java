package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.HiddenMagicNumberMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class OhChristmasTree extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(OhChristmasTree.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public OhChristmasTree() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 12;
        exhaust = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new GainHollyAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
        setDependencies(true, 2, 0, 1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new OhChristmasTree();
    }
}