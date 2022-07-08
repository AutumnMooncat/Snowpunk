package Snowpunk.cards;

import Snowpunk.actions.AssembleCardAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Invent extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Invent.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Invent() {
        super(ID, COST, TYPE, RARITY, TARGET);
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING); // We don't want this generated in combat
        baseInfo = info = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (info == 0) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.INVENTION));
        } else if (info == 1) {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.CREATION));
        } else {
            Wiz.atb(new AssembleCardAction(AssembleCardAction.AssembleType.MACHINATION));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeInfo(1));
        addUpgradeData(this, () -> upgradeInfo(1), 0);
    }
}