package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
public class Fireball extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Fireball.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    protected static ArrayList<TooltipInfo> tooltip;

    private static final int COST = 0, UP_COST = 0, DMG = 2, UP_DMG = 1, MAGIC = 2, UP_MAGIC = 1;

    public Fireball() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = MAGIC;
        isEthereal = true;
        //SoulboundField.soulbound.set(this, true);
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        Wiz.applyToEnemy(m, new SingePower(m, p, magicNumber));
    }
/*
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle(cardStrings.EXTENDED_DESCRIPTION[0].toLowerCase()));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (tooltip == null) {
            tooltip = new ArrayList<>();
            tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(cardStrings.EXTENDED_DESCRIPTION[0].toLowerCase()), BaseMod.getKeywordDescription(cardStrings.EXTENDED_DESCRIPTION[0].toLowerCase())));
        }
        return tooltip;
    }*/

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            upgradeDamage(UP_DMG);
            upgradeMagicNumber(UP_MAGIC);
        });
        //addUpgradeData(this, () -> upgradeMagicNumber(UP_MAGIC));
        //addUpgradeData(this, () -> upgradeBaseCost(UP_COST), 0, 1);
    }
}