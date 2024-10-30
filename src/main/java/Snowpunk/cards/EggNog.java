package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.GainHollyAction;
import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.HiddenMagicNumberMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class EggNog extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(EggNog.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    public EggNog() {
        this(0);
    }

    public EggNog(int numBoosted) {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        info = baseInfo = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new GainSnowballAction(magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToBot(new GainHollyAction(getSnow()));
                isDone = true;
            }
        });
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EggNog(info);
    }
}