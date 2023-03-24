package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;
import static Snowpunk.util.Wiz.atb;

@NoCompendium
@NoPools
public class TheEmperor extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TheEmperor.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2, UP_COST = 1, DMG = 3, UP_DMG = 1, HITS = 5, UP_HITS = 1;

    public TheEmperor() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = HITS;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBaseCost(UP_COST));
        addUpgradeData(() -> upgradeDamage(UP_DMG));
        addUpgradeData(() -> upgradeMagicNumber(UP_HITS));
    }
}