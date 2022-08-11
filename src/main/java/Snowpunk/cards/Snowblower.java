package Snowpunk.cards;

import Snowpunk.cardmods.WhistolMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CustomTags;
import Snowpunk.powers.PressureValvesPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowblower extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Snowblower.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;
    private static final int DMG = 10;
    private static final int DOWN_DMG = -4;
    private static final int SNOW = 2;
    private static final int UP_SNOW = 1;
    private static final int DOWN_SNOW = -2;

    public Snowblower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = SNOW;
        baseInfo = info = 0;
        CardModifierManager.addModifier(this, new WhistolMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (cost == -1) {
            int effect = this.energyOnUse;

            if (p.hasRelic("Chemical X")) {
                effect += ChemicalX.BOOST;
                p.getRelic("Chemical X").flash();
            }

            effect += magicNumber;

            for (int i = 0 ; i < effect ; i++) {
                dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
            }
            Wiz.applyToSelf(new SnowballPower(p, effect));

            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        } else {
            if (info == 0) {
                dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            } else {
                for (int i = 0 ; i < magicNumber ; i++) {
                    dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                }
            }
            Wiz.applyToSelf(new SnowballPower(p, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            upgradeDamage(DOWN_DMG);
            upgradeInfo(1);
            uDesc();
        });
        addUpgradeData(this, () -> upgradeMagicNumber(UP_SNOW));
        addUpgradeData(this, () -> {
            upgradeBaseCost(-1);
            upgradeMagicNumber(DOWN_SNOW);
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }, 0, 1);
    }
}