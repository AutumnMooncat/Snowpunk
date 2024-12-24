package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.PermWrenchPower;
import Snowpunk.powers.ReverseNextClankPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
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

    private static final int COST = 1, DMG = 11, CLANK = 1, UP_DMG = 3, UP_CLANK = 1;

    public Ratchet() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = DMG;
        CardModifierManager.addModifier(this, new GearMod(1));
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new SFXAction("snowpunk:bonk"));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        int gears = getGears();
        if (gears > 0) {
            if (info < 0)
                Wiz.applyToSelf(new PermWrenchPower(player, gears));
            else
                Wiz.applyToSelf(new ReverseNextClankPower(player, gears));
        }
    }

    @Override
    public void addUpgrades() {
//        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeInfo(2));
        addUpgradeData(() -> {
            isInnate = true;
            uDesc();
        });
    }
}