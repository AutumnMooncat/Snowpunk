package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class FireFist extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FireFist.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 9;

    private static ArrayList<TooltipInfo> FireTip, FireAndHotTip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (FireTip == null) {
            FireTip = new ArrayList<>();
            FireTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }

        if (FireAndHotTip == null) {
            FireAndHotTip = new ArrayList<>();
            FireAndHotTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
            FireAndHotTip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.HOT.toLowerCase()), BaseMod.getKeywordDescription(KeywordManager.HOT.toLowerCase())));
        }

        if (CardTemperatureFields.getCardHeat(this) != CardTemperatureFields.HOT)
            return FireAndHotTip;
        return FireTip;
    }

    public FireFist() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));

        Wiz.applyToSelf(new FireballPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(4));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.HOT));
        addUpgradeData(() -> upgradeMagicNumber(1));
    }
}