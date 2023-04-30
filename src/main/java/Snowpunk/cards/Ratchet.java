package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Ratchet extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Ratchet.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1, DMG = 9, CLANK = 1, UP_DMG = 3, UP_CLANK = 1;

    public Ratchet() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        magicNumber = baseMagicNumber = CLANK;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new SFXAction("snowpunk:bonk"));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToSelf(new PermWrenchPower(player, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, -1));
        addUpgradeData(() -> upgradeMagicNumber(UP_CLANK));
    }
}