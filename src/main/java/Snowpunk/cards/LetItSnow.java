package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.SnowfallPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class LetItSnow extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(LetItSnow.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public LetItSnow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addHeat(this, CardTemperatureFields.COLD);
        CardModifierManager.addModifier(this, new GearMod(4));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        Wiz.atb(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
        int gears = getGears();
        if (gears > 0) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped() && monster.currentHealth > 0)
                    Wiz.atb(new ApplyPowerAction(monster, AbstractDungeon.player, new ChillPower(monster, gears), gears));
            }
        }
//        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//            if (!monster.isDeadOrEscaped() && monster.currentHealth > 0) {
//                int numChill = 0;
//                if (monster.hasPower(ChillPower.POWER_ID))
//                    numChill = monster.getPower(ChillPower.POWER_ID).amount;
//                if (!monster.hasPower(ArtifactPower.POWER_ID))
//                    numChill += gears;
//
//                Wiz.atb(new DamageAction(monster, new DamageInfo(player, numChill, DamageInfo.DamageType.HP_LOSS)));
//            }
//        }

        Wiz.applyToSelf(new SnowfallPower(player, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(2)));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
    }
}