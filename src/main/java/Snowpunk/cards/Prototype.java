package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.CheckPrototypeAction;
import Snowpunk.actions.ClankAction;
import Snowpunk.actions.ResetExhaustAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Prototype extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Prototype.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean afterClank = false;
    private Masterpiece success;

    public Prototype() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 10;
        success = new Masterpiece();
        MultiCardPreview.add(this, success);
        CardModifierManager.addModifier(this, new GearMod(0));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CheckPrototypeAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
        addUpgradeData(() -> upgradeMagicNumber(-2));
        addUpgradeData(() -> upgradeMagicNumber(-2));
        setDependencies(true, 2, 1);
    }
}