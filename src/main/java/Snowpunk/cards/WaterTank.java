package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.interfaces.MultiTempEffectCard;
import Snowpunk.patches.CustomTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class WaterTank extends AbstractEasyCard implements MultiTempEffectCard {
    public final static String ID = makeID(WaterTank.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int MULTI = 2;
    private static final int UP_MULTI = 1;

    public WaterTank() {
        super(ID, COST, TYPE, RARITY, TARGET);
        info = baseInfo = MULTI;
        tags.add(CustomTags.VENT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public void upp() {
        upgradeInfo(UP_MULTI);
    }

    @Override
    public int tempEffectAmount() {
        return info;
    }
}