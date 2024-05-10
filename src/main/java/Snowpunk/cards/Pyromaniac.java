package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.PyromaniacPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class Pyromaniac extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Pyromaniac.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;

    public Pyromaniac() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        isEthereal = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new VFXAction(player, new InflameEffect(player), .5F));
        Wiz.applyToSelf(new PyromaniacPower(player, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(1));
        addUpgradeData(() ->
        {
            isInnate = true;
            uDesc();
        });
    }
}