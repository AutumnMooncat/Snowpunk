package Snowpunk.archive;

import Snowpunk.actions.ARCHIVED_AssembleCardAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Create extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Create.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, CORES = 4, PARTS = 1, BASE_OPTIONS = 3, BONUS_OPTIONS = 2;

    public Create() {
        super(ID, COST, TYPE, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING);
        baseInfo = info = BONUS_OPTIONS;
        magicNumber = baseMagicNumber = CORES;
        secondMagic = baseSecondMagic = PARTS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ARCHIVED_AssembleCardAction(magicNumber, 0, BASE_OPTIONS + info));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}