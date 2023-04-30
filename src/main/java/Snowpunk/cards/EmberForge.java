package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.EvaporateCardInHandAction;
import Snowpunk.actions.EvaporateRandomCardsAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.EmberForgePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class EmberForge extends AbstractMultiUpgradeCard implements ClankCard {
    public final static String ID = makeID(EmberForge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    private boolean upgradeDraw = false;

    public EmberForge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new VFXAction(player, new InflameEffect(player), .5F));
        Wiz.applyToSelf(new EmberForgePower(player, 1));
        addToBot(new ClankAction(this));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(-1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, 1));
        setDependencies(true, 2, 1);
    }

    @Override
    public void onClank() {
        for (int i = 0; i < magicNumber; i++)
            addToTop(new EvaporateCardInHandAction());
    }
}