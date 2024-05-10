package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.MakeCopyInHandAction;
import Snowpunk.cardmods.OverdriveMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SunlightYellow extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SunlightYellow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public SunlightYellow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new EnhanceCardInHardAction(new OverdriveMod()));
        //Wiz.atb(new ApplyCardModifierAction(p.hand, Math.max(magicNumber, 1), new OverdriveMod()));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}