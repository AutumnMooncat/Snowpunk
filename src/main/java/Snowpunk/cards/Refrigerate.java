package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Refrigerate extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Refrigerate.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 8;
    private static final int UP_BLK = 3;
    private static final int BUFF = 3;

    private boolean buffOnRetain = false;

    public Refrigerate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new ModCardTempAction(this, -1));
    }

    @Override
    public void onRetained() {
        if (buffOnRetain) {
            upgradeBlock(magicNumber);
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBlock(UP_BLK));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new ReshuffleMod()));
        addUpgradeData(this, () -> {
            buffOnRetain = true;
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(BUFF);
            uDesc();
        });
    }
}