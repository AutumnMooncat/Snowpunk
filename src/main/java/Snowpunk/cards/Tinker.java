package Snowpunk.cards;

import Snowpunk.actions.UpgradeRandomInHardWithVisualAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnObtainCard;
import Snowpunk.powers.BrassPower;
import Snowpunk.util.Wiz;
import Snowpunk.vfx.SmithEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Tinker extends AbstractEasyCard implements OnObtainCard {
    public final static String ID = makeID(Tinker.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public Tinker() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
//        addToBot(new UpgradeRandomInHardWithVisualAction(1));
        Wiz.applyToSelf(new BrassPower(player, magicNumber));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upp() {

    }

    @Override
    public void onObtain() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.closeCurrentScreen();
        }
        AbstractDungeon.effectsQueue.add(new SmithEffect());
    }
}