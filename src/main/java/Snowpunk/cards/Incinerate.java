package Snowpunk.cards;

import Snowpunk.actions.MultDebuffsAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Incinerate extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Incinerate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private boolean AoE;

    public Incinerate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        exhaust = true;
        AoE = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AoE) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped())
                    Wiz.atb(new MultDebuffsAction(mo, magicNumber));
            }
        } else
            Wiz.atb(new MultDebuffsAction(m, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> {
            target = CardTarget.ALL_ENEMY;
            AoE = true;
            uDesc();
        });
        addUpgradeData(() -> upgradeBaseCost(0));
    }
}