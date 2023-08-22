package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class ProperGreeting extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ProperGreeting.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0, CHILL = 3, UP_CHILL = 2, HAT = 1, UP_HAT = 1;

    private boolean aoe;

    public ProperGreeting() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHILL;
        CardModifierManager.addModifier(this, new HatMod(HAT));
        isInnate = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (aoe) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped())
                    Wiz.applyToEnemy(mo, new ChillPower(mo, magicNumber));
            }
        } else
            Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));

    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(UP_CHILL));
        addUpgradeData(() -> {
            aoe = true;
            target = CardTarget.ALL_ENEMY;
            uDesc();
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(HAT)));
    }
}