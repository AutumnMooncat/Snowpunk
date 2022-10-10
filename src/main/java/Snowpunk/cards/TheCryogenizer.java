package Snowpunk.cards;

import Snowpunk.actions.CondenseRandomCardToHandAction;
import Snowpunk.actions.ModEngineTempAction;
import Snowpunk.actions.PlayRandomEvaporatedCardAction;
import Snowpunk.cardmods.TemperatureMod;
import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class TheCryogenizer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TheCryogenizer.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, DMG = 20, UP_DMG = 8, FROST = 1;

    public TheCryogenizer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = FROST;
        exhaust = true;
        CardTemperatureFields.addInherentHeat(this, -2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new FrostbitePower(m, p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeDamage(UP_DMG));
        addUpgradeData(this, () -> upgradeMagicNumber(1));
        addUpgradeData(this, () -> {
            exhaust = false;
            uDesc();
        });
    }
}