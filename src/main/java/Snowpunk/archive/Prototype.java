package Snowpunk.archive;

import Snowpunk.actions.CheckPrototypeAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.Masterpiece;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
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